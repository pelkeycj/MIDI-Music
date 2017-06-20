package cs3500.music.control;

import java.awt.event.MouseListener;
import java.util.Map;

/**
 * Interface to specify operations that must be performed
 * by a mouse listener in the Music Editor program to handle
 * user mouse input.
 */
public interface MouseStrategy extends MouseListener {

  /**
   * Sets the mouse click strategy.
   * @param map mouse click strategy
   */
  void setMouseClicks(Map<Integer, Runnable> map);

  /**
   * Sets the mouse presses strategy.
   * @param map mouse presses strategy
   */
  void setMousePresses(Map<Integer, Runnable> map);

  /**
   * Sets the mouse enters strategy.
   * @param map mouse enters strategy
   */
  void setMouseEnters(Map<Integer, Runnable> map);

  /**
   * Sets the mouse exits strategy.
   * @param map mouse exits strategy
   */
  void setMouseExits(Map<Integer, Runnable> map);

  /**
   * Sets the mouse releases strategy.
   * @param map mouse releases strategy
   */
  void setMouseReleased(Map<Integer, Runnable> map);

}
