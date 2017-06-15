package cs3500.music.test;

import org.junit.Test;

import cs3500.music.util.StringUtilities;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@link StringUtilities} class.
 */
public class StringUtilitiesTest {

  @Test
  // test padLeft
  public void testPadLeft1() {
    assertEquals(" F", StringUtilities.padLeft("F", 1));
  }

  @Test
  //test pad right
  public void testPadRight1() {
    assertEquals("F   ", StringUtilities.padRight("F", 3));
  }

  @Test(expected = IllegalArgumentException.class)
  // test size to small
  public void testSizeToSmallCenter() {
    StringUtilities.center("help", 2);
  }

  @Test
  // test center string
  public void testCenter() {
    assertEquals("  F  ", StringUtilities.center("F", 5));
  }

  @Test
  // test center lopsided
  public void testCenterLopsided() {
    assertEquals(" C5  ", StringUtilities.center("C5", 5));
  }
}