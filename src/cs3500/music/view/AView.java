package cs3500.music.view;

import cs3500.music.model.PitchSequence;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JFrame;


/**
 * Abstract class for view implementations that defines shared fields and methods.
 */
public abstract class AView extends JFrame implements IView {
  List<PitchSequence> pitches;
  boolean active;

  @Override
  public void setNotes(List<PitchSequence> pitches) {
    this.pitches = pitches;
  }

  @Override
  public void setTempo(int tempo) {
    //in many views, this info is not needed
  }

  @Override
  public void setKeyListener(KeyListener keyListener) {
    this.addKeyListener(keyListener);
  }

  @Override
  public void setMouseListener(MouseListener mouseListener) {
    this.addMouseListener(mouseListener);
  }

  @Override
  public boolean isActive() {
    return this.active;
  }
}
