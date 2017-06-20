package cs3500.music.control;


import java.awt.event.KeyListener;
import java.util.Map;


/**
 * Interface to represent operations that must be performed
 * by a key listener in the Music Editor program
 * to handle user input. Allows a user to set their own strategies
 * to handle keys.
 */
public interface KeyStrategy extends KeyListener {

  /**
   * Sets the strategy to use for typed keys.
   * @param map the strategy to use
   */
  void setKeyTypedStrategy(Map<Integer, Runnable> map);

  /**
   * Sets the strategy to use for pressed keys.
   * @param map the strategy to use
   */
  void setKeyPressedStrategy(Map<Integer, Runnable> map);

  /**
   * Sets the strategy to use for released keys.
   * @param map the strategy to use
   */
  void setKeyReleasedStrategy(Map<Integer, Runnable> map);
}
