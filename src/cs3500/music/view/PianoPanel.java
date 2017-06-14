package cs3500.music.view;

import cs3500.music.model.NoteTypeWestern;
import cs3500.music.model.OctaveNumber1To10;
import cs3500.music.model.Pitch;
import java.awt.*;

import java.util.HashSet;
import javax.swing.*;

/**
 * Displays a piano with keys that highlight to show the currently playing notes.
 */
public class PianoPanel extends JPanel {

  private final int SIDE_BUFFER = 25;
  private final int NUMBER_OF_OCTAVES = 10;
  private final int BOTTOM_BUFFER = 25;

  //non-final components
  private int panelWidth;
  private int panelHeight;

  private int whiteKeyWidth;
  private int whiteKeyHeight;
  private int blackKeyWidth;
  private int blackKeyHeight;

  private PianoKey[] keys;


  public PianoPanel(int width, int height) {
    if (width < 100 || height < 100) {
      throw new IllegalArgumentException("Panel must be at least 100x100.");
    }
    this.panelWidth = width;
    this.panelHeight = height;
    this.keys = generatePianoKeys(NUMBER_OF_OCTAVES);

    setKeyDimensions();
  }

  private void setKeyDimensions() {
    this.whiteKeyWidth = (this.panelWidth - SIDE_BUFFER) / (7 * NUMBER_OF_OCTAVES);
    this.whiteKeyHeight = this.panelHeight - BOTTOM_BUFFER;
    this.blackKeyWidth = this.whiteKeyWidth / 2;
    this.blackKeyHeight = this.whiteKeyHeight / 2;
  }


  public void setOnKeys(HashSet<Pitch> onPitches) {
    for (PianoKey key : this.keys) {
      if (onPitches.contains(key.pitch)) {
        key.press();
      }
      else {
        key.unpress();
      }
    }
  }

  /**
   * Updates the panel with it's new size changing the sizes of the elements in the panel.
   * @param width new width of the panel
   * @param height new height of the panel
   */
  public void resizePanel(int width, int height) {
    this.panelWidth= width;
    this.panelHeight = height;
    setKeyDimensions();
  }

  @Override
  public void paintComponent(Graphics g){
    // Handle the default painting
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g;

    drawLargeKeys(g2d);
    drawSmallKeys(g2d);
  }

  private void drawLargeKeys(Graphics2D g2d) {
    int yPos = 0;
    int xPos = SIDE_BUFFER;
    for (PianoKey p : this.keys) {
      if (p.isLargeKey()) {
        Shape key = new Rectangle(xPos, yPos, whiteKeyWidth, whiteKeyHeight);
        g2d.setColor(p.getBackGroundColor());
        g2d.fill(key);
        g2d.setColor(p.getOutlineColor());
        g2d.draw(key);
        xPos += this.whiteKeyWidth;
      }
    }
  }

  private void drawSmallKeys(Graphics2D g2d) {
    int yPos = 0;
    int xPos = SIDE_BUFFER + whiteKeyWidth - blackKeyWidth / 2;
    int smallKeyInOctave = 1;
    for (PianoKey p : this.keys) {
      if (!(p.isLargeKey())) {
        Shape key = new Rectangle(xPos, yPos, blackKeyWidth, blackKeyHeight);
        g2d.setColor(p.getBackGroundColor());
        g2d.fill(key);
        g2d.setColor(p.getOutlineColor());
        g2d.draw(key);
        if (smallKeyInOctave == 2) {
          xPos += this.whiteKeyWidth * 2;
        }
        else {
          xPos += this.whiteKeyWidth;
        }
        if (smallKeyInOctave == 5) {
          xPos += this.whiteKeyWidth;
          smallKeyInOctave = 1;
        }
        else {
          smallKeyInOctave++;
        }
      }
    }
  }

  PianoKey[] generatePianoKeys(int numOctaves) {
    PianoKey[] keys = new PianoKey[numOctaves * 12];
    for (int octave = 0; octave < numOctaves; octave++) {
      for (int note = 0; note < NoteTypeWestern.values().length; note++) {
        keys[octave * 12 + note] = new PianoKey(note, octave + 1);
      }
    }
    return keys;
  }


  private class PianoKey {
    Pitch pitch;
    boolean largeKey;

    boolean pressed;
    Color pressedColor = Color.yellow;

    PianoKey (int noteValue, int octaveValue) {
      this.pitch = new Pitch(NoteTypeWestern.intToNote(noteValue), OctaveNumber1To10.intToOctave(octaveValue));
      if (pitch.toString().contains("#")) {
        this.largeKey = false;
      } else {
        this.largeKey = true;
      }
      this.pressed = false;
    }

    boolean isLargeKey() {
      return this.largeKey;
    }

    void press() {
      this.pressed = true;
    }

    void unpress() {
      this.pressed = false;
    }

    Color getOutlineColor() {
      return Color.BLACK;
    }

    Color getBackGroundColor() {
      if (this.pressed) {
        return this.pressedColor;
      }
      else if (this.isLargeKey()) {
        return Color.WHITE;
      }
      else {
        return Color.BLACK;
      }
    }
  }

}
