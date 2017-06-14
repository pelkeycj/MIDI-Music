package cs3500.music.view;

import com.sun.corba.se.impl.orbutil.graph.Graph;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;

import cs3500.music.model.PitchSequence;

/**
 * Displays the sheet of music.
 */


//TODO JScrollPane
//TODO test that this draws correctly
  //TODO set current beat method : draw red line
public class SheetPanel extends JPanel {
  // constants subject to change TODO
  private final int DEFAULT_WIDTH = 1000;
  private final int DEFAULT_HEIGHT = 250;

  private final int BEAT_WIDTH = 20;
  private final int BEAT_HEIGHT = 10;
  private final int MEASURE_WIDTH = BEAT_WIDTH * 4;

  private final int BORDER_WIDTH = 1;

  private final int BEAT_BORDER_HEIGHT = BEAT_HEIGHT + 2 * BORDER_WIDTH;
  private final int MEASURE_BORDER_WIDTH = MEASURE_WIDTH + 2 * BORDER_WIDTH;
  private final int MEASURE_BORDER_HEIGHT = BEAT_BORDER_HEIGHT;


  private final int SHEET_START_X = 50; // x,y coords of top left corner of sheet
  private final int SHEET_START_Y = 50;

  private List<PitchSequence> pitches;
  private int lastBeat;
  private int panelWidth;
  private int panelHeight;

  /**
   * Constructs a sheet panel.
   */
  //TODO sizing, measures, headers, beats
  public SheetPanel() {
    this.pitches = new ArrayList<PitchSequence>();
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g;
    g2d.setBackground(Color.white);

    this.lastBeat = getLastBeat();
    this.drawBeatCount(g2d);

    Collections.sort(pitches);
    Collections.reverse(this.pitches); // so that higher pitches are on top
    for (int i = 0; i < this.pitches.size(); i++) {
      this.drawPitch(g2d, this.pitches.get(i), i);
    }
  }

  /**
   * Sets the notes for this panel to display.
   * @param pitches the sheet of notes
   */
  public void setNotes(List<PitchSequence> pitches) {
    this.pitches = pitches;
  }

  /**
   * Draws the beat count above the sheet of notes.
   * @param g2d the 2d graphics object to draw on
   */
  private void drawBeatCount(Graphics2D g2d) {
    int numMeasures = (int) Math.ceil(this.lastBeat / 4);

    g2d.setColor(Color.black);
    for (int i = 0; i < numMeasures; i++) {
      g2d.drawString((i * 4) + "", SHEET_START_X + MEASURE_BORDER_WIDTH * i,
              SHEET_START_Y - 10);
    }
  }


  /**
   * Draws a pitch to the surface.
   * @param g2d the graphics 2d object to draw to
   * @param p the pitch to render
   * @param row the row to place the pitch at
   */
  private void drawPitch(Graphics2D g2d, PitchSequence p, int row) {
    //TODO correct Y offset?

    g2d.setColor(Color.black);
    g2d.drawString(p.getHeader(), 5, SHEET_START_Y + (row + 1) * BEAT_BORDER_HEIGHT);

    // place measures
    this.drawMeasures(g2d, row);

    // draw out pitch based on pitch string and last beat
    String pitchString = p.toString();

    for (int i = 0; i < pitchString.length(); i++) {
      this.drawBeat(g2d, pitchString.charAt(i), i, row);
    }
  }


  /**
   * Draws a beat based on the character passed.
   * 'X' indicates a black beat, '|' indicates green, otherwise white.
   * @param g2d the graphics 2d object to draw to
   * @param c the character representing the beat
   * @param beat the index of the beat
   * @param row the row of pitches being drawn
   */
  private void drawBeat(Graphics2D g2d, char c, int beat, int row) {
    int measureNum = beat / 4;
    int remainingBeats = beat - measureNum * 4;

    int xPos = SHEET_START_X + MEASURE_BORDER_WIDTH * measureNum
            + BEAT_WIDTH * remainingBeats;
    int yPos = SHEET_START_Y + BEAT_BORDER_HEIGHT * row;

    Color color;

    switch (c) {
      case 'X':
        color = Color.black;
        break;
      case '|':
        color = Color.green;
        break;
      default:
        color = Color.white;
    }

    g2d.setColor(color);
    g2d.fillRect(xPos, yPos, BEAT_WIDTH, BEAT_HEIGHT);
  }

  /**
   * Draws the outlines of the measures at the specified location.
   * @param g2d the 2d graphics object to draw to
   * @param row the row to draw at
   */
  private void drawMeasures(Graphics2D g2d, int row) {
    int numMeasures = (int) Math.ceil(this.lastBeat / 4);
    int xPos;
    int yPos;

    for (int i = 0; i < numMeasures; i++) {
      xPos = SHEET_START_X + i * MEASURE_BORDER_WIDTH;
      yPos = SHEET_START_Y + row * BEAT_BORDER_HEIGHT;

      // draw black outline
      g2d.setColor(Color.black);
      g2d.fillRect(xPos, yPos, MEASURE_BORDER_WIDTH, BEAT_BORDER_HEIGHT);

      // draw white inside
      xPos++;
      yPos++;
      g2d.setColor(Color.white);
      g2d.fillRect(xPos, yPos, MEASURE_WIDTH, BEAT_HEIGHT);
    }
  }

  /**
   * Determines the index of the last beat
   * for the sheet of music to be displayed.
   * @return index of last beat
   */
  private int getLastBeat() {
    int lastBeat = 0;
    for (PitchSequence p : this.pitches) {
      if (p.getLastBeat() > lastBeat) {
        lastBeat = p.getLastBeat();
      }
    }
    return lastBeat;
  }

}
