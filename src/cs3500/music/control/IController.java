package cs3500.music.control;

/**
 * Controller interface for the MIDI program.
 * The primary operation of the controller is to
 * handle key input from the view and update accordingly.
 */
public interface IController {

  //TODO indicate which notes to highlight?
    // IView -> toggle notes?

  /**
   * Provide the controller with control, start the program.
   */
  void go();

}
