package cs3500.music.control;

import java.awt.event.MouseListener;
import java.util.Map;

/**
 * Interface to specify operations that must be performed
 * by a mouse listener in the Music Editor program to handle
 * user mouse input. Generic over the type of class to handle mouse events.
 */
public interface MouseStrategy<T> extends MouseListener {

  /**
   * Sets the mouse event strategy. Mapped from an integer representation of a mouse event
   * to a type T representing a class to handle input.
   * @param map mouse event strategy
   */
  void setMouseEvents(Map<Integer, T> map);

  /**
   * Gets the mouse event strategy. Mapped from an integer representation
   * of a mouse event to a type T representing a class to handle input.
   * @return mouse event strategy
   */
  Map<Integer, T> getMouseEvents();
}
