package cs3500.music.model;

/**
 * Representation of an individual music note on a pitch.
 * Stores basic information such as start beat and end beat (inclusive).
 * Implements comparable to allow for sorting.
 */
public class Note implements Comparable<Note> {
  //INVARIANT: end >= start
  private int start;
  private int end;
  private int instrument;
  private int loudness;

  /**
   * Constructs an instance of a {@code Note}.
   * @param start the start beat of the note (inclusive)
   * @param end the end beat of the note (inclusive)
   * @throws IllegalArgumentException if {@code start} or {@code end} are less than 0, or
   *                                   if {@code end} is less than {@code start}
   */
  public Note(int start, int end) throws IllegalArgumentException {
    if (start < 0 || end < 0 || end < start) {
      throw new IllegalArgumentException("Invalid start and/or end beats.");
    }
    this.start = start;
    this.end = end;
  }

  /**
   * Construct a note with start, end, instrument, and loudness.
   * @param start beat to start at
   * @param end beat to end at
   * @param instrument the instrument being played
   * @param loudness the loudness to play at
   * @throws IllegalArgumentException if start < 0, end < 0, end < start,
   *                                  instrument < 1, loudness < 0, loudness > 100
   */
  public Note(int start, int end, int instrument, int loudness) throws IllegalArgumentException {
    this(start, end);

    if (instrument < 1 || loudness < 0 || loudness > 100) {
      throw new IllegalArgumentException("Invalid instrument or loudness");
    }

    this.instrument = instrument;
    this.loudness = loudness;
  }


  /**
   * Gets the start beat of this {@code Note}.
   * @return the start beat
   */
  public int getStart() {
    return this.start;
  }

  /**
   * Gets the end beat of this {@code Note}.
   * @return the end beat
   */
  public int getEnd() {
    return this.end;
  }


  /**
   * Gets the instrument playing this note.
   * @return integer representation of an instrument
   */
  public int getInstrument() {
    return this.instrument;
  }

  /**
   * Gets the loudness of this note from 0-100 inclusive.
   * @return the loudness of this note
   */
  public int getLoudness() {
    return this.loudness;
  }


  @Override
  public String toString() {
    StringBuilder s = new StringBuilder();
    s.append("X");

    for (int i = start; i < end; i++) {
      s.append("|");
    }
    return s.toString();
  }

  @Override
  public int compareTo(Note n) {
    if (this.start - n.start == 0) {
      return this.end - n.end;
    }
    else {
      return this.start - n.start;
    }
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Note)) {
      return false;
    }
    Note n = (Note) o;
    return this.start == n.start
            && this.end == n.end
            && this.loudness == n.loudness
            && this.instrument == n.instrument;
  }

  @Override
  public int hashCode() {
    return 10000 * start + end;
  }

  /**
   * Makes a copy of this {@code Note}.
   * @return a deep copy of this {@code Note}
   */
  public Note copy() {
    return new Note(this.start, this.end);
  }
}
