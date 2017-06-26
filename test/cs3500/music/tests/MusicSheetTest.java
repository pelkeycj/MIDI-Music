package cs3500.music.tests;

import cs3500.music.model.Octave;
import cs3500.music.model.OctaveNumber0To10;
import cs3500.music.model.RepeatInstr;
import org.junit.Before;
import org.junit.Test;

import cs3500.music.model.MusicOperations;
import cs3500.music.model.MusicSheet;
import cs3500.music.model.NoteTypeWestern;
import cs3500.music.model.OctaveNumber1To10;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Tests for the MusicSheet class.
 */
public class MusicSheetTest {
  MusicOperations m1;
  MusicOperations m2;

  String addNoteString =
            "  C1   A#2 \n"
          + "0  X       \n"
          + "1  |    X  \n"
          + "2  |       \n"
          + "3  |       \n"
          + "4  |       \n";

  String mergeSheetString =
            "  C1   A5  \n"
          + "0  X       \n"
          + "1          \n"
          + "2       X  \n";

  String appendSheetString =
            "  C1   A5  \n"
          + "0  X       \n"
          + "1          \n"
          + "2          \n"
          + "3       X  \n";

  String longBoye =
            "   C1   A#1  D#5 \n"
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

  /**
   * Initialize and reset fields.
   */
  @Before
  public void init() {
    m1 = new MusicSheet();
    m2 = new MusicSheet();
  }

  @Test
  // tests add note
  public void testAddNote() {
    m1.addNote(OctaveNumber1To10.O1, NoteTypeWestern.C, 0, 5);
    m1.addNote(OctaveNumber1To10.O2, NoteTypeWestern.A_SHARP, 1, 2);
    assertEquals(addNoteString, m1.getSheet());
  }

  @Test(expected = IllegalArgumentException.class)
  // remove note does not exist
  public void testRemoveNonExistent() {
    m1.removeNote(OctaveNumber1To10.O2, NoteTypeWestern.C_SHARP, 0, 12);
  }

  @Test
  // remove
  public void testRemove() {
    m1.addNote(OctaveNumber1To10.O1, NoteTypeWestern.C, 0, 5);
    m1.addNote(OctaveNumber1To10.O2, NoteTypeWestern.A_SHARP, 1, 2);
    m1.removeNote(OctaveNumber1To10.O1, NoteTypeWestern.C, 0, 5);
    m1.removeNote(OctaveNumber1To10.O2, NoteTypeWestern.A_SHARP, 1, 2);
    assertEquals("", m1.getSheet());
  }



  @Test(expected = IllegalArgumentException.class)
  // change-> no such note
  public void testChangeNoNote() {
    m1.changeNote(OctaveNumber1To10.O1, NoteTypeWestern.A_SHARP, 0, 7,
            5, 10);
  }

  @Test
  // change note
  public void testChangeNote() {
    m1.addNote(OctaveNumber1To10.O2, NoteTypeWestern.C_SHARP, 0, 5);
    m1.changeNote(OctaveNumber1To10.O2, NoteTypeWestern.C_SHARP,
            0, 5, 0, 1);
    assertEquals("  C#2 \n0  X  \n", m1.getSheet());
  }

  @Test
  // merge sheets
  public void testMergeSheet() {
    m1.addNote(OctaveNumber1To10.O1, NoteTypeWestern.C, 0, 1);
    m2.addNote(OctaveNumber1To10.O5, NoteTypeWestern.A, 2, 3);

    m1.mergeSheet(m2);
    assertEquals(mergeSheetString, m1.getSheet());
  }

  @Test
  //append sheet
  public void testAppendSheet() {
    m1.addNote(OctaveNumber1To10.O1, NoteTypeWestern.C, 0, 1);
    m2.addNote(OctaveNumber1To10.O5, NoteTypeWestern.A, 2, 3);
    m1.appendSheet(m2);

    //note from n2 placed at 3-4 on m1
    assertEquals(appendSheetString, m1.getSheet());
  }

  @Test
  // get sheet
  public void testGetSheet() {
    m1.addNote(OctaveNumber1To10.O1, NoteTypeWestern.C, 0, 1);
    m1.addNote(OctaveNumber1To10.O1, NoteTypeWestern.A_SHARP, 10, 11);
    m1.addNote(OctaveNumber1To10.O5, NoteTypeWestern.D_SHARP, 6, 9);
    m2.addNote(OctaveNumber1To10.O5, NoteTypeWestern.D_SHARP, 8, 10);
    m1.mergeSheet(m2);

    assertEquals(longBoye, m1.getSheet());
  }

  //HW 9 TESTS
  @Test
  public void addRepeatDoesNotDisrupt() {
    m1.addNote(OctaveNumber1To10.O1, NoteTypeWestern.C, 0, 1);
    m1.addNote(OctaveNumber1To10.O1, NoteTypeWestern.A_SHARP, 10, 11);
    m1.addNote(OctaveNumber1To10.O5, NoteTypeWestern.D_SHARP, 6, 9);
    m2.addNote(OctaveNumber1To10.O5, NoteTypeWestern.D_SHARP, 8, 10);
    m1.mergeSheet(m2);

    m2.addRepeat(new RepeatInstr(0, 1));
    assertEquals(longBoye, m1.getSheet());

    String expection = "";

    try {
      m2.addRepeat(new RepeatInstr(0, 1000));
    } catch (IllegalArgumentException e) {
      expection = e.getMessage();
    }

    assertNotEquals("", expection);
  }

  @Test
  public void mergeRepeatsOverlap() {
    m1.addNote(OctaveNumber1To10.O1, NoteTypeWestern.C, 0, 1);
    m1.addNote(OctaveNumber1To10.O1, NoteTypeWestern.A_SHARP, 10, 11);
    m1.addNote(OctaveNumber1To10.O5, NoteTypeWestern.D_SHARP, 6, 9);
    m2.addNote(OctaveNumber1To10.O5, NoteTypeWestern.D_SHARP, 8, 10);

    m2.addRepeat(new RepeatInstr(0, 4));
    m1.addRepeat(new RepeatInstr(4, 5));

    String exception = "";
    try {
      m1.mergeSheet(m2);
    } catch (IllegalArgumentException e) {
      exception = e.getMessage();
    }

    assertNotEquals("", exception);
  }

  @Test
  public void addRepeatOutOfBounds() {
    String exception = "";
    try {
      m1.addRepeat(new RepeatInstr(0,1));
    } catch (IllegalArgumentException e) {
      exception = e.getMessage();
    }
    assertNotEquals("", exception);

    exception = "";
    m1.addNote(OctaveNumber0To10.O3, NoteTypeWestern.G, 0, 12);
    try {
      m1.addRepeat(new RepeatInstr(0,13));
    } catch (IllegalArgumentException e) {
      exception = e.getMessage();
    }
    assertNotEquals("", exception);
  }

}