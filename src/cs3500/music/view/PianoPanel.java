package cs3500.music.view;

import cs3500.music.model.NoteTypeWestern;
import cs3500.music.model.OctaveNumber0To10;
import cs3500.music.model.Pitch;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Rectangle;

import java.util.HashSet;
import javax.swing.JPanel;

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
  private HashSet<Pitch> onSet;


  /**
   * Package private constructor for the piano panel initializes the fields of the object.
   * @param width the desired width of the panel
   * @param height the desired height of the panel
   * @throws IllegalArgumentException if the height or width is less than 100
   */
  PianoPanel(int width, int height) throws IllegalArgumentException {
    if (width < 100 || height < 100) {
      throw new IllegalArgumentException("Panel must be at least 100x100.");
    }
    this.panelWidth = width;
    this.panelHeight = height;
    setKeyDimensions();

    generatePianoKeys(NUMBER_OF_OCTAVES);
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
    this.onSet = onPitches;
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
   * Sets the key representing the given pitch to the active state.
   * @param p pitch to be set to the active state.
   */
  public void activateKey(Pitch p) {
    for (PianoKey key : this.keys) {
      if (key.pitchCopy().equals(p)) {
        if (this.onSet.contains(key.pitch)) {
          key.goodPress();
        } else {
          key.badPress();
        }
        return;
      }
    }
    throw new IllegalArgumentException("This is pitch is not contained on the panel.");
  }

  /**
   * Determines if all keys are currently on are in the activated state.
   * @return true if all on keys are active
   */
  public boolean allOnKeysActivated() {
    for (PianoKey key : this.keys) {
      if (key.isPressed()) {
        if (!(key.isActivated())) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Returns a copy of the pitch that is represented at the given x, y coordinates. If no piano
   * key is located at that position, a null pitch is returns.
   * @param x the x coordinate of the piano panel
   * @param y the y coordinate of the piano panel
   * @return the pitch object represented at this location
   */
  public Pitch keyAt(int x, int y) {
    for (PianoKey p : this.keys) {
      if (p.foundAt(x, y)) {
        return p.pitchCopy();
      }
    }
    return null;
  }

  /**
   * Updates the panel with it's new size changing the sizes of the elements in the panel.
   * @param width new width of the panel
   * @param height new height of the panel
   */
  public void resizePanel(int width, int height) {
    this.panelWidth = width;
    this.panelHeight = height;
    setKeyDimensions();
  }

  @Override
  public void paintComponent(Graphics g) {
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
    for (PianoKey p : this.keys) {
      if (p.isLargeKey()) {
        p.draw(g2d);
      }
    }
  }

  //provide x and y coordinates to each large key on the piano
  private void placeLargeKeys() {
    int yPos = 0;
    int xPos = SIDE_BUFFER;
    for (PianoKey p : this.keys) {
      if (p.isLargeKey()) {
        p.setKeyPos(xPos, yPos);
        xPos += this.whiteKeyWidth;
      }
    }
  }

  //provide x and y coordinates to each small key on the piano
  private void placeSmallKeys() {
    int yPos = 0;
    int xPos = SIDE_BUFFER + whiteKeyWidth - blackKeyWidth / 2;
    int smallKeyInOctave = 1;
    for (PianoKey p : this.keys) {
      if (!(p.isLargeKey())) {
        p.setKeyPos(xPos, yPos);
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
   * Draws the large keys on the panel. Parameters like color are supplied by the info held in the
   * PianoKey instances.
   * @param g2d the Graphics object on which to draw the keys
   */
  private void drawSmallKeys(Graphics2D g2d) {
    for (PianoKey p : this.keys) {
      if (!(p.isLargeKey())) {
        p.draw(g2d);
      }
    }
  }

  /**
   * Generates a full set of piano key objects. One for each key to represented in the gui view.
   * @param numOctaves the number of octaves that this piano panel is representing
   * @return a full set piano keys
   */
  private void generatePianoKeys(int numOctaves) {
    this.keys = new PianoKey[numOctaves * 12];
    for (int octave = 0; octave < numOctaves; octave++) {
      for (int note = 0; note < NoteTypeWestern.values().length; note++) {
        keys[octave * 12 + note] = new PianoKey(note, octave);
      }
    }
    placeLargeKeys();
    placeSmallKeys();
  }

  /**
   * Private class represents all the information needed to represent a PainoKey including the pitch
   * of the key and how it should be drawn. Contains methods to interact with the key and modify its
   * state as well as methods to get information from the key.
   */
  private class PianoKey {
    private Pitch pitch;
    private boolean largeKey;
    private boolean pressed;
    private Color pressedColor = Color.ORANGE;
    Rectangle image;
    private boolean activated;
    private Color activateColor = Color.green;
    private boolean badPress;
    private Color badPressColor = Color.red;

    /**
     * Constructor for use in the GuiViewFrame class to create a instance of a piano key.
     * @param noteValue the note that this key represents
     * @param octaveValue the octave of the note that this key plays
     */
    private PianoKey(int noteValue, int octaveValue) {
      this.pitch = new Pitch(NoteTypeWestern.intToNote(noteValue),
              OctaveNumber0To10.intToOctave(octaveValue));
      this.largeKey = !(pitch.toString().contains("#"));
      this.pressed = false;
      if (this.largeKey) {
        this.image = new Rectangle(whiteKeyWidth, whiteKeyHeight);
      } else {
        this.image = new Rectangle(blackKeyWidth, blackKeyHeight);
      }
    }

    /**
     * Determines if this is a key to be drawn large or small.
     * @return true if the key is part of the larger key set
     */
    private boolean isLargeKey() {
      return this.largeKey;
    }

    /**
     * Modifies the state of this key to be pressed.
     */
    private void press() {
      this.pressed = true;
    }

    /**
     * Modifies the state of this key to be unpressed.
     */
    private void unpress() {
      this.pressed = false;
      this.activated = false;
      this.badPress = false;
    }

    /**
     * Set this key to its active state.
     */
    private void goodPress() {
      this.activated = true;
    }

    /**
     * Indicated that this key has been incorrectly pressed.
     */
    private void badPress() {
      this.badPress = true;
    }

    /**
     * Determines if this key is pressed.
     * @return true if the key is pressed.
     */
    private boolean isPressed() {
      return this.pressed;
    }

    /**
     * Determines if this key is activated.
     * @return true is the key is activated.
     */
    private boolean isActivated() {
      return this.activated;
    }

    /**
     * Determines the color of the outline of this key.
     * @return the color of the outline of this key
     */
    private Color getOutlineColor() {
      return Color.BLACK;
    }

    /**
     * Returns the background color of this key. This depends on whether or not the key is a large
     * key or if it is pressed.
     * @return the background color of a key image.
     */
    private Color getBackGroundColor() {
      if (this.badPress) {
        return this.badPressColor;
      }
      else if (this.activated) {
        return this.activateColor;
      }
      else if (this.pressed) {
        return this.pressedColor;
      }
      else if (this.isLargeKey()) {
        return Color.WHITE;
      }
      else {
        return Color.BLACK;
      }
    }

    private Pitch pitchCopy() {
      return new Pitch(this.pitch.getNote(), this.pitch.getOctave());
    }

    /**
     * Draws the key onto the panel.
     * @param g2d graphics object on which to draw the key
     */
    private void draw(Graphics2D g2d) {
      g2d.setColor(this.getBackGroundColor());
      g2d.fill(this.image);
      g2d.setColor(this.getOutlineColor());
      g2d.draw(this.image);
    }

    /**
     * Sets the position of the image of this key.
     * @param x the x-coordinate of the top left corner of this piano key
     * @param y the y-coordinate of the top left corner of this piano key
     */
    private void setKeyPos(int x, int y) {
      this.image.setLocation(x, y);
    }

    private boolean foundAt(int x, int y) {
      return this.image.contains(x, y);
    }
  }

}
