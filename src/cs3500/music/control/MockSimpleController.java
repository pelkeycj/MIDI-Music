package cs3500.music.control;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cs3500.music.model.MusicOperations;
import cs3500.music.model.Pitch;
import cs3500.music.view.IView;

/**
 * Mock controller to test mouse event handler. This mock controller
 */
public class MockSimpleController extends SimpleController {
  Appendable out;

  /**
   * Constructs a MockSimpleController.
   * @param out the appendable to write to
   * @param model the model to use
   * @param view the view to use
   */
  public MockSimpleController(Appendable out, MusicOperations model, IView... view) {
    super(model, view);
    this.out = out;
    this.setMockMouseStrategy(out);
  }

  /**
   * Sets the mock mouse strategy so that an appendable is used to ensure
   * that the mouse click is obtaining the proper pitch to add a note to.
   * @param out the appendable to write to
   */
  private void setMockMouseStrategy(Appendable out) {
    Map<Integer, MouseEventProcessor> mouseEvents = new HashMap<>();

    mouseEvents.put(MouseEvent.MOUSE_CLICKED, e -> {
      int x = e.getX();
      int y = e.getY();

      Pitch p = null;
      for (IView v : views) {
        p = v.getPitchAt(x, y);
        if (p != null) {
          break;
        }
      }

      if (p == null) {
        return;
      }

      try {
        out.append("Found pitch: ").append(p.toString()).append("\n");
      } catch (IOException exception) {
        throw new RuntimeException("Error appending information.");
      }
    });

    this.mouseStrategy.setMouseEvents(mouseEvents);
  }

  /**
   * Gets the mouse strategy used.
   * @return the mock mouse strategy
   */
  public MouseStrategy<MouseEventProcessor> getMouseStrategy() {
    return this.mouseStrategy;
  }
}
