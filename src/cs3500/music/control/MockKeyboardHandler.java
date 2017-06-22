package cs3500.music.control;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Returns sets of mock Runnable objects that don't actually act on the views/controller,
 * but instead print a message onto an Appendable so that testers can check that the correct
 * action has been run.
 */
public class MockKeyboardHandler {

  /**
   * Creates a map of integers and runnable objects that match the runnables used in the
   * real controller's keyTypes map
   * @param out Appendable object to record output from the runnable objects.
   * @return a map of the mock runnable objects.
   */
  public static Map<Integer, Runnable> getKeyTypesMap(Appendable out) {
    Map<Integer, Runnable> keyTypes = new HashMap<>();
    return keyTypes;
  }

  /**
   * Creates a map of integers and runnable objects that match the runnables used in the
   * real controller's keyReleases map
   * @param out Appendable object to record output from the runnable objects.
   * @return a map of the mock runnable objects.
   */
  public static Map<Integer, Runnable> getKeyReleases(Appendable out) {
    Map<Integer, Runnable> keyReleases = new HashMap<>();
    return keyReleases;
  }

  /**
   * Creates a map of integers and runnable objects that match the runnables used in the
   * real controller's keyPresses map
   * @param out Appendable object to record output from the runnable objects.
   * @return a map of the mock runnable objects.
   */
  public static Map<Integer, Runnable> getKeyPressesMap(Appendable out) {
    Map<Integer, Runnable> keyPresses = new HashMap<>();

    keyPresses.put(KeyEvent.VK_LEFT, new BackOneBeat(out));
    keyPresses.put(KeyEvent.VK_RIGHT, new ForwardOneBeat(out));
    keyPresses.put(KeyEvent.VK_SPACE, new PausePlay(out));
    keyPresses.put(KeyEvent.VK_BACK_SPACE, new BackToStart(out));
    keyPresses.put(KeyEvent.VK_HOME, new BackToStart(out));
    keyPresses.put(KeyEvent.VK_ENTER, new ToEnd(out));
    keyPresses.put(KeyEvent.VK_END, new ToEnd(out));
    keyPresses.put(KeyEvent.VK_UP, new ScrollUp(out));
    keyPresses.put(KeyEvent.VK_DOWN, new ScrollDown(out));

    return keyPresses;
  }

  /**
   * Mocks an object that moves a controller back one beat.
   */
  private static class BackOneBeat implements Runnable {
    Appendable out;

    BackOneBeat(Appendable out) {
      this.out = out;
    }

    /**
     * Run this mock object and print its message onto the Appendable object.
     */
    public void run() {
      try {
        out.append("Change beat by -1.\n");
      } catch (IOException e) {
        throw new RuntimeException("append to out failed.");
      }
    }
  }

  /**
   * Mocks an object that moves a controller forward one beat.
   */
  private static class ForwardOneBeat implements Runnable {
    Appendable out;

    ForwardOneBeat(Appendable out) {
      this.out = out;
    }

    /**
     * Run this mock object and print its message onto the Appendable object.
     */
    public void run() {
      try {
        out.append("Change beat by 1.\n");
      } catch (IOException e) {
        throw new RuntimeException("append to out failed.");
      }
    }
  }

  /**
   * Mocks an object that toggles the playing state of a controller.
   */
  private static class PausePlay implements Runnable {
    Appendable out;

    PausePlay(Appendable out) {
      this.out = out;
    }

    /**
     * Run this mock object and print its message onto the Appendable object.
     */
    public void run() {
      try {
        out.append("Pause if playing. Start playing if paused.\n");
      } catch (IOException e) {
        throw new RuntimeException("append to out failed.");
      }
    }
  }

  /**
   * Mocks an object that moves the controller back to the starting beat.
   */
  private static class BackToStart implements Runnable {
    Appendable out;

    BackToStart(Appendable out) {
      this.out = out;
    }

    /**
     * Run this mock object and print its message onto the Appendable object.
     */
    public void run() {
      try {
        out.append("Back to start.\n");
      } catch (IOException e) {
        throw new RuntimeException("append to out failed.");
      }
    }
  }

  /**
   * Mocks an object that moves a controller back to the first beat of peice.
   */
  private static class ToEnd implements Runnable {
    Appendable out;

    ToEnd(Appendable out) {
      this.out = out;
    }

    /**
     * Run this mock object and print its message onto the Appendable object.
     */
    public void run() {
      try {
        out.append("Go to the end of the piece.\n");
      } catch (IOException e) {
        throw new RuntimeException("append to out failed.");
      }
    }
  }

  /**
   * Mocks an object that scrolls a gui view upward.
   */
  private static class ScrollUp implements Runnable {
    Appendable out;

    ScrollUp(Appendable out) {
      this.out = out;
    }

    /**
     * Run this mock object and print its message onto the Appendable object.
     */
    public void run() {
      try {
        out.append("Scroll up.\n");
      } catch (IOException e) {
        throw new RuntimeException("append to out failed.");
      }
    }
  }

  /**
   * Mocks an object the scrolls a gui view downward.
   */
  private static class ScrollDown implements Runnable {
    Appendable out;

    ScrollDown(Appendable out) {
      this.out = out;
    }

    /**
     * Run this mock object and print its message onto the Appendable object.
     */
    public void run() {
      try {
        out.append("Scroll down.\n");
      } catch (IOException e) {
        throw new RuntimeException("append to out failed.");
      }
    }
  }

}
