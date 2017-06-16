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
 * A skeleton for MusicSheet playback
 */
public class MidiViewImpl extends AView {
  private final Synthesizer synth;
  private final Receiver receiver;

  private final int CHANNEL = 0;

  private int noteDurationMicro;

  private MidiViewImpl(Synthesizer s) {
    try {
      this.synth = s;
      this.receiver = synth.getReceiver();
      this.synth.open();
    } catch (MidiUnavailableException e) {
      throw new RuntimeException("Midi view failed to open.");
    }
  }

  public static MidiViewImpl buildSoundView() {
    try {
      return new MidiViewImpl(MidiSystem.getSynthesizer());
    } catch (MidiUnavailableException e) {
      throw new IllegalArgumentException("Midi Synthesizer failed to open.");
    }
  }

  public static MidiViewImpl buildTestView(StringBuilder log) {
    return new MidiViewImpl(new MockSynthesizer(log));
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
          int duration = 1 + n.getEnd() - n.getStart();
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
    return;
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

    void run(Receiver r, Synthesizer s) {
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
