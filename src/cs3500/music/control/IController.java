package cs3500.music.control;

import cs3500.music.model.NoteType;
import cs3500.music.model.Octave;

/**
 * Controller interface for the MusicEditor program.
 * The primary operation of the controller is to
 * handle key input from the view and update accordingly.
 */
public interface IController {

  /**
   * Pass the sheet of notes from the model to the view.
   */
  void setViewNotes();

  /**
   * Set the tempo to play at.
   * @param tempo in microseconds per beat
   */
  void setTempo(int tempo);

  /**
   * Adds a note to the model.
   * @param o the octave to add to
   * @param nt the note type to add to
   * @param start the start beat
   * @param end the end beat
   */
  void addNote(Octave o, NoteType nt, int start, int end);

  /**
   * Adds a note to the model.
   * @param o the octave to add to
   * @param nt the note type to add to
   * @param start the start beat
   * @param end the end beat
   * @param instrument the instrument to play
   * @param loudness the loudness to play at
   */
  void addNote(Octave o, NoteType nt, int start, int end, int instrument, int loudness);

  /**
   * Provide the controller with control, start the program.
   */
  void go();

}
