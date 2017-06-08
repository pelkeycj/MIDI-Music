package cs3500.music.model;

import org.junit.Before;
import org.junit.Test;

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
    tiny = new Note(0, 0);
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
  // end less than start
  public void testBadConstructor3() {
    new Note(2,1);
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
    assertEquals("X|||||", small.toString());
  }

  @Test
  public void testCompareTo() {
    assertTrue(tiny.compareTo(small) < 0);
    assertTrue(big.compareTo(small) > 0);
  }

  @Test
  public void testEquals() {
    assertTrue(tiny.equals(tiny));
    assertTrue(tiny.equals(new Note(0,0)));
    assertFalse(tiny.equals(big));
  }

  @Test
  public void testHashCode() {
    assertEquals(0, tiny.hashCode());
    assertEquals(5, small.hashCode());
  }

}