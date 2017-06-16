package views;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import cs3500.music.control.IController;
import cs3500.music.control.SimpleController;
import cs3500.music.model.MusicOperations;
import cs3500.music.model.MusicSheet;
import cs3500.music.model.NoteTypeWestern;
import cs3500.music.model.OctaveNumber0To10;
import cs3500.music.view.IView;
import cs3500.music.view.MidiViewImpl;
import java.util.ArrayList;
import java.util.List;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;
import org.junit.Test;

/**
 * Tests of the Midiview using a mock synthesizer and receiver.
 */
public class MidiTests {

  private MusicOperations model;

  public void runAudio(MusicOperations model, List<MidiPair> expectedOutput) {
    StringBuilder log = new StringBuilder();
    IView mockAudio = MidiViewImpl.buildTestView(log);
    mockAudio.setTempo(1000000);

    int lastBeat = model.getLastBeat();
    mockAudio.setNotes(model.getPitches());

    //run through the audioview which is sending m
    for (int beat = 0; beat <= lastBeat; beat++) {
      mockAudio.setCurrentBeat(beat);
    }

    StringBuilder expected = new StringBuilder();

    for (MidiPair p : expectedOutput) {
      expected.append(p.toString());
    }

    assertEquals(expected.toString(), log.toString());
  }

  private class MidiPair {

    MidiMessage m;
    long timeStamp;

    MidiPair(MidiMessage m, long t) {
      this.m = m;
      this.timeStamp = t;
    }

    MidiMessage getMessage() {
      return this.m;
    }

    Long getTime() {
      return this.timeStamp;
    }

    @Override
    public String toString() {
      return m.toString() + " timeStamp: " + Long.toString(timeStamp) + "\n";
    }
  }

  public List<MidiPair> addMidiStartStop(int channel, int pitch, int loudness) {
    List<MidiPair> output = new ArrayList<>();
    try {
      MidiMessage start = new ShortMessage(ShortMessage.NOTE_ON, channel, pitch, loudness);
      MidiMessage end = new ShortMessage(ShortMessage.NOTE_OFF, channel, pitch, loudness);
      output.add(new MidiPair(start, -1));
      output.add(new MidiPair(end, 1000000));
    } catch (InvalidMidiDataException e) {
      throw new IllegalArgumentException("Bad midi data provided");
    }
    return output;
  }


  @Test
  public void emptyTest() {
    model = new MusicSheet();

    runAudio(model, new ArrayList<>());
  }

  @Test
  public void oneNoteTest() {
    model = new MusicSheet();
    List<MidiPair> expectedMessages = new ArrayList<>();

    model.addNote(OctaveNumber0To10.O0, NoteTypeWestern.C, 0, 1, 1, 1);
    expectedMessages.addAll(addMidiStartStop( 0, 0, 1));

    runAudio(model, expectedMessages);
  }


}
