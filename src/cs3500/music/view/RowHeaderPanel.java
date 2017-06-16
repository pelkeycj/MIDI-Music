package cs3500.music.view;


import javax.swing.*;

import java.awt.*;
import java.util.Collections;
import java.util.List;

import javax.swing.*;

import cs3500.music.model.PitchSequence;

import static cs3500.music.util.ViewConstants.MEASURE_BORDER_HEIGHT;
import static cs3500.music.util.ViewConstants.SHEET_START_X;
import static cs3500.music.util.ViewConstants.SHEET_START_Y;

/**
 * Panel to display row header information for a JScrollPane
 */
public class RowHeaderPanel extends JPanel {
  private final int X_OFFSET = 5;
  private List<PitchSequence> pitches;

  public RowHeaderPanel(List<PitchSequence> pitches) {
    this.pitches = pitches;
    this.setPreferredSize(new Dimension(SHEET_START_X, this.getYDimension()));
  }

  @Override
  public void paintComponent(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;

    g2d.setColor(Color.black);
    g2d.setFont(new Font("TimesRoman", Font.PLAIN, 12));
    PitchSequence p;

    Collections.sort(this.pitches);
    Collections.reverse(this.pitches);
    for (int i = 0; i < this.pitches.size(); i++) {
      p = this.pitches.get(i);
      g2d.drawString(p.getHeader(), X_OFFSET,
              SHEET_START_Y + (i + 1) * MEASURE_BORDER_HEIGHT - 4);
    }
  }

  /**
   * Gets the required size of the panel to fit all pitch headers.
   * @return the required Y dimension
   */
  private int getYDimension() {
    return SHEET_START_Y + this.pitches.size() * MEASURE_BORDER_HEIGHT;
  }
}
