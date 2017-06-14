package cs3500.music.view;

import java.awt.*;

import javax.swing.*;

/**
 * A scrollable panel to display a music sheet.
 */
public class ScrollingSheet extends JScrollPane {

  public ScrollingSheet(JPanel panel) {
    super(panel);
    setLayout(new ScrollPaneLayout());
    this.horizontalScrollBarPolicy = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;
  }

  // get header from sheetpanel?
    // or build separately?

  // get pitch info from sheetpanel?
      // or build separately

      // should sheetpanel just build basic sheet?
}
