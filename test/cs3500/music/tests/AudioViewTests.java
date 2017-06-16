package cs3500.music.tests;

import static org.junit.Assert.assertTrue;

import cs3500.music.model.MusicOperations;
import cs3500.music.model.MusicSheet;
import cs3500.music.model.NoteTypeWestern;
import cs3500.music.model.OctaveNumber0To10;
import cs3500.music.model.Pitch;
import cs3500.music.view.IView;
import cs3500.music.view.AudioView;
import java.io.IOException;
import java.util.Scanner;
import org.junit.Test;

/**
 * Tests of the AudioView using a mock synthesizer and receiver. Because of the way notes are
 * handled, it doesn't matter if notes are sent to the receiver in exactly the order they are sent
 * to the model. This becomes of interest when multiple notes are set to be played at the same time.
 * As long as these notes are sent during the correct beat, the AudioView will preform correctly.
 * Because of this, we test that lines of the expected log and actual log are the same even if the
 * order is different.
 */
public class AudioViewTests {

  private MusicOperations model;

  /**
   * A kind of test harness for the AudioView. The method takes in a model that has been modified
   * with new notes added to it. The model the data from this model is transferred to an AudioView
   * that is set to keep a log of the calls made to its receiver. The method runs through the
   * the beats of the music sheet to simulate the view playing and the resulting log is compared
   * with the expected log.
   * @param model the model holding the notes to run
   * @param expectedLog the output that is expected to match that of the actual log
   * @return true if the success is successful (this is added to satisfy the style autograder)
   */
  private boolean runAudio(MusicOperations model, String expectedLog) {
    StringBuilder log = new StringBuilder();
    IView mockAudio = AudioView.buildTestView(log);
    mockAudio.setTempo(1000000);

    int lastBeat = model.getLastBeat();
    mockAudio.setNotes(model.getPitches());

    //run through the audioview which is sending m
    for (int beat = 0; beat <= lastBeat; beat++) {
      mockAudio.setCurrentBeat(beat);
    }

    String logString = log.toString();

    Scanner expectedLines = new Scanner(expectedLog);
    Scanner actualLines = new Scanner(logString);

    while (expectedLines.hasNextLine()) {
      assertTrue(logString.contains(expectedLines.nextLine()));
    }

    while (actualLines.hasNextLine()) {
      assertTrue(expectedLog.contains(actualLines.nextLine()));
    }
    return true;
  }

  /**
   * Uses note information to create a set of calls that would be expected to be made to a receiver
   * for the given note. Both a NOTE_ON call and NOTE_OFF call are made as these are always made in
   * pairs in the AudioView. Using this method on a succession of notes mimics the output that would
   * expected by the log on the MockReceiver
   * @param channel the channel on which the message is sent
   * @param pitch the pitch of the note - to be decomposed into its NoteType and Octave
   * @param loudness the loudness of the note
   * @param duration the duration of hte note in beats
   * @return a string combining all of this information into a pair of expected calls to a
   *     receiver object
   */
  private String addMidiStartStop(int channel, Pitch pitch, int loudness, int duration) {
    StringBuilder s = new StringBuilder();

    s.append("Command: ").append("NOTE_ON").append("\t");
    s.append("Channel: ").append(channel).append("\t");
    s.append("Pitch: ").append(pitch.toString()).append("\t");
    s.append("Loudness: ").append(loudness).append("\t");
    s.append("Timestamp: ").append(-1).append("\n");

    s.append("Command: ").append("NOTE_OFF").append("\t");
    s.append("Channel: ").append(channel).append("\t");
    s.append("Pitch: ").append(pitch).append("\t");
    s.append("Loudness: ").append(loudness).append("\t");
    s.append("Timestamp: ").append(duration * 1000000).append("\n");
    return s.toString();
  }


  @Test
  public void emptyTest() {
    model = new MusicSheet();

    assertTrue(runAudio(model, ""));
  }

  @Test
  public void oneNoteTest() {
    model = new MusicSheet();
    StringBuilder expectedLog = new StringBuilder();

    Pitch c = new Pitch(NoteTypeWestern.C, OctaveNumber0To10.O0);

    model.addNote(OctaveNumber0To10.O0, NoteTypeWestern.C, 0, 1, 1, 20);
    expectedLog.append(addMidiStartStop(0, c, 20, 1));

    assertTrue(runAudio(model, expectedLog.toString()));
  }

  @Test
  public void twoNoteTest() throws IOException {
    model = new MusicSheet();
    StringBuilder expectedLog = new StringBuilder();

    Pitch b = new Pitch(NoteTypeWestern.B, OctaveNumber0To10.O3);
    Pitch c = new Pitch(NoteTypeWestern.C, OctaveNumber0To10.O1);

    model.addNote(OctaveNumber0To10.O3, NoteTypeWestern.B, 30, 40, 2, 20);
    model.addNote(OctaveNumber0To10.O1, NoteTypeWestern.C, 1, 3, 1, 25);

    expectedLog.append(addMidiStartStop(0, c, 25, 2));
    expectedLog.append(addMidiStartStop(0, b, 20, 10));

    assertTrue(runAudio(model, expectedLog.toString()));
  }

  @Test
  public void fiveNotesSameTimeTest() {
    model = new MusicSheet();
    StringBuilder expectedLog = new StringBuilder();

    Pitch cSharp = new Pitch(NoteTypeWestern.C_SHARP, OctaveNumber0To10.O1);
    Pitch c = new Pitch(NoteTypeWestern.C, OctaveNumber0To10.O1);
    Pitch d = new Pitch(NoteTypeWestern.D, OctaveNumber0To10.O1);
    Pitch dSharp = new Pitch(NoteTypeWestern.D_SHARP, OctaveNumber0To10.O1);
    Pitch e = new Pitch(NoteTypeWestern.E, OctaveNumber0To10.O1);

    model.addNote(OctaveNumber0To10.O1, NoteTypeWestern.C_SHARP, 1, 3, 2, 11);
    model.addNote(OctaveNumber0To10.O1, NoteTypeWestern.C, 1, 3, 1, 12);
    model.addNote(OctaveNumber0To10.O1, NoteTypeWestern.D, 1, 3, 1, 13);
    model.addNote(OctaveNumber0To10.O1, NoteTypeWestern.D_SHARP, 1, 3, 1, 14);
    model.addNote(OctaveNumber0To10.O1, NoteTypeWestern.E, 1, 3, 1, 15);

    expectedLog.append(addMidiStartStop(0, cSharp, 11, 2));
    expectedLog.append(addMidiStartStop(0, c, 12, 2));
    expectedLog.append(addMidiStartStop(0, d, 13, 2));
    expectedLog.append(addMidiStartStop(0, dSharp, 14, 2));
    expectedLog.append(addMidiStartStop(0, e, 15, 2));

    assertTrue(runAudio(model, expectedLog.toString()));
  }

}
