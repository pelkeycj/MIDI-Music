package cs3500.music.model;

import java.util.ArrayList;
import java.util.Collections;

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


  /**
   * Adds all {@link Note}s from the provided {@code PitchSequence} to this one.
   * If {@code merge} is {@code true}, the notes are merged, otherwise they are appended to the end
   * of this sequence.
   * @param p the pitch sequence to merge with
   * @param merge indicates whether notes are merged or appended
   * @return the combined {@code PitchSequence}s
   */
  PitchSequence addAll(PitchSequence p, boolean merge) {
     int lastBeat;
     Note n;
     while (!p.isEmpty()) {
       n = p.notes.remove(0);
       if (merge) {
         this.addNote(p.notes.remove(0));
       }
       else {
         lastBeat = this.getLastBeat();
         this.addNote(new Note(n.getStart() + lastBeat, n.getEnd() + lastBeat));
       }
     }
     return this;
  }

  @Override
  public String toString() {
     if (this.isEmpty()) {
       return "";
     }

    Collections.sort(this.notes);
    StringBuilder s = new StringBuilder();
    for (int i = 0; i <= this.getLastBeat(); i++) {
      s.append(' ');
    }

    String noteString;
    int current;
    for (Note n : this.notes) {
      noteString = n.toString();
      current = n.getStart();

      for (char c : noteString.toCharArray()) {
        s.setCharAt(current, c);
        current++;
      }
    }

    return s.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof PitchSequence)) {
      return false;
    }
    PitchSequence p = (PitchSequence) o;
    return this.getHeader().equals(p.getHeader());
  }

  @Override
  public int hashCode() {
    return this.getHeader().hashCode();
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
