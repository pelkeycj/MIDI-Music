package cs3500.music.model;

/**
 * This is just an idea. I think it would be great to have something the pairs the note and
 * the octave together, but doesn't have the full pitch sequence.
 * I'm adding it for use in the piano panel. We can review that solution to see if there is something
 * more elegant.
 */
public class Pitch {
  private final Octave octaveNum;
  private final NoteType noteType;

  public Pitch(NoteType note, Octave octave) {
    this.noteType = note;
    this.octaveNum = octave;
  }

  /**
   * Copy constructor.
   * @param p pitch to copy
   */
  public Pitch(Pitch p) {
    this(p.getNote(), p.getOctave());
  }

  public Octave getOctave() {
    return this.octaveNum; //TODO make sure this doesn't expose this to modification
  }

  public NoteType getNote() {
    return this.noteType; //TODO same thing
  }

  public int getValue() {
    return this.noteType.getValue() + (this.octaveNum.getValue() - 1) * 12;
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
