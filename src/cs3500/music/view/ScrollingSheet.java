package cs3500.music.view;
import javax.swing.*;


/**
 * A scrollable panel to display a music sheet. This object is a kind of JPanel that allows for
 * horizontal scrolling and wraps around another panel containing the image of interest.
 */
public class ScrollingSheet extends JScrollPane {

  /**
   * Public constructor for a scrolling panel.
   * @param panel the panel containing the picture to display in the scrollable panel.
   */
  public ScrollingSheet(JPanel panel) {
    super(panel);
    setLayout(new ScrollPaneLayout());
    this.horizontalScrollBarPolicy = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;
  }
}
