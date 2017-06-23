package cs3500.music.control;

import java.awt.event.MouseEvent;

import javax.swing.JPanel;

/**
 * Mock mouse event to be used in testing. This class does nothing.
 */
public class MockMouseEvent extends MouseEvent {
  public MockMouseEvent() {
    super(new JPanel(), 0, 0, 0, 0, 0, 0, false);
  }
}
