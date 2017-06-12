package cs3500.music.view;

import cs3500.music.model.PitchSequence;
import java.util.List;

/**
 * Abstract class for view implementations that defines shared fields and methods.
 */
public abstract class AView implements IView {
  List<PitchSequence> pitches;

  @Override
  public void setNotes(List<PitchSequence> pitches) {
    this.pitches = pitches;
  }
}
