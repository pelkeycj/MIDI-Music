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

  /**
   * Constructs an instance of a {@code Note}.
   * @param start the start beat of the note (inclusive)
   * @param end the end beat of the note (inclusive)
   * @throws IllegalArgumentException if {@code start} or {@code end} are less than 0, or
   *                                   if {@code end} is less than {@code start}
   */
  Note(int start, int end) throws IllegalArgumentException {
    if (start < 0 || end < 0 || end < start) {
      throw new IllegalArgumentException("Invalid start and/or end beats.");
    }
    this.start = start;
    this.end = end;
  }

  /**
   * Gets the start beat of this {@code Note}.
   * @return the start beat
   */
  int getStart() {
    return this.start;
  }

  /**
   * Gets the end beat of this {@code Note}.
   * @return the end beat
   */
  int getEnd() {
    return this.end;
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
    return this.start == n.start && this.end == n.end;
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
