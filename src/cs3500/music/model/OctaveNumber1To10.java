package cs3500.music.model;

/**
 * Enumeration to represent the musical octaves 1-10 inclusive.
 */
public enum OctaveNumber1To10 {
  O1(1), O2(2), O3(3), O4(4), O5(5), O6(6), O7(7), O8(8), O9(9), O10(10);
  private final int number;

  OctaveNumber1To10(int number) {
    this.number = number;
  }

  @Override
  public String toString() {
    return this.number + "";
  }
}
