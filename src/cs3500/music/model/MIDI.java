package cs3500.music.model;

import java.util.ArrayList;

/**
 * Representation of a MIDI music player. <br>
 * Public facing class that implements the {@link MusicOperations} interface.
 */
public class MIDI implements MusicOperations {
  private ArrayList<PitchSequence> pitches;
  private ArrayList<MIDI> appendedSheets;

  public MIDI() {
    this.pitches = new ArrayList<PitchSequence>();
    this.appendedSheets = new ArrayList<MIDI>();
  }

  @Override
  public void addNote(Octave o, NoteType nt, int start, int end) {
    if (this.hasPitch(o, nt)) {
      this.getPitch(o, nt).addNote(new Note(start, end));
    }
    else {
      this.pitches.add(new PitchSequence(o, nt).addNote(new Note(start, end)));
    }
  }

  @Override
  public void removeNote(Octave o, NoteType nt, int start, int end) throws IllegalArgumentException {
    if (this.hasPitch(o, nt)) {
      this.getPitch(o, nt).removeNote(new Note(start ,end));
    }
    else {
      throw new IllegalArgumentException("No such PitchSequence.");
    }
  }

  @Override
  public void changeNote(Octave o, NoteType nt, int oldStart, int oldEnd, int newStart, int newEnd) throws IllegalArgumentException {
    //TODO
  }

  @Override
  public void mergeSheet(MusicOperations m) {
    //TODO
  }

  @Override
  public void appendSheet(MusicOperations m) {
    //TODO
  }

  @Override
  public String getSheet() {
    //TODO
    return "";
  }

  //TODO remove empty pitches

  /**
   * Determines if there is a {@link PitchSequence} in {@code pitches} that matches the
   * provided {@link Octave} and {@link NoteType}.
   * @param o the octave to check for
   * @param nt the note type to check for
   * @return true if a matching {@link PitchSequence} is found
   */
  private boolean hasPitch(Octave o, NoteType nt) {
    for (PitchSequence p : this.pitches) {
      if (p.getHeader().equals(nt.toString() + o.toString())) {
        return true;
      }
    }
    return false;
  }

  /**
   * Retrieves the specified {@link PitchSequence}.
   * @param o the octave to retrieve
   * @param nt the note type to retrieve
   * @return the specified pitch sequence
   * @throws IllegalArgumentException if there is no matching
   *                                  {@link PitchSequence} in {@code pitches}
   */
  private PitchSequence getPitch(Octave o, NoteType nt) throws IllegalArgumentException {
    for (PitchSequence p : this.pitches) {
      if (p.getHeader().equals(nt.toString() + o.toString())) {
        return p;
      }
    }
    throw new IllegalArgumentException("No such PitchSequence.");
  }

}
