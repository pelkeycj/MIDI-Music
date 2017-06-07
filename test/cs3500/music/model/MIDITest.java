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


  //TODO
  // change note has
  //change note doesnt have


  //TODO
  //merge sheet

  //TODO
  // append sheet

  //TODO
  // getSheet
}