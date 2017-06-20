package cs3500.music.control;

import java.awt.event.KeyEvent;
import java.util.Map;

/**
 * Represents a strategy for handling keyboard input.
 * Contains maps from {@code Integer}s to {@code Runnable}s so that
 * a client can set their own strategy.
 */
public class KeyHandler implements KeyStrategy {
  Map<Integer, Runnable> keyTypes;
  Map<Integer, Runnable> keyPresses;
  Map<Integer, Runnable> keyReleases;

  /**
   * Default empty constructor to construct an instance of KeyHandler.
   */
  public KeyHandler() {
    // defualt empty constructor
  }


  @Override
  public void keyPressed(KeyEvent e) {
    this.run(this.keyPresses, e);
  }

  @Override
  public void keyTyped(KeyEvent e) {
    this.run(this.keyTypes, e);
  }

  @Override
  public void keyReleased(KeyEvent e) {
    this.run(this.keyReleases, e);
  }

  @Override
  public void setKeyTypedStrategy(Map<Integer, Runnable> map) {
    this.keyTypes = map;
  }

  @Override
  public void setKeyPressedStrategy(Map<Integer, Runnable> map) {
    this.keyPresses = map;
  }

  @Override
  public void setKeyReleasedStrategy(Map<Integer, Runnable> map) {
    this.keyReleases = map;
  }

  /**
   * Runs the {@link Runnable} associated with the given key event in the given map.
   * @param map the map containing key strategies
   * @param e the key event to act on
   */
  private void run(Map<Integer, Runnable> map, KeyEvent e) {
    if (map.containsKey(e.getKeyCode())) {
      map.get(e.getKeyCode()).run();
    }
  }
}
