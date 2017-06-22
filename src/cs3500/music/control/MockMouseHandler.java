package cs3500.music.control;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Returns a set of {@link MouseEventProcessor} objects that do not actually act on the
 * view/controller but instead print a message onto an {@link Appendable} so that
 * testers can check that the correct action has been run.
 */
public class MockMouseHandler {

  /**
   * Gets a map of {@link Integer}s to {@link MouseEventProcessor} objects that
   * match the {@link MouseEventProcessor}s used in the real controller.
   * @param out the appendable to write to
   * @return a map of mock mouse event processors
   */
  public static Map<Integer, MouseEventProcessor> getMouseEvents(Appendable out) {
    Map<Integer, MouseEventProcessor> mouseClicks = new HashMap<>();
    mouseClicks.put(MouseEvent.MOUSE_CLICKED, new AddNote(out));

    return mouseClicks;
  }

  /**
   * Mocks an object that adds a note on a mouse event e.
   */
  private static class AddNote implements MouseEventProcessor {
    Appendable out;

    AddNote(Appendable out) {
      this.out = out;
    }

    @Override
    /**
     * 'Processes' mouse event and prints a message to the Appendable.
     */
    public void process(MouseEvent e) {
      try {
        out.append("Add note\n");
      }
      catch (IOException error) {
        throw new RuntimeException("append to out failed.");
      }
    }
  }
}
