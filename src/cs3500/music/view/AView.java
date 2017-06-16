package cs3500.music.view;

import cs3500.music.model.PitchSequence;

import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.*;

/**
 * Abstract class for view implementations that defines shared fields and methods.
 */
public abstract class AView extends JFrame implements IView {
  List<PitchSequence> pitches;
  boolean active = false;

  @Override
  public void setNotes(List<PitchSequence> pitches) {
    this.pitches = pitches;
  }

  @Override
  public void setTempo(int tempo) {
    //in many views, this info is not needed
    return;
  }

  @Override
  public void setKeyListener(KeyListener keyListener) {
    this.addKeyListener(keyListener);
  }

  @Override
  public boolean isActive() {
    return this.active;
  }
}
