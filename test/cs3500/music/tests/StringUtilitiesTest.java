package cs3500.music.tests;

import org.junit.Test;

import cs3500.music.util.StringUtilities;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@link StringUtilities} class.
 */
public class StringUtilitiesTest {

  @Test
  // tests padLeft
  public void testPadLeft1() {
    assertEquals(" F", StringUtilities.padLeft("F", 1));
  }

  @Test
  //tests pad right
  public void testPadRight1() {
    assertEquals("F   ", StringUtilities.padRight("F", 3));
  }

  @Test(expected = IllegalArgumentException.class)
  // tests size to small
  public void testSizeToSmallCenter() {
    StringUtilities.center("help", 2);
  }

  @Test
  // tests center string
  public void testCenter() {
    assertEquals("  F  ", StringUtilities.center("F", 5));
  }

  @Test
  // tests center lopsided
  public void testCenterLopsided() {
    assertEquals(" C5  ", StringUtilities.center("C5", 5));
  }
}