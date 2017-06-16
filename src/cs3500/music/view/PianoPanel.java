package cs3500.music.view;

import cs3500.music.model.NoteTypeWestern;
import cs3500.music.model.OctaveNumber0To10;
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

  private int panelWidth;
  private int panelHeight;

  private int whiteKeyWidth;
  private int whiteKeyHeight;
  private int blackKeyWidth;
  private int blackKeyHeight;

  private PianoKey[] keys;


  /**
   * Public constructor for the piano panel initializes the fields of the object
   * @param width the desired width of the panel
   * @param height the desired height of the panel
   * @throws IllegalArgumentException if the height or width is less than 100
   */
  public PianoPanel(int width, int height) throws IllegalArgumentException {
    if (width < 100 || height < 100) {
      throw new IllegalArgumentException("Panel must be at least 100x100.");
    }
    this.panelWidth = width;
    this.panelHeight = height;
    this.keys = generatePianoKeys(NUMBER_OF_OCTAVES);

    setKeyDimensions();
  }

  /**
   * Sets the key dimensions based on the current size of the piano panel.
   */
  private void setKeyDimensions() {
    this.whiteKeyWidth = (this.panelWidth - SIDE_BUFFER) / (7 * NUMBER_OF_OCTAVES);
    this.whiteKeyHeight = this.panelHeight - BOTTOM_BUFFER;
    this.blackKeyWidth = this.whiteKeyWidth / 2;
    this.blackKeyHeight = this.whiteKeyHeight / 2;
  }

  /**
   * Sets which keys are currently being pressed. Any keys are are in the given set of "onPitches"
   * are switched to unpressed.
   * @param onPitches the set of pitches are are currently being played in the view
   */
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
    this.setBackground(Color.LIGHT_GRAY);

    Graphics2D g2d = (Graphics2D) g;

    drawLargeKeys(g2d);
    drawSmallKeys(g2d);
  }

  /**
   * Draws the large keys on the panel. Parameters like color are supplied by the info held in the
   * PianoKey instances.
   * @param g2d the Graphics object on which to draw the keys
   */
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

  /**
   * Draws the large keys on the panel. Parameters like color are supplied by the info held in the
   * PianoKey instances.
   * @param g2d the Graphics object on which to draw the keys
   */
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

  /**
   * Generates a full set of piano key objects. One for each key to represented in the gui view.
   * @param numOctaves the number of octaves that this piano panel is representing
   * @return a full set piano keys
   */
  PianoKey[] generatePianoKeys(int numOctaves) {
    PianoKey[] keys = new PianoKey[numOctaves * 12];
    for (int octave = 0; octave < numOctaves; octave++) {
      for (int note = 0; note < NoteTypeWestern.values().length; note++) {
        keys[octave * 12 + note] = new PianoKey(note, octave + 1);
      }
    }
    return keys;
  }

  /**
   * Private class represents all the information needed to represent a PainoKey including the pitch
   * of the key and how it should be drawn. Contains methods to interact with the key and modify its
   * state as well as methods to get information from the key.
   */
  private class PianoKey {
    Pitch pitch;
    boolean largeKey;

    boolean pressed;
    Color pressedColor = Color.ORANGE;

    /**
     * Constructor for use in the GuiViewFrame class to create a instance of a piano key.
     * @param noteValue the note that this key represents
     * @param octaveValue the octave of the note that this key plays
     */
    private PianoKey (int noteValue, int octaveValue) {
      this.pitch = new Pitch(NoteTypeWestern.intToNote(noteValue),
              OctaveNumber0To10.intToOctave(octaveValue));
      this.largeKey = !(pitch.toString().contains("#"));
      this.pressed = false;
    }

    /**
     * Determines if this is a key to be drawn large or small.
     * @return true if the key is part of the larger key set
     */
    boolean isLargeKey() {
      return this.largeKey;
    }

    /**
     * Modifies the state of this key to be pressed.
     */
    void press() {
      this.pressed = true;
    }

    /**
     * Modifies the state of this key to be unpressed.
     */
    void unpress() {
      this.pressed = false;
    }

    /**
     * Determines the color of the outline of this key.
     * @return the color of the outline of this key
     */
    Color getOutlineColor() {
      return Color.BLACK;
    }

    /**
     * Returns the background color of this key. This depends on whether or not the key is a large
     * key or if it is pressed.
     * @return the background color of a key image.
     */
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
