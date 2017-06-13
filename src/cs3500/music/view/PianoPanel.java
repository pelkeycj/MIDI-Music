package cs3500.music.view;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import javax.swing.*;

/**
 * Displays a piano with keys that highlight to show the currently playing notes.
 */
public class PianoPanel extends JPanel {

  /*this is based on the expectation that the preferred size of the window is 500 x 250.
   if you want to have a resizeable panel or one in which the objects grow/shrink based on the
   window size, give this panel a way of knowing how large it is and set the parameters below
  */

  private final int DEFAULT_WIDTH = 500;
  private final int DEFAULT_HEIGHT = 250;
  private final int SIDE_BUFFER = 25;
  private final int NUMBER_OF_OCTAVES = 10;
  private final int BOTTOM_BUFFER = 25;

  private final int KEY_OUTLINE_WIDTH = 1;

  private final int WHITE_KEY_WIDTH = (DEFAULT_WIDTH - (2 * SIDE_BUFFER)) / (12 * NUMBER_OF_OCTAVES);
  private final int BLACK_KEY_WIDTH = WHITE_KEY_WIDTH / 2;
  private final int WHITE_KEY_HEIGHT = DEFAULT_HEIGHT - BOTTOM_BUFFER;
  private final int BLACK_KEY_HEIGHT = WHITE_KEY_HEIGHT / 2;

  private final int WHITE_KEY_BORDER_WIDTH = WHITE_KEY_WIDTH + 2 * KEY_OUTLINE_WIDTH;
  private final int WHITE_KEY_BORDER_HEIGHT = WHITE_KEY_HEIGHT + 2 * KEY_OUTLINE_WIDTH;

  @Override
  public void paintComponent(Graphics g){
    // Handle the default painting
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g;

    //place each one of the white keys with a black outline
    for (int i = 0; i < NUMBER_OF_OCTAVES; i++) {
      drawOctaveOfKeys(g2d, i);
    }


    // Look for more documentation about the Graphics class,
    // and methods on it that may be useful
    g.drawString("Hello World", 25, 25);
  }

  //places an octave of keys on this panel with 0 being the first octave
  private void drawOctaveOfKeys(Graphics2D g2d, int startingOctave) {
    int octaveWidth = WHITE_KEY_BORDER_WIDTH * 2;

    //place each one of the white keys with a black outline
    for (int i = 0; i < 7; i++) {
      int yPos = 0;
      int xPos = i * WHITE_KEY_BORDER_WIDTH + octaveWidth * startingOctave + SIDE_BUFFER;

      //draw the outline
      g2d.setColor(Color.BLACK);
      Shape outline = new Rectangle(xPos, yPos, WHITE_KEY_BORDER_WIDTH, WHITE_KEY_HEIGHT);
      g2d.draw(outline);

      xPos += WHITE_KEY_WIDTH;
      g2d.setColor(Color.WHITE);
      Shape key = new Rectangle(xPos, yPos, WHITE_KEY_WIDTH, WHITE_KEY_HEIGHT);
      g2d.fill(key);
    }

    for (int i = 0; i < 6; i++) {
      int yPos = 0;
      int xPos = i * WHITE_KEY_BORDER_WIDTH + SIDE_BUFFER + octaveWidth + startingOctave;

      if (i == 2) {
        continue;
      }

      g2d.setColor(Color.BLACK);
      Shape key = new Rectangle(xPos, yPos, BLACK_KEY_WIDTH, BLACK_KEY_HEIGHT);
      g2d.fill(key);
    }
  }

}
