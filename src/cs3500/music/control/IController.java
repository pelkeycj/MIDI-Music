package cs3500.music.control;

/**
 * Controller interface for the MIDI program.
 * The primary operation of the controller is to
 * handle key input from the view and update accordingly.
 */
public interface IController {

  /**
   * Pass the sheet of notes from the model to the view.
   */
  void setViewNotes();

  /**
   * Provide the controller with control, start the program.
   */
  void go();

}
