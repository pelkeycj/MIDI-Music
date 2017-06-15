package cs3500.music.test;

import org.junit.Before;
import org.junit.Test;

import cs3500.music.model.Note;
import cs3500.music.model.NoteTypeWestern;
import cs3500.music.model.OctaveNumber1To10;
import cs3500.music.model.PitchSequence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Tests for the {@link PitchSequence} class.
 */
public class PitchSequenceTest {
  PitchSequence p1D;
  PitchSequence p1A;
  PitchSequence p2C;

  /**
   * Initialize and reset.
   */
  @Before
  public void init() {
    p1D = new PitchSequence(OctaveNumber1To10.O1, NoteTypeWestern.D);
    p1A = new PitchSequence(OctaveNumber1To10.O1, NoteTypeWestern.A);
    p2C = new PitchSequence(OctaveNumber1To10.O2, NoteTypeWestern.C);
  }

  @Test
  //Add note
  public void testAddNote() {
    Note n = new Note(0, 1);
    assertTrue(p1A.isEmpty());
    p1A.addNote(n);
    assertFalse(p1A.isEmpty());
    assertEquals(1, p1A.getLastBeat());
  }

  @Test(expected = IllegalArgumentException.class)
  // add note -> already exists
  public void testAddNoteFails() {
    p1A.addNote(new Note(0, 1));
    p1A.addNote(new Note(0, 1));
  }

  @Test(expected = IllegalArgumentException.class)
  // remove note that does not exist
  public void testRemoveFail() {
    Note n = new Note(5, 100);
    p1A.addNote(n);
    p1A.removeNote(new Note(0, 1));
  }

  @Test
  //remove note
  public void testRemovePass() {
    Note n = new Note(0, 1);
    p1D.addNote(n);
    p1D.removeNote(n);
    assertTrue(p1D.isEmpty());
  }

  @Test
  // empty toString()
  public void testEmptyToString() {
    assertEquals("", p1D.toString());
  }

  @Test
  // overlap
  public void testOverlapToString() {
    p1D.addNote(new Note(0, 2)).addNote(new Note(1, 3));
    assertEquals("XX||", p1D.toString());
  }

  @Test
  // no overlap
  public void testNoOverlapToString() {
    p1D.addNote(new Note(1,2)).addNote(new Note(3, 3));
    assertEquals(" X|X", p1D.toString());
  }

  @Test
  //equals
  public void testEquals() {
    assertFalse(p1A.equals(p1D));
    assertTrue(p1A.equals(new PitchSequence(OctaveNumber1To10.O1, NoteTypeWestern.A)));
  }

  @Test
  //hashcode == the hashcode of header for the pitch sequence
  public void testHashCode() {
    assertEquals("A1".hashCode(), p1A.hashCode());
  }

  @Test
  //getHeader
  public void testGetHeader() {
    assertEquals("A1", p1A.getHeader());
  }

  @Test
  //test compareTO
  public void testCompareTo() {
    assertTrue(p1A.compareTo(p2C) < 0);
    assertTrue(p1A.compareTo(p1D) > 0);
  }

  @Test(expected = IllegalArgumentException.class)
  // test merge with bad args
  public void testMergeInvalidDelta() {
    p1D.addAll(new PitchSequence(OctaveNumber1To10.O1, NoteTypeWestern.D), -9);
  }

  @Test
  //test valid merge
  public void testMergeValidDelta() {
    p1D.addNote(new Note(0,1));
    p1A.addNote(new Note(0,1));
    p1D.addAll(p1A, 2);
    assertEquals("X|X|", p1D.toString());
  }

}