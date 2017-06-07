package cs3500.music.model;

/**
 * Represents an interface to specify operations to be performed on note types.
 */
public interface NoteType {
  /**
   * Gets the integer value of the note type.
   * @return the integer value of the note type
   */
  int getValue();

  @Override
  String toString();
}
