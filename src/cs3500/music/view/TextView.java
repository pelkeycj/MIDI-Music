package cs3500.music.view;

import cs3500.music.model.PitchSequence;
import cs3500.music.util.StringUtilities;

import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Presents a textual representation of the notes.
 */
public class TextView extends AView {
  Appendable out;

  public TextView(Appendable out) {
    this.out = out;
  }


  @Override
  public void initialize() {
    //do nothing to initialize this view
    return;
  }

  @Override
  public void setCurrentBeat(int beat) throws IllegalArgumentException {
    //do nothing
    return;
  }

  @Override
  public void setNotes(List<PitchSequence> pitches) {
    super.setNotes(pitches);
    //System.out.println(drawNotes());
    for (char c : this.drawNotes().toCharArray()) {
      try {
        out.append(c);
      }
      catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void refresh() {
   initialize();
  }

  @Override
  public void setKeyListener(KeyListener keyListener) {
    return;
  }

  /**
   * Format the pitches data into a string output.
   * @return a string representing the notes given to the view.
   */
  private String drawNotes() {
    StringBuilder s = new StringBuilder();
    int lastBeat = this.getLastBeat();
    int padToSize = (lastBeat + "").length();

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

  /**
   * Gets the last beat of this sheet.
   * @return the index of the last beat of this sheet
   */
  private int getLastBeat() {
    int lastBeat = 0;
    for (PitchSequence p : this.pitches) {
      if (p.getLastBeat() > lastBeat) {
        lastBeat = p.getLastBeat();
      }
    }
    return lastBeat;
  }
}
