package cs3500.music.model;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Representation of a sequence of notes at a pitch.
 * A pitch is characterized as a note at an octave.
 */
public class PitchSequence implements Comparable<PitchSequence> {
  private final Octave octaveNum;
  private final NoteType noteType;
  private ArrayList<Note> notes;

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
  public int getLastBeat() {
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
   * @throws IllegalArgumentException if the note already exists
   *
   */
  PitchSequence addNote(Note n) throws IllegalArgumentException {
    if (this.notes.contains(n)) {
      throw new IllegalArgumentException("Note already exists.");
    }
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
   * @param p the pitch sequence to merge with
   * @param delta the amount to shift each number
   * @return the combined {@code PitchSequence}s
   * @throws IllegalArgumentException if {@code delta} is less than 0
   */
  PitchSequence addAll(PitchSequence p, int delta) throws IllegalArgumentException {
    if (delta < 0) {
      throw new IllegalArgumentException("Delta must be > 0");
    }

    Note n;
    while (!p.isEmpty()) {
      n = p.notes.remove(0);
      this.addNote(new Note(n.getStart() + delta, n.getEnd() + delta));
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
  public String getHeader() {
    return this.noteType.toString() + this.octaveNum.toString();
  }

  @Override
  public int compareTo(PitchSequence p) {
    if (this.octaveNum.getValue() != p.octaveNum.getValue()) {
      return this.octaveNum.getValue() - p.octaveNum.getValue();
    }
    else {
      return this.noteType.getValue() - p.noteType.getValue();
    }
  }

  /**
   * Gets the Octave of this pitch sequence.
   * @return the octave of this pitch sequence
   */
  Octave getOctave() {
    return this.octaveNum;
  }

  /**
   * Gets the note type of this pitch sequence.
   * @return the note type of this pitch sequence
   */
  NoteType getNoteType() {
    return this.noteType;
  }


  /**
   * Makes a deep copy of this {@code PitchSequence}.
   * @return a deep copy of this {@code PitchSequence}
   */
  public PitchSequence copy() {
    PitchSequence copy = new PitchSequence(this.octaveNum, this.noteType);

    for (Note n : this.notes) {
      copy.addNote(n.copy());
    }
    return copy;
  }

  //=============THINGS ADAM ADDED JUNE 13TH=============

  /**
   * Determines if this pitch is playing at the given beat.
   * @param beat beat to check
   * @return true if the pitch is during the given
   */
  public boolean playingAt(int beat) {
    if (beat < 0) {
      throw new IllegalArgumentException("Beats must be 0 or greater");
    }
    for (Note n : this.notes) {
      if (beat >= n.getStart() && beat <= n.getEnd()) {
        return true;
      }
    }
    return false;
  }

  /**
   * TODO this kind of overlaps with get beat
   * @param beat
   * @return
   */
  public Note noteAt(int beat) {
    for (Note n : this.notes) {
      if (beat >= n.getStart() && beat <= n.getEnd()) {
        return new Note(n.getStart(), n.getEnd(), n.getInstrument(), n.getLoudness());
      }
    }
    throw new IllegalArgumentException("No note playing at this beat.");
  }



  /**
   * Get a copy of the pitch represented by this pitch sequence.
   * @return a copy of the pitch
   */
  public Pitch getPitchCopy() {
    return new Pitch(this.getNoteType(), this.getOctave());
  }


}
