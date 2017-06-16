package cs3500.music.tests;

import org.junit.Before;
import org.junit.Test;

import cs3500.music.model.Note;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Tests for the {@link Note} class.
 */
public class NoteTest {
  Note tiny;
  Note small;
  Note big;

  /**
   * Initializes and resets fields.
   */
  @Before
  public void init() {
    tiny = new Note(0, 1);
    small = new Note(0, 5);
    big = new Note(1, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  // start too small
  public void testBadConstruction1() {
    new Note(-1, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  //end too small
  public void testBadConstruction2() {
    new Note(2, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  // invalid instrument and loudness
  public void testBadConstructor4() {
    new Note(0, 10, 0, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  // invalid instrument
  public void testBadConstructor5() {
    new Note(0, 1, 0, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  // invalid loudness
  public void testBadConstructor6() {
    new Note(0, 1, 1, 130);
  }

  @Test
  public void testGetStart() {
    assertEquals(0, tiny.getStart());
  }

  @Test
  public void testGetEnd() {
    assertEquals(10, big.getEnd());
  }

  @Test
  public void testToString() {
    assertEquals("X", tiny.toString());
    assertEquals("X||||", small.toString());
  }

  @Test
  public void testCompareTo() {
    assertTrue(tiny.compareTo(small) < 0);
    assertTrue(big.compareTo(small) > 0);
  }

  @Test
  public void testEquals() {
    assertTrue(tiny.equals(tiny));
    assertTrue(tiny.equals(new Note(0,1)));
    assertFalse(tiny.equals(big));
  }

  @Test
  public void testHashCode() {
    assertEquals(1, tiny.hashCode());
    assertEquals(5, small.hashCode());
  }

}