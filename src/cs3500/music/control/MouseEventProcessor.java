package cs3500.music.control;

import java.awt.event.MouseEvent;

/**
 * An interface to specify operations to be performed by classes that must
 * process a mouse event.
 */
public interface MouseEventProcessor {

  /**
   * Processes event {@code e}.
   * @param e the mouse event to process
   */
  void process(MouseEvent e);
}
