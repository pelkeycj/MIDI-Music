package cs3500.music.model;

import java.util.ArrayList;

/**
 * Interface to represent specifications for required methods for a music playler model.
 */
public interface MusicOperations {

  /**
   * Adds a note from beats {@code start} to {@code end} at pitch
   * specified by octave {@code o} and note type {@code nt}.
   * @param o the octave to add to
   * @param nt the note type to add to
   * @param start the start beat of the note
   * @param end the end beat of the note
   */
  void addNote(Octave o, NoteType nt, int start, int end);

  /**
   * Removes the note from beats {@code start} to {@code end} at the pitch
   * specified by octave {@code o} and note type {@code nt}.
   * @param o the octave to remove from
   * @param nt the note type to remove from
   * @param start the start of the note
   * @param end the end of the note
   * @throws IllegalArgumentException if {@code start}  or {@code end} are invalid, or there
   *                                  is no {@code Note} to remove.
   */
  void removeNote(Octave o, NoteType nt, int start, int end) throws IllegalArgumentException;

  /**
   * Changes the start or end beats for the specified note.
   * @param o the octave to change
   * @param nt the note type to change
   * @param oldStart the original start position
   * @param oldEnd the original end position
   * @param newStart the new start position
   * @param newEnd the new end position
   * @throws IllegalArgumentException if there is no note to move, or one of the
   *                                  location parameters are invalid.
   */
  void changeNote(Octave o, NoteType nt, int oldStart, int oldEnd, int newStart, int newEnd)
          throws IllegalArgumentException;


  /**
   * Merges the sheet of notes represented in {@code m}.
   * {@code MusicOperation}s sheet.
   * @param m the sheet of notes to merge
   */
  void mergeSheet(MusicOperations m);

  /**
   * Appends the sheet of notes represented in {@code m} to the end of this sheet.
   * @param m the sheet of notes to append
   */
  void appendSheet(MusicOperations m);

  /**
   * Gets the string representation of this music player's sheet of notes.
   * @return a string representing this music player's sheet of notes.
   */
  String getSheet();

  /**
   * Get the list of {@link PitchSequence}s.
   * @return a list of pitch sequences
   */
  ArrayList<PitchSequence> getPitches();
}
