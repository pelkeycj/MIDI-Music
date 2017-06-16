package views;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import cs3500.music.model.MusicOperations;
import cs3500.music.model.MusicSheet;
import cs3500.music.model.NoteTypeWestern;
import cs3500.music.model.OctaveNumber0To10;
import cs3500.music.view.IView;
import cs3500.music.view.AudioView;
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

  public void runAudio(MusicOperations model, String expectedLog) {
    StringBuilder log = new StringBuilder();
    IView mockAudio = AudioView.buildTestView(log);
    mockAudio.setTempo(1000000);

    int lastBeat = model.getLastBeat();
    mockAudio.setNotes(model.getPitches());

    //run through the audioview which is sending m
    for (int beat = 0; beat <= lastBeat; beat++) {
      mockAudio.setCurrentBeat(beat);
    }

    assertEquals(expectedLog, log.toString());
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

  public String addMidiStartStop(int channel, int pitch, int loudness) {
    StringBuilder s = new StringBuilder();
    s.append("Command: ").append(ShortMessage.NOTE_ON).append(" ");
    s.append("Channel: ").append(channel).append(" ");
    s.append("Data1: ").append(pitch).append(" ");
    s.append("Data2: ").append(loudness).append("\n");

    s.append("Command: ").append(ShortMessage.NOTE_OFF).append(" ");
    s.append("Channel: ").append(channel).append(" ");
    s.append("Data1: ").append(pitch).append(" ");
    s.append("Data2: ").append(loudness).append("\n");
    return s.toString();
  }


  @Test
  public void emptyTest() {
    model = new MusicSheet();

    runAudio(model, "");
  }

  @Test
  public void oneNoteTest() {
    model = new MusicSheet();
    StringBuilder expectedLog = new StringBuilder();

    model.addNote(OctaveNumber0To10.O0, NoteTypeWestern.C, 0, 1, 1, 20);
    expectedLog.append(addMidiStartStop(0, 0, 20));

    runAudio(model, expectedLog.toString());
  }


}
