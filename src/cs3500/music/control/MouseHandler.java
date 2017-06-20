package cs3500.music.control;


import java.awt.event.MouseEvent;
import java.util.Map;

/**
 * Represents a strategy for handling keyboard input.
 * Contains maps from {@code Integer}s to {@code Runnable}s so that
 * a client can set their own strategy.
 */
public class MouseHandler implements MouseStrategy {
  Map<Integer, Runnable> mouseClicks;
  Map<Integer, Runnable> mousePresses;
  Map<Integer, Runnable> mouseEnters;
  Map<Integer, Runnable> mouseExits;
  Map<Integer, Runnable> mouseReleases;

  /**
   * Default empty constructor to construct an instance of MouseHandler.
   */
  public MouseHandler() {
    // empty
  }

  @Override
  public void setMouseClicks(Map<Integer, Runnable> map) {
    this.mouseClicks = map;
  }

  @Override
  public void setMousePresses(Map<Integer, Runnable> map) {
    this.mousePresses = map;
  }

  @Override
  public void setMouseEnters(Map<Integer, Runnable> map) {
    this.mouseEnters = map;
  }

  @Override
  public void setMouseExits(Map<Integer, Runnable> map) {
    this.mouseExits = map;
  }

  @Override
  public void setMouseReleased(Map<Integer, Runnable> map) {
    this.mouseReleases = map;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    this.run(this.mouseClicks, e);
  }

  @Override
  public void mousePressed(MouseEvent e) {
    this.run(this.mousePresses, e);
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    this.run(this.mouseReleases, e);
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    this.run(this.mouseEnters, e);
  }

  @Override
  public void mouseExited(MouseEvent e) {
    this.run(this.mouseExits, e);
  }

  /**
   * Runs the {@link Runnable} associated with the given mouse event in the given map.
   * @param map map containing mouse event strategies
   * @param e the mouse event to act on
   */
  private void run(Map<Integer, Runnable> map, MouseEvent e) {
    if (map.containsKey(e.getID())) {
      map.get(e.getID()).run();
    }
  }

}
