package cs3500.music.model;

import java.util.ArrayList;
import java.util.Collections;

import cs3500.music.util.StringUtilities;

/**
 * Representation of a MIDI music player. <br>
 * Public facing class that implements the {@link MusicOperations} interface.
 */
public class MIDI implements MusicOperations {
  private ArrayList<PitchSequence> pitches;

  public MIDI() {
    this.pitches = new ArrayList<PitchSequence>();
  }

  //FOR GRADER'S USE
  @Override
  public void consoleRender() {
    System.out.print(this.getSheet());
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
  public void removeNote(Octave o, NoteType nt, int start, int end)
          throws IllegalArgumentException {
    if (this.hasPitch(o, nt)) {
      this.getPitch(o, nt).removeNote(new Note(start ,end));
      this.removeEmpty();
    }
    else {
      throw new IllegalArgumentException("No such PitchSequence.");
    }
  }

  @Override
  public void changeNote(Octave o, NoteType nt, int oldStart, int oldEnd, int newStart, int newEnd)
          throws IllegalArgumentException {
    if (this.hasPitch(o, nt)) {
      PitchSequence p = this.getPitch(o, nt);
      p.removeNote(new Note(oldStart, oldEnd));
      p.addNote(new Note(newStart, newEnd));
    }
    else {
      throw new IllegalArgumentException("No such note");
    }
  }

  @Override
  public void mergeSheet(MusicOperations m) {
    this.addSheet(m, 0);
  }

  @Override
  public void appendSheet(MusicOperations m) {
    this.addSheet(m, this.getLastBeat() + 1);
  }

  /**
   * Adds the provided music sheet with the given offset.
   * @param m the music sheet to add
   * @param delta the offset to shift the sheet by
   * @throws IllegalArgumentException if {@code delta} is less than 0
   */
  private void addSheet(MusicOperations m, int delta) throws IllegalArgumentException {
    if (delta < 0) {
      throw new IllegalArgumentException("delta must be non-negative");
    }

    for (PitchSequence mPitch : m.getPitches()) {
      if (this.hasPitch(mPitch.getOctave(), mPitch.getNoteType())) {
        this.getPitch(mPitch.getOctave(), mPitch.getNoteType()).addAll(mPitch, delta);
      }
      else {
        this.pitches.add(new PitchSequence(mPitch.getOctave(),
                mPitch.getNoteType()).addAll(mPitch, delta));
      }
    }
  }


  @Override
  public String getSheet() {
    StringBuilder s = new StringBuilder();
    int lastBeat = this.getLastBeat();
    int padToSize = (lastBeat + "").length();

    this.removeEmpty();
    if (this.pitches.isEmpty()) {
      return "";
    }

    Collections.sort(this.pitches);
    ArrayList<String> pitchStrings = new ArrayList<String>();

    // get string representations of pitch sequences
    // to avoid repeated toString() calls
    for (PitchSequence p : this.pitches) {
      pitchStrings.add(p.toString());
    }

    // make header
    for (int i = 0; i < padToSize; i++) {
      s.append(" ");
    }
    for (PitchSequence p : this.pitches) {
      s.append(StringUtilities.center(p.getHeader(), 5));
    }
    s.append("\n");

    //add all beats
    int padDelta;
    String noteAtBeat;
    for (int i = 0; i <= lastBeat; i++) {
      // add beat index
      padDelta = padToSize - (i + "").length();
      s.append(StringUtilities.padLeft(i + "", padDelta));

      // add beat at each note
      for (String ps : pitchStrings) {
        if (i >= ps.length()) {
          noteAtBeat = " ";
        }
        else {
          noteAtBeat = ps.charAt(i) + "";
        }
        s.append(StringUtilities.center(noteAtBeat, 5));
      }
      s.append("\n");
    }

    return s.toString();
  }


  @Override
  public int getLastBeat() {
    int lastBeat = 0;
    for (PitchSequence p : this.pitches) {
      if (p.getLastBeat() > lastBeat) {
        lastBeat = p.getLastBeat();
      }
    }
    return lastBeat;
  }

  @Override
  public ArrayList<PitchSequence> getPitches() {
    this.removeEmpty();
    ArrayList<PitchSequence> copy = new ArrayList<PitchSequence>();

    for (PitchSequence p : this.pitches) {
      copy.add(p.copy());
    }
    Collections.sort(copy);
    return copy;
  }


  /**
   * Removes empty {@link PitchSequence}s from {@code pitches}.
   */
  private void removeEmpty() {
    ArrayList<PitchSequence> removed = new ArrayList<PitchSequence>();
    for (PitchSequence p : this.pitches) {
      if (!p.isEmpty()) {
        removed.add(p);
      }
    }
    this.pitches = removed;
  }

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
