package cs3500.music.view;

import cs3500.music.model.PitchSequence;
import java.awt.event.KeyListener;
import java.util.List;

/**
 * The view interface. Holds all the methods to interact with a view for a sheet of music.
 */
public interface IView {

  /**
   * Provide the view with the notes to be displayed.
   * @param pitches the note information for the view
   */
  void setNotes(List<PitchSequence> pitches);

  /**
   * Make the view visible.
   */
  void initialize();

  /**
   * Set the view to a certain beat.
   * @param beat the current beat
   */
  void setCurrentBeat(int beat);

  /**
   * Signal to the view to redraw itself.
   */
  void refresh();

  /**
   * Provide the view with a key listener to allow the program to
   * process key input.
   * @param keyListener the key listener to set
   */
  void setKeyListener(KeyListener keyListener);

  /**
   * Sets the rate of play for the given view.
   * @param tempo the speed at which the view moves through a given piece of music.
   *     Units: microseconds per beat
   */
  void setTempo(int tempo);


  /**
   * Determines if the window is active. Allows the controller to close if inactive.
   * @return true if active
   */
  boolean isActive();

}
