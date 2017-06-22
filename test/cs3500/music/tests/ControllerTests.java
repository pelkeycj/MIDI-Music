package cs3500.music.tests;

import static org.junit.Assert.assertTrue;

import cs3500.music.control.IController;
import cs3500.music.control.SimpleController;
import cs3500.music.model.MusicOperations;
import cs3500.music.model.MusicSheet;
import cs3500.music.model.Note;
import cs3500.music.model.NoteTypeWestern;
import cs3500.music.model.OctaveNumber0To10;
import cs3500.music.model.PitchSequence;
import cs3500.music.view.GuiViewFrame;
import cs3500.music.view.IView;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests of the controller's methods.
 */
public class ControllerTests {
  IController controller;
  MusicOperations model;
  IView guiView;

  /**
   * Initialize the parameters for testing.
   */
  @Before
  public void init() {
    model = new MusicSheet();
    guiView = new GuiViewFrame();
    controller = new SimpleController(model, guiView);
  }

  @Test
  public void addTest() {
    List<PitchSequence> expected = new ArrayList<>();

    assertTrue(model.getPitches().isEmpty());

    controller.addNote(OctaveNumber0To10.O1, NoteTypeWestern.C, 0, 1);
    PitchSequence pSeq1 = new PitchSequence(OctaveNumber0To10.O1, NoteTypeWestern.C);
    pSeq1.addNote(new Note(0, 1));
    expected.add(pSeq1);

    controller.addNote(OctaveNumber0To10.O1, NoteTypeWestern.D, 0, 3);
    PitchSequence pSeq2 = new PitchSequence(OctaveNumber0To10.O1, NoteTypeWestern.D);
    pSeq2.addNote(new Note(0, 3));
    expected.add(pSeq2);

    //check that the two lists contain the same notes
    for (PitchSequence pSeq : model.getPitches()) {
      assertTrue(expected.contains(pSeq));
    }

    for (PitchSequence pSeq : expected) {
      assertTrue(model.getPitches().contains(pSeq));
    }
  }

  @Test
  public void addTestInstrumentLoudness() {
    List<PitchSequence> expected = new ArrayList<>();

    assertTrue(model.getPitches().isEmpty());

    controller.addNote(OctaveNumber0To10.O1, NoteTypeWestern.C, 0, 1, 1, 100);
    PitchSequence pSeq1 = new PitchSequence(OctaveNumber0To10.O1, NoteTypeWestern.C);
    pSeq1.addNote(new Note(0, 1, 1, 100));
    expected.add(pSeq1);

    controller.addNote(OctaveNumber0To10.O1, NoteTypeWestern.D, 0, 3, 3, 120);
    PitchSequence pSeq2 = new PitchSequence(OctaveNumber0To10.O1, NoteTypeWestern.D);
    pSeq2.addNote(new Note(0, 3, 3, 120));
    expected.add(pSeq2);

    //check that the two lists contain the same notes
    for (PitchSequence pSeq : model.getPitches()) {
      assertTrue(expected.contains(pSeq));
    }

    for (PitchSequence pSeq : expected) {
      assertTrue(model.getPitches().contains(pSeq));
    }
  }
}
