package cs3500.music.view;

import cs3500.music.model.Note;
import cs3500.music.model.Pitch;
import cs3500.music.model.PitchSequence;
import java.util.HashSet;
import java.util.Set;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Receiver;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiMessage;

/**
 * An audio view for a piece of music. The view stores a set of notes at certain beats and plays
 * those notes by sending corresponding MidiMessages to a reciever. The notes to play are determined
 * each time the current beat of the view is updated. Just like someone playing a piece of music,
 * only the notes starting at the current beat are played. The duration of these notes is controlled
 * by both the note information provided to the view as well as the tempo at which the view is set.
 */
public class AudioView extends AView {
  private final Synthesizer synth;
  private final Receiver receiver;

  private final int CHANNEL = 0;

  private int noteDurationMicro;

  /**
   * A private constructor that is called by the public factory methods of this class.
   * @param s the kind of synthesizer to be used by this AudioView
   */
  private AudioView(Synthesizer s) {
    try {
      this.synth = s;
      this.receiver = synth.getReceiver();
      this.synth.open();
    } catch (MidiUnavailableException e) {
      throw new RuntimeException("Midi view failed to open.");
    }
  }

  /**
   * Public factory method to make an audio model that plays sounds to the default chanel
   * based on the note information passed to the view.
   * @return a new instance of an AudioView using a synthesizer that produces sounds with the
   *     midimessages it receives.
   */
  public static AudioView buildSoundView() {
    try {
      return new AudioView(MidiSystem.getSynthesizer());
    } catch (MidiUnavailableException e) {
      throw new IllegalArgumentException("Midi Synthesizer failed to open.");
    }
  }

  /**
   * Public factory method to make an audio model that uses its midimessages to keep a log of the
   * sound messages it send to its receiver. Used for testing purposes.
   * @param log the stringbuilder used to keep a log of the messages sent by this audio view
   * @return a new instance of an AudioView that will keep track of the messages it sends to
   *      a receiver.
   */
  public static AudioView buildTestView(StringBuilder log) {
    return new AudioView(new MockSynthesizer(log));
  }

  @Override
  public void setTempo(int noteDurationMicro) {
    this.noteDurationMicro = noteDurationMicro;
  }

  @Override
  public void initialize() {
    this.active = true;
  }

  @Override
  public void setCurrentBeat(int beat) throws IllegalArgumentException {
    Set<MIDIData> newMidi = new HashSet<>();

    for (PitchSequence p : this.pitches) {
      if (p.playingAt(beat)) {
        Note n = p.noteAt(beat);
        if (n.getStart() == beat) {
          int duration = n.getEnd() - n.getStart();
          newMidi.add(new MIDIData(p.getPitchCopy(), n.getLoudness(), duration, n.getInstrument()));
        }
      }
    }

    for (MIDIData m : newMidi) {
      m.run(this.receiver, this.synth);
    }
  }

  @Override
  public void refresh() {
    //do nothing
  }

  /**
   * A private class used to collected all the information needed to send a full midi message to
   * a receiver. Keeps all the midi data in one place and creates its own messages to send to the
   * receiver.
   */
  private class MIDIData {
    private int channel;
    private int instrument;
    private int duration;
    private int pitch;
    private int loudness;

    /**
     * Private constructor to be used in the AudioView class to package and store
     * midimessage information
     * @param p the pitch of this midi message using the {@link Pitch} class understood by the
     *     model and views
     * @param loudness the loudness of the note on a scale from  0 to 127
     * @param duration the duration of the note in beats
     * @param instrument the instrument to play this sound
     */
    private MIDIData(Pitch p, int loudness, int duration, int instrument) {
      this.channel = CHANNEL;
      this.pitch = p.getValue();
      this.loudness = loudness;
      this.duration = duration;
      this.instrument = instrument;
    }

    /**
     * Sends this mididata to the receiver using its stored information. Using the instrument to
     * set the synth to its instrument and then sends its note data to the receiver. Sends both its
     * start and end message as both of these are known at the using the duration of the note.
     * @param r the receiver to which the message will be sent.
     * @param s the synthesizer associated with the given receiver. Used to modify the synthesizer's
     *     instrument
     */
    private void run(Receiver r, Synthesizer s) {
      try {
        Soundbank myInstruments = s.getDefaultSoundbank();
        if (myInstruments != null) {
          s.loadInstrument(myInstruments.getInstruments()[this.instrument]);
        }
        MidiMessage start = new ShortMessage(ShortMessage.NOTE_ON, this.channel, this.pitch, this.loudness);
        MidiMessage end = new ShortMessage(ShortMessage.NOTE_OFF, this.channel, this.pitch, this.loudness);
        r.send(start, -1);
        r.send(end, s.getMicrosecondPosition() + duration * noteDurationMicro);
      } catch (InvalidMidiDataException e) {
        throw new RuntimeException("Note failed to play.");
      }
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      else if (o instanceof MIDIData) {
        MIDIData m = (MIDIData) o;
        return m.channel == this.channel && m.pitch == this.pitch && m.loudness == this.loudness
            && m.instrument == this.instrument;
      }
      return false;
    }

    @Override
    public int hashCode() {
      return this.pitch * 10000000 + this.loudness * 1000 + this.channel * 100 + this.instrument;
    }

  }
}
