package cs3500.music.model;

/**
 * Represents the western notes of C through B.
 */
public enum NoteTypeWestern {
  C("C"), C_SHARP("C#"), D("D"), D_SHARP("D#"),
  E("E"), F("F"), F_SHARP("F#"), G("G#"),
  G_SHARP("G#"), A("A"), A_SHARP("A#"), B("B");

  private final String asString;

  /**
   * Construct an instance of {@code NoteTypeWestern}.
   * @param asString the string representation of a note
   */
  NoteTypeWestern(String asString) {
    this.asString = asString;
  }

  @Override
  public String toString() {
    return this.asString;
  }
}
