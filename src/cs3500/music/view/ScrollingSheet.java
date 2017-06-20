package cs3500.music.view;

import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.ScrollPaneLayout;
import javax.swing.ScrollPaneConstants;
import javax.swing.JScrollBar;


/**
 * A scrollable panel to display a music sheet. This object is a kind of JPanel that allows for
 * horizontal scrolling and wraps around another panel containing the image of interest.
 */
public class ScrollingSheet extends JScrollPane {
  private final int SCROLL_DELTA = 20;

  /**
   * Public constructor for a scrolling panel.
   * @param panel the panel containing the picture to display in the scrollable panel.
   */
  public ScrollingSheet(JPanel panel) {
    super(panel);
    setLayout(new ScrollPaneLayout());
    this.horizontalScrollBarPolicy = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;
  }

  /**
   * Scrolls the vertical bar if possible.
   * @param direction the direction to scroll (< 0 for up, > 0 for down)
   */
  public void scrollVertical(int direction) {
    JScrollBar sb = this.verticalScrollBar;
    if (direction < 0) {
      sb.setValue(sb.getValue() - SCROLL_DELTA);
    }
    else if (direction > 0) {
      sb.setValue(sb.getValue() + SCROLL_DELTA);
    }
  }
}
