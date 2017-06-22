package cs3500.music.control;


import java.awt.event.MouseEvent;
import java.util.Map;

/**
 * Represents a strategy for handling mouse input.
 * This implementation for the music editor program  only handles mouse clicks.
 * All other methods remain as stubs.
 */
public class MouseHandler<M> implements MouseStrategy<MouseEventProcessor> {
  Map<Integer, MouseEventProcessor> mouseEvents;

  /**
   * Default empty constructor to construct an instance of MouseHandler.
   */
  public MouseHandler() {
    // empty
  }

  @Override
  public void setMouseEvents(Map<Integer, MouseEventProcessor> map) {
    this.mouseEvents = map;
  }

  @Override
  public Map<Integer, MouseEventProcessor> getMouseEvents() {
    return this.mouseEvents;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    this.run(mouseEvents, e);
  }

  @Override
  public void mousePressed(MouseEvent e) {
    // do nothing
    return;
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    // do nothing
    return;
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    // do nothing
    return;
  }

  @Override
  public void mouseExited(MouseEvent e) {
    // do nothing
    return;
  }

  /**
   * Runs the {@link MouseEventProcessor} associated with the given mouse event in the given map.
   * @param map map containing mouse event strategies
   * @param e the mouse event to act on
   */
  private void run(Map<Integer, MouseEventProcessor> map, MouseEvent e) {
    if (map.containsKey(e.getID())) {
      map.get(e.getID()).process(e);
    }
  }
}
