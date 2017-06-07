package cs3500.music.model;

/**
 * Represents the western notes of C through B.
 */
public enum NoteTypeWestern implements NoteType {
  C(0, "C"), C_SHARP(1, "C#"), D(2, "D"), D_SHARP(3, "D#"),
  E(4, "E"), F(5, "F"), F_SHARP(6, "F#"), G(7, "G#"),
  G_SHARP(8, "G#"), A(9, "A"), A_SHARP(10, "A#"), B(11, "B");

  private final int value;
  private final String asString;

  /**
   * Construct an instance of {@code NoteTypeWestern}.
   * @param asString the string representation of a note
   */
  NoteTypeWestern(int value, String asString) {
    this.value = value;
    this.asString = asString;
  }

  @Override
  public int getValue() {
    return this.value;
  }

  @Override
  public String toString() {
    return this.asString;
  }
}
