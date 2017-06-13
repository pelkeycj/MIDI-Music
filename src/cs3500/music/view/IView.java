package cs3500.music.view;

import com.sun.javaws.exceptions.InvalidArgumentException;
import cs3500.music.model.*;
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
   * TODO does this throw and error if the beat is invalid?
   */
  void setCurrentBeat(int beat) throws IllegalArgumentException;


  /**
   * Signal to the view to redraw itself.
   */
  void refresh();
}
