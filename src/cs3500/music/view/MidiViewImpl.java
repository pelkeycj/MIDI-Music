package cs3500.music.view;

import cs3500.music.model.Note;
import cs3500.music.model.NoteType;
import cs3500.music.model.NoteTypeWestern;
import cs3500.music.model.OctaveNumber1To10;
import cs3500.music.model.Pitch;
import cs3500.music.model.PitchSequence;
import java.awt.Rectangle;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.util.Builder;
import javax.sound.midi.*;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.omg.SendingContext.RunTime;

/**
 * A skeleton for MIDI playback
 */
public class MidiViewImpl extends AView {
  private final Synthesizer synth;
  private final Receiver receiver;

  private final int CHANNEL = 0;

  private int tempo;
  private int noteDurationMicro;

  public MidiViewImpl(int tempo) {
    try {
      this.synth = MidiSystem.getSynthesizer();
      this.receiver = synth.getReceiver();
      this.synth.open();
    } catch (MidiUnavailableException e) {
      throw new RuntimeException("Midi view failed to open.");
    }
    this.tempo = tempo;
    double beatsPerSecond = tempo / 60.0;
    double secondsPerBeat = 1.0 / beatsPerSecond;
    this.noteDurationMicro = (int) (1000000 * secondsPerBeat);
  }
  /**
   * Relevant classes and methods from the javax.sound.midi library:
   * <ul>
   *  <li>{@link MidiSystem#getSynthesizer()}</li>
   *  <li>{@link Synthesizer}
   *    <ul>
   *      <li>{@link Synthesizer#open()}</li>
   *      <li>{@link Synthesizer#getReceiver()}</li>
   *      <li>{@link Synthesizer#getChannels()}</li>
   *    </ul>
   *  </li>
   *  <li>{@link Receiver}
   *    <ul>
   *      <li>{@link Receiver#send(MidiMessage, long)}</li>
   *      <li>{@link Receiver#close()}</li>
   *    </ul>
   *  </li>
   *  <li>{@link MidiMessage}</li>
   *  <li>{@link ShortMessage}</li>
   *  <li>{@link MidiChannel}
   *    <ul>
   *      <li>{@link MidiChannel#getProgram()}</li>
   *      <li>{@link MidiChannel#programChange(int)}</li>
   *    </ul>
   *  </li>
   * </ul>
   * @see <a href="https://en.wikipedia.org/wiki/General_MIDI">
   *   https://en.wikipedia.org/wiki/General_MIDI
   *   </a>
   */

  public void playNote() throws MidiUnavailableException, InvalidMidiDataException {

    MidiMessage start = new ShortMessage(ShortMessage.NOTE_ON, 0, 60, 64);
    MidiMessage stop = new ShortMessage(ShortMessage.NOTE_OFF, 0, 60, 64);
    this.receiver.send(start, -1);
    this.receiver.send(stop, this.synth.getMicrosecondPosition() + 200000);

    MidiChannel[] midiChannels = synth.getChannels();
    Instrument[] instruments = synth.getDefaultSoundbank().getInstruments();
    synth.loadInstrument(instruments[41]);

    List<Pitch> ps = new ArrayList<>();
    ps.add(new Pitch(NoteTypeWestern.A, OctaveNumber1To10.O5));
    ps.add(new Pitch(NoteTypeWestern.A, OctaveNumber1To10.O5));
    ps.add(new Pitch(NoteTypeWestern.A, OctaveNumber1To10.O5));
    ps.add(new Pitch(NoteTypeWestern.B, OctaveNumber1To10.O5));
    ps.add(new Pitch(NoteTypeWestern.B, OctaveNumber1To10.O5));

    for (Pitch p : ps) {
      System.out.println("Press any key to continue...");
      try
      {
        System.in.read();
      }
      catch(Exception e)
      {}
      start = new ShortMessage(ShortMessage.NOTE_ON, 4, p.getValue(), 100);
      stop = new ShortMessage(ShortMessage.NOTE_OFF, 4, p.getValue(), 100);
      this.receiver.send(start, -100000);
      this.receiver.send(stop, this.synth.getMicrosecondPosition() + 1000000);
      System.out.println("P.getvalue: " + Integer.toString(p.getValue()));
//      midiChannels[0].noteOn(p.getValue(), 100);
    }

    /*
    The receiver does not "block", i.e. this method
    immediately moves to the next line and closes the
    receiver without waiting for the synthesizer to
    finish playing.

    You can make the program artificially "wait" using
    Thread.sleep. A better solution will be forthcoming
    in the subsequent assignments.
    */
    this.receiver.close(); // Only call this once you're done playing *all* notes
  }


  @Override
  public void initialize() {
//    try {
//      playNote();
//    } catch (InvalidMidiDataException e) {
//      throw new RuntimeException("Midi play failed.");
//    }
  }

  @Override
  public void setCurrentBeat(int beat) throws IllegalArgumentException {
    Set<MIDIData> newMidi = new HashSet<>();

    for (PitchSequence p : this.pitches) {
      if (p.playingAt(beat)) {
        Note n = p.noteAt(beat);
        if (n.getStart() == beat) {
          int duration = 1 + n.getEnd() - n.getStart();
          newMidi.add(new MIDIData(p.getPitchCopy(), n.getLoudness(), duration, n.getInstrument()));
        }
      }
    }

    for (MIDIData m : newMidi) {
      m.queue(this.receiver, this.synth);
    }

  }

  @Override
  public void refresh() {
    this.receiver.close();
  }

  @Override
  public void setKeyListener(KeyListener keyListener) {
    
  }

  private class MIDIData {
    private int channel;
    private int instrument;
    private int duration;
    private int pitch;
    private int loudness;

    MIDIData(Pitch p, int loudness, int duration, int instrument) {
      this.channel = CHANNEL;
      this.pitch = p.getValue();
      this.loudness = loudness;
      this.duration = duration;
      this.instrument = instrument;
    }

    void queue(Receiver r, Synthesizer s) {
      try {
        System.out.println("Play " + Integer.toString(this.pitch) + " at " + Integer.toString(loudness) + " for " + Integer.toString(duration) + " beats.");
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
