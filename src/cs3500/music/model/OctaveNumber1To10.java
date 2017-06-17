package cs3500.music.model;

/**
 * Enumeration to represent the musical octaves 1-10 inclusive.
 */
public enum OctaveNumber1To10 implements Octave {
  O1(1), O2(2), O3(3), O4(4), O5(5), O6(6), O7(7), O8(8), O9(9), O10(10);
  final int number;

  OctaveNumber1To10(int number) {
    this.number = number;
  }

  @Override
  public int getValue() {
    return this.number;
  }

  @Override
  public String toString() {
    return this.number + "";
  }

  /**
   * Get the Octave for this value.
   * @param value the desired octave
   * @return an Octave object that corresponds to the give input
   * @throws IllegalArgumentException if the given value is outside of the range of these octaves
   */
  public static Octave intToOctave(int value) throws IllegalArgumentException {
    for (Octave o : OctaveNumber1To10.values()) {
      if (o.getValue() == value) {
        return o;
      }
    }
    throw new IllegalArgumentException("Provide a valid integer 1 through 10.");
  }
}
