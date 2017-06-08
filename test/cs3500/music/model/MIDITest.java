package cs3500.music.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for the {@link MIDI} class.
 */
public class MIDITest {
  MIDI m1;
  MIDI m2;

  String addNoteString =
            "  C1   A#2 \n"
          + "0  X       \n"
          + "1  |    X  \n"
          + "2  |    |  \n"
          + "3  |       \n"
          + "4  |       \n"
          + "5  |       \n";

  String mergeSheetString =
            "  C1    A5  \n"
          + "0  X        \n"
          + "1  |        \n"
          + "2        X  \n"
          + "3        |  \n";

  String appendSheetString =
            "  C1   A#2 \n"
          + "0  X       \n"
          + "1  |       \n"
          + "2          \n"
          + "3          \n"
          + "4       X  \n"
          + "5       |  \n";


  /**
   * Initialize and reset fields.
   */
  @Before
  public void init() {
    m1 = new MIDI();
    m2 = new MIDI();
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
    assertEquals("  C#2 \n0  X  ", m1.getSheet());
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
    m1.mergeSheet(m2);

    //note from n2 placed at 3-4 on m1
    assertEquals(appendSheetString, m1.getSheet());
  }
}