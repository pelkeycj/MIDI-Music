package cs3500.music.tests;

import org.junit.Before;
import org.junit.Test;

import cs3500.music.model.MusicOperations;
import cs3500.music.model.MusicSheet;
import cs3500.music.model.NoteTypeWestern;
import cs3500.music.model.OctaveNumber0To10;
import cs3500.music.model.OctaveNumber1To10;
import cs3500.music.view.IView;
import cs3500.music.view.TextView;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link cs3500.music.view.TextView}.
 */
public class TextViewTest {
  IView textView;
  MusicOperations m;
  StringBuilder b;

  String output =
            "   C0   A#1  D#5 \n"
          + " 0  X            \n"
          + " 1               \n"
          + " 2               \n"
          + " 3               \n"
          + " 4               \n"
          + " 5               \n"
          + " 6            X  \n"
          + " 7            |  \n"
          + " 8            X  \n"
          + " 9            |  \n"
          + "10       X       \n";

  @Before
  /**
   * Initializes and resets fields.
   */
  public void init() {
    b = new StringBuilder();
    textView = new TextView(b);
    m = new MusicSheet();
  }

  @Test
  public void testEmptyRender() {
    textView.setNotes(m.getPitches());
    assertEquals("", b.toString());
  }

  @Test
  public void testRender() {
    m.addNote(OctaveNumber0To10.O0, NoteTypeWestern.C, 0, 1);
    m.addNote(OctaveNumber1To10.O1, NoteTypeWestern.A_SHARP, 10, 11);
    m.addNote(OctaveNumber1To10.O5, NoteTypeWestern.D_SHARP, 6, 9);
    m.addNote(OctaveNumber1To10.O5, NoteTypeWestern.D_SHARP, 8, 10);

    textView.setNotes(m.getPitches());
    assertEquals(output, b.toString());
  }


}