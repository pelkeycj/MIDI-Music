package cs3500.music.control;

import cs3500.music.model.Pitch;
import cs3500.music.view.IView;
import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 * Created by adambechtold on 6/22/17.
 */
public class MockMouseGetPitch implements MouseEventProcessor {

  Appendable out;
  IView[] views;

  public MockMouseGetPitch(Appendable out, IView... views) {
    this.out = out;
    this.views = views;
  }

  @Override
  public void process(MouseEvent e) {
    int x = e.getX();
    int y = e.getY();

    Pitch p = null;
    for (IView v : views) {
      p = v.getPitchAt(x, y);
      if (p != null) {
        break;
      }
    }

    try {
      out.append("Found pitch: ").append(p.toString());
    } catch (IOException exception) {
      throw new RuntimeException("Error appending information.");
    }
  }
}
