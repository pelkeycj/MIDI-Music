package cs3500.music.view;
import javax.swing.*;


/**
 * A scrollable panel to display a music sheet.
 */
public class ScrollingSheet extends JScrollPane {

  public ScrollingSheet(JPanel panel) {
    super(panel);
    setLayout(new ScrollPaneLayout());
   // this.horizontalScrollBarPolicy = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;
  }

}
