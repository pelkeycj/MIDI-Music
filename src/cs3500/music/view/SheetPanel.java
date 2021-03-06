package cs3500.music.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Dimension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;

import cs3500.music.model.PitchSequence;
import cs3500.music.model.RepeatInstr;

import static cs3500.music.util.ViewConstants.SHEET_START_Y;
import static cs3500.music.util.ViewConstants.MEASURE_BORDER_HEIGHT;
import static cs3500.music.util.ViewConstants.MEASURE_BORDER_WIDTH;
import static cs3500.music.util.ViewConstants.BEAT_HEIGHT;
import static cs3500.music.util.ViewConstants.BEAT_WIDTH;
import static cs3500.music.util.ViewConstants.DEFAULT_WIDTH;
import static cs3500.music.util.ViewConstants.BORDER_WIDTH;
import static cs3500.music.util.ViewConstants.BEAT_BORDER_HEIGHT;

/**
 * Displays the sheet of music. Shows a cursor representing the current beat
 * and scrolls the sheet of music along with the moving cursor after beat 20.
 */
public class SheetPanel extends JPanel {
  private List<PitchSequence> pitches;
  private List<RepeatInstr> repeats;
  private int numMeasures;
  private int currentBeat;
  private int pitchHeight;

  /**
   * Constructs a sheet panel. A sheet panel contains a visual representation of the notes in a
   * piece of music. Only the notes relevant to the piece are displayed on this panel (i.e. if
   * the note never gets played, it is not displayed). A grid pattern representing those notes and
   * the measure of the piece of music are displayed on a scrollable panel along with a marker
   * showing the current beat of the music.
   * Notes are represents by colored rectangles with the starting beat of a note a distinctly
   * different color that the subsequent beats of the same note. The marker of the notes scrolls
   * along with the notes as the view moves through the piece of music.
   * The position of this marker and the notes displayed on the panel can be modified with public
   * methods.
   */
  public SheetPanel() {
    this.pitches = new ArrayList<>();
    this.repeats = new ArrayList<>();
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setBackground(Color.white);

    this.drawBeatCount(g2d);

    for (int i = 0; i < this.pitches.size(); i++) {
      this.drawPitch(g2d, this.pitches.get(i), i);
    }

    this.drawMeasures(g2d);

    this.drawRepeats(g2d);

    this.drawCursor(g2d);
  }

  /**
   * Sets the notes for this panel to display.
   * @param pitches the sheet of notes
   */
  public void setNotes(List<PitchSequence> pitches) {
    this.pitches = pitches;
    this.pitchHeight = SHEET_START_Y + pitches.size() * MEASURE_BORDER_HEIGHT;
    this.setPreferredSize(new Dimension(DEFAULT_WIDTH, this.pitchHeight));
    this.setLastBeat();
    // sort and reverse for displaying
    Collections.sort(pitches);
    Collections.reverse(this.pitches); // so that higher pitches are on top
  }

  /**
   * Sets the repeats to display.
   * @param repeats the list of repeats
   */
  public void setRepeats(List<RepeatInstr> repeats) {
    this.repeats = repeats;
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
    if (this.pitches.isEmpty()) {
      return;
    }

    int x;
    int y;

    g2d.setColor(Color.black);

    for (int i = (this.currentBeat - 20) / 4; i <= (this.currentBeat + 100) / 4; i++) {
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
  private void drawPitch(Graphics2D g2d, PitchSequence p, int row)  {
    // draw out pitch based on pitch string and last beat
    String pitchString = p.toString();
    int start;
    int end;

    if (this.currentBeat < 20) {
      start = 0;
    }
    else {
      start = this.currentBeat - 20;
    }
    if (pitchString.length() - 110 < this.currentBeat) {
      end = pitchString.length();
    }
    else {
      end = this.currentBeat + 100;
    }

    for (int i = start; i < end; i++) {
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
   * Draws the outlines of the measures.
   * @param g2d the 2d graphics object to draw to
   */
  private void drawMeasures(Graphics2D g2d) {
    int beginAt = (this.currentBeat - 20) / 4;
    int endAt = (this.currentBeat + 100) / 4;
    int xStart;
    int xEnd;
    int yStart;
    int yEnd;


    g2d.setColor(Color.black);
    // verticals
    for (int i = beginAt; i < endAt; i++) {
      xStart = i * MEASURE_BORDER_WIDTH + this.getScrollDelta();
      xEnd = xStart;
      yStart = SHEET_START_Y;
      yEnd = this.pitchHeight;
      g2d.drawLine(xStart, yStart, xEnd, yEnd);
    }

    //horizontals
    for (int i = 0; i < this.pitches.size() + 1; i++) {
      xStart = beginAt + this.getScrollDelta();
      xEnd = endAt * MEASURE_BORDER_WIDTH + getScrollDelta();
      yStart = SHEET_START_Y + i * MEASURE_BORDER_HEIGHT;
      yEnd = yStart;
      g2d.drawLine(xStart, yStart, xEnd, yEnd);
    }

  }

  /**
   * Draws the repeats to the sheet.
   * @param g2d the graphics 2d object to draw to
   */
  private void drawRepeats(Graphics2D g2d) {
    int numMeasures;
    int remainingBeats;
    int xStart;
    int xEnd;
    int yStart;
    int yEnd;

    for (RepeatInstr r : this.repeats) {
      // draw start line
      numMeasures = r.firstBeat() / 4;
      remainingBeats = r.firstBeat() - numMeasures * 4;
      xStart = numMeasures * MEASURE_BORDER_WIDTH + remainingBeats * BEAT_WIDTH
              + this.getScrollDelta();
      xEnd = xStart;

      yStart = SHEET_START_Y - 10;
      yEnd = this.pitchHeight + 10;

      g2d.setColor(Color.magenta);
      g2d.drawLine(xStart, yStart, xEnd, yEnd);

      // draw stop line
      numMeasures = r.lastBeat() / 4;
      remainingBeats = r.lastBeat() - numMeasures * 4;

      xStart = numMeasures * MEASURE_BORDER_WIDTH + remainingBeats * BEAT_WIDTH
              + this.getScrollDelta();
      xEnd = xStart;

      g2d.setColor(Color.blue);
      g2d.drawLine(xStart, yStart, xEnd, yEnd);
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
    int endY = this.pitchHeight;
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
    this.numMeasures = (int) Math.ceil(lastBeat / 4) + 1;
  }


  /**
   * Gets the amount of pixels to shift the sheet left by as a negative integer,
   * based on the position of the cursor.
   * @return the amount to shift by
   */
  private int getScrollDelta() {
    if (this.currentBeat > 20) {
      int beatsAfter20 = this.currentBeat - 20;

      int delta = (beatsAfter20 * BEAT_WIDTH) +  (beatsAfter20 / 4) * (2 * BORDER_WIDTH);

      return -1 * delta;
    }
    return 0;
  }
}
