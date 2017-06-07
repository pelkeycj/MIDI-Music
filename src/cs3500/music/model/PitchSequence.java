package cs3500.music.model;

import java.util.ArrayList;

/**
 * Representation of a sequence of notes at a pitch.
 * A pitch is characterized as a note at an octave.
 * @param <O> the generic octave number enumeration
 * @param <N> the generic note type enumeration
 */
class PitchSequence<O, N> implements Comparable<PitchSequence<O, N>> {
  O octaveNum;
  N noteType;
  ArrayList<Note> notes;

  /**
   * Constructs an instance of {@code PitchSequence}.
   * @param octaveNum the octave number of this pitch
   * @param noteType the note type of this pitch
   */
  PitchSequence(O octaveNum, N noteType) {
    this.octaveNum = octaveNum;
    this.noteType = noteType;
    notes = new ArrayList<Note>();
  }

  /**
   * Determines if this {@code PitchSequence} has no {@link Note}s.
   * @return true if {@code notes} is empty
   */
  boolean isEmpty() {
    return this.notes.isEmpty();
  }


  /**
   * Gets the index of the last beat in this sequence of notes.
   * @return the index of the last beat
   */
  int getLastBeat() {
    int last = 0;
    for (Note n : this.notes) {
      if (n.getEnd() > last) {
        last = n.getEnd();
      }
    }
    return last;
  }


  //TODO add note
  //TODO remove note
  //TODO toString
  //TODO equals
  //TODO hashcode
  //TODO getHeader


  @Override
  //TODO
  public int compareTo(PitchSequence<O, N> o) {
    return 0;
  }
}
