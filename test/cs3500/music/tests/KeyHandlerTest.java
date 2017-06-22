package cs3500.music.tests;

import org.junit.Before;
import org.junit.Test;

import java.awt.event.KeyEvent;
import java.util.Map;

import cs3500.music.control.KeyHandler;
import cs3500.music.control.MockKeyboardHandler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Tests for the {@link KeyHandler} class.
 */
public class KeyHandlerTest {
  StringBuilder out;
  MockKeyboardHandler mock;
  Map<Integer, Runnable> keyPresses;

  @Before
  /**
   * Resets and Initializes fields.
   */
  public void init() {
    out = new StringBuilder();
    keyPresses = MockKeyboardHandler.getKeyPressesMap(out);
  }

  @Test
  public void testRightLeftChangeBeat() {
    keyPresses.get(KeyEvent.VK_LEFT).run();
    keyPresses.get(KeyEvent.VK_RIGHT).run();
    assertEquals("Change beat by -1.\nChange beat by 1.\n",
            out.toString());
  }

  @Test
  public void testSpaceBarTogglesPlay() {
    keyPresses.get(KeyEvent.VK_SPACE).run();
    assertEquals("Pause if playing. Start playing if paused.\n",
            out.toString());
  }

  @Test
  public void testEnterEndBringsToLastBeat() {
    keyPresses.get(KeyEvent.VK_END).run();
    keyPresses.get(KeyEvent.VK_ENTER).run();
    assertEquals("Go to the end of the piece.\nGo to the end of the piece.\n",
            out.toString());
  }

  @Test
  public void testBackspaceHomeBringsToStart() {
    keyPresses.get(KeyEvent.VK_BACK_SPACE).run();
    keyPresses.get(KeyEvent.VK_HOME).run();
    assertEquals("Back to start.\nBack to start.\n",
            out.toString());
  }

  @Test
  public void testUpDownScrolls() {
    keyPresses.get(KeyEvent.VK_DOWN).run();
    keyPresses.get(KeyEvent.VK_UP).run();
    assertEquals("Scroll down.\nScroll up.\n",
            out.toString());
  }

  @Test
  public void testInvalidKeyDoesNothing() {
    assertFalse(keyPresses.containsKey(KeyEvent.VK_H));
  }
}