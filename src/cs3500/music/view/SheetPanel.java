package cs3500.music.view;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.*;

import cs3500.music.model.PitchSequence;

import static cs3500.music.util.ViewConstants.*;

/**
 * Displays the sheet of music. Shows a cursor representing the current beat
 * and scrolls the sheet of music along with the moving cursor after beat 20.
 */
public class SheetPanel extends JPanel {
  private List<PitchSequence> pitches;
  private int lastBeat;
  private int numMeasures;
  private int currentBeat;

  /**
   * Constructs a sheet panel.
   */
  public SheetPanel() {
    this.pitches = new ArrayList<PitchSequence>();
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g;
    g2d.setBackground(Color.white);

    this.setLastBeat();
    this.drawBeatCount(g2d);

    Collections.sort(pitches);
    Collections.reverse(this.pitches); // so that higher pitches are on top
    for (int i = 0; i < this.pitches.size(); i++) {
      this.drawPitch(g2d, this.pitches.get(i), i);
    }

    this.drawCursor(g2d);
  }

  /**
   * Sets the notes for this panel to display.
   * @param pitches the sheet of notes
   */
  public void setNotes(List<PitchSequence> pitches) {
    this.pitches = pitches;
    int pitchHeight = SHEET_START_Y + pitches.size() * MEASURE_BORDER_HEIGHT;
    this.setPreferredSize(new Dimension(DEFAULT_WIDTH, pitchHeight));
  }

  /**
   * Sets the current beat to place the cursor at.
   * @param beat the beat to place the cursor at
   */
  public void setCurrentBeat(int beat) {
    if (beat < 0 || beat > this.numMeasures * 4) {
      return;
    }
    this.currentBeat = beat;
  }
  /**
   * Draws the beat count above the sheet of notes.
   * @param g2d the 2d graphics object to draw on
   */
  private void drawBeatCount(Graphics2D g2d) {
    int x;
    int y;

    g2d.setColor(Color.black);
    for (int i = 0; i <= this.numMeasures; i++) {
      x = MEASURE_BORDER_WIDTH * i + this.getScrollDelta();
      y = SHEET_START_Y - 10;
      g2d.drawString((i * 4) + "", x, y);
    }
  }


  /**
   * Draws a pitch to the surface.
   * @param g2d the graphics 2d object to draw to
   * @param p the pitch to render
   * @param row the row to place the pitch at
   */
  private void drawPitch(Graphics2D g2d, PitchSequence p, int row) {
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

    int xPos = MEASURE_BORDER_WIDTH * measureNum
            + BEAT_WIDTH * remainingBeats + BORDER_WIDTH + this.getScrollDelta();
    int yPos = SHEET_START_Y + BEAT_BORDER_HEIGHT * row + BORDER_WIDTH;

    Color color;

    switch (c) {
      case 'X':
        color = Color.black;
        break;
      case '|':
        color = Color.green;
        break;
      default:
        return;
    }

    g2d.setColor(color);
    g2d.fillRect(xPos, yPos, BEAT_WIDTH + 1, BEAT_HEIGHT + 1);
  }

  /**
   * Draws the outlines of the measures at the specified location.
   * @param g2d the 2d graphics object to draw to
   * @param row the row to draw at
   */
  private void drawMeasures(Graphics2D g2d, int row) {
    int xPos;
    int yPos;

    for (int i = 0; i < numMeasures; i++) {
      xPos = i * MEASURE_BORDER_WIDTH + this.getScrollDelta();
      yPos = SHEET_START_Y + row * BEAT_BORDER_HEIGHT;

      // draw black outline
      g2d.setColor(Color.black);
      g2d.drawRect(xPos, yPos, MEASURE_BORDER_WIDTH, BEAT_BORDER_HEIGHT);
    }
  }

  /**
   * Place the cursor to mark the current beat.
   * @param g2d the graphics 2d object to draw to
   */
  private void drawCursor(Graphics2D g2d) {
    int measureNum = this.currentBeat / 4;
    int remainingBeat = currentBeat - measureNum * 4;
    int startX = measureNum * MEASURE_BORDER_WIDTH
            + remainingBeat * BEAT_WIDTH + this.getScrollDelta();
    int endX = startX;
    int startY = SHEET_START_Y;
    int endY = SHEET_START_Y + this.pitches.size() * MEASURE_BORDER_HEIGHT;
    g2d.setColor(Color.red);
    g2d.drawLine(startX, startY, endX, endY);
  }

  /**
   * Sets the index of the last beat in the sheet.
   */
  private void setLastBeat() {
    int lastBeat = 0;
    for (PitchSequence p : this.pitches) {
      if (p.getLastBeat() > lastBeat) {
        lastBeat = p.getLastBeat();
      }
    }
    this.lastBeat = lastBeat;
    this.numMeasures = (int) Math.ceil(this.lastBeat / 4) + 1;
  }


  /**
   * Gets the amount of pixels to shift the sheet left by as a negative integer,
   * based on the position of the cursor.
   * @return the amount to shift by
   */
  private int getScrollDelta() {
    if (this.currentBeat > 20) {
      int measures = (this.currentBeat - 20) / 4;
      int remainingBeats = (this.currentBeat - 20) - measures;
      return -1 * ((this.currentBeat - 20) * BEAT_WIDTH);
    }
    return 0;
  }

}
