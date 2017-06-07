package cs3500.music.model;

import java.util.ArrayList;

/**
 * Representation of a sequence of notes at a pitch.
 * A pitch is characterized as a note at an octave.
 */
class PitchSequence implements Comparable<PitchSequence> {
  Octave octaveNum;
  NoteType noteType;
  ArrayList<Note> notes;

  /**
   * Constructs an instance of {@code PitchSequence}.
   * @param octaveNum the octave number of this pitch
   * @param noteType the note type of this pitch
   */
  PitchSequence(Octave octaveNum, NoteType noteType) {
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

  /**
   * Adds the provided note to this {@code PitchSequence}.
   * @param n the note to add
   * @return the modified{@code PitchSequence}
   */
   PitchSequence addNote(Note n) {
    this.notes.add(n);
    return this;
  }

  /**
   * Removes the specified note from this {@code PitchSequence}.
   * @param n the {@link Note} to remove
   * @return the modified {@code PitchSequence}
   * @throws IllegalArgumentException if {@code n} does not exist in {@code notes}
   */
   PitchSequence removeNote(Note n) throws IllegalArgumentException {
    if (this.notes.remove(n)) {
      return this;
    }
    else {
      throw new IllegalArgumentException("Note does not exist: " + n);
    }
  }

  @Override
  public String toString() {
    //TODO
    return "";
  }

  @Override
  public boolean equals(Object o) {
    //TODO
    return false;
  }

  @Override
  public int hashCode() {
    //TODO
    return 0;
  }

  /**
   * Gets the header for this pitch in the form of note type + octave number.
   * @return the header for this pitch
   */
  String getHeader() {
    return this.noteType.toString() + this.octaveNum.toString();
  }

  @Override
  public int compareTo(PitchSequence p) {
    if (this.octaveNum.getValue() < p.octaveNum.getValue()) {
      return this.octaveNum.getValue() - p.octaveNum.getValue();
    }
    else {
      return this.noteType.getValue() - p.noteType.getValue();
    }
  }
}
