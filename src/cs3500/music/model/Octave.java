package cs3500.music.model;

/**
 * Represents an interface to specify operations
 * to be performed on octaves.
 */
public interface Octave {

  /**
   * Gets the integer value of the octave.
   * @return the integer value of the octave
   */
  int getValue();

  @Override
  String toString();
}
