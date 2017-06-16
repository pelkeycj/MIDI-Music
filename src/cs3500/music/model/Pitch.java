package cs3500.music.model;

/**
 * Represents a pitch as a combination of a note type and an octave.
 * Used to convert to an integer representation for playing with Midi.
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

  /**
   * Gets the octave of this pitch.
   * @return the octave
   */
  public Octave getOctave() {
    return this.octaveNum;
  }

  /**
   * Gets the note type of this pitch.
   * @return the note type
   */
  public NoteType getNote() {
    return this.noteType;
  }

  public int getValue() {
    return this.noteType.getValue() + (this.octaveNum.getValue() * 12);
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
