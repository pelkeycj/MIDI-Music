package cs3500.music.model;

/**
 * This is just an idea. I think it would be great to have something the pairs the note and the
 * octave together, but doesn't have the full pitch sequence. I'm adding it for use in the piano
 * panel. We can review that solution to see if there is something more elegant.
 */
public class Pitch {

  private final Octave octaveNum;
  private final NoteType noteType;

  /**
   * Public constructor for a pitch object.
   *
   * @param note the kind of note of this pitch
   * @param octave the octave in which the note of this pitch is played
   */
  public Pitch(NoteType note, Octave octave) {
    this.noteType = note;
    this.octaveNum = octave;
  }

  /**
   * Return the octave in which this pitch is played.
   *
   * @return Octave value of this pitch
   */
  public Octave getOctave() {
    return this.octaveNum;
  }

  /**
   * Returns the type of note this pitch plays.
   *
   * @return NoteType of this pitch
   */
  public NoteType getNote() {
    return this.noteType;
  }

  /**
   * Returns the value of this note in a standard 10 octaves of western notes from 0 to 127
   *
   * @return integer representing this pitch
   */
  public int getValue() {
    return this.noteType.getValue() + (this.octaveNum.getValue() * 12);
  }

  /**
   * Returns the pitch represented by the given value in a traditional 10 octave western scale
   * of 0 to 127
   *
   * @param value value of the desired pitch
   * @return a pitch object
   */
  public static Pitch intToPitch(int value) {
    return new Pitch(NoteTypeWestern.intToNote(value % 12),
        OctaveNumber0To10.intToOctave(value / 12));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o instanceof Pitch) {
      Pitch p = (Pitch) o;
      return p.noteType.equals(this.noteType) && p.octaveNum.equals(this.octaveNum);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return this.octaveNum.getValue() * 100 + this.noteType.getValue();
  }

  public String toString() {
    return this.noteType.toString() + this.octaveNum.toString();
  }
}
