package cs3500.music.test;

import org.junit.Before;
import org.junit.Test;

import cs3500.music.model.MusicOperations;
import cs3500.music.model.MusicSheet;
import cs3500.music.model.NoteTypeWestern;
import cs3500.music.model.OctaveNumber1To10;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the MusicSheet class.
 */
public class MIDITest {
  MusicOperations m1;
  MusicOperations m2;

  String addNoteString =
            "  C1   A#2 \n"
          + "0  X       \n"
          + "1  |    X  \n"
          + "2  |    |  \n"
          + "3  |       \n"
          + "4  |       \n"
          + "5  |       \n";

  String mergeSheetString =
            "  C1   A5  \n"
          + "0  X       \n"
          + "1  |       \n"
          + "2       X  \n"
          + "3       |  \n";

  String appendSheetString =
            "  C1   A5  \n"
          + "0  X       \n"
          + "1  |       \n"
          + "2          \n"
          + "3          \n"
          + "4       X  \n"
          + "5       |  \n";

  String longBoye =
            "   C1   A#1  D#5 \n"
          + " 0  X            \n"
          + " 1  |            \n"
          + " 2               \n"
          + " 3               \n"
          + " 4               \n"
          + " 5               \n"
          + " 6            X  \n"
          + " 7            |  \n"
          + " 8            X  \n"
          + " 9            |  \n"
          + "10       X    |  \n";





  /**
   * Initialize and reset fields.
   */
  @Before
  public void init() {
    m1 = new MusicSheet();
    m2 = new MusicSheet();
  }

  @Test
  // test add note
  public void testAddNote() {
    m1.addNote(OctaveNumber1To10.O1, NoteTypeWestern.C, 0, 5);
    m1.addNote(OctaveNumber1To10.O2, NoteTypeWestern.A_SHARP, 1, 2);
    assertEquals(addNoteString, m1.getSheet());
  }

  @Test(expected = IllegalArgumentException.class)
  // remove note does not exist
  public void testRemoveNonExistant() {
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
            0, 5, 0, 0);
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
    m1.addNote(OctaveNumber1To10.O1, NoteTypeWestern.A_SHARP, 10, 10);
    m1.addNote(OctaveNumber1To10.O5, NoteTypeWestern.D_SHARP, 6, 9);
    m2.addNote(OctaveNumber1To10.O5, NoteTypeWestern.D_SHARP, 8, 10);
    m1.mergeSheet(m2);

    assertEquals(longBoye, m1.getSheet());
  }

}