package cs3500.music.control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

import cs3500.music.model.MusicOperations;
import cs3500.music.view.IView;

/**
 * A simple controller to connect the view and the model.
 */
public class SimpleController implements IController, KeyListener {
  private MusicOperations model;
  private IView view;
  private int currentBeat;
  private int tempo;

  public SimpleController(MusicOperations model, IView view) {
    this.model = model;
    this.view = view;
    this.currentBeat = 0;
  }

  /**
   * Set the tempo to play at.
   * @param tempo tempo in beats per minute
   */
  public void setTempo(int tempo) {
    this.tempo = tempo;
  }

  @Override
  public void go() {
    this.view.setKeyListener(this);
    this.view.initialize(); //TODO do we need this?
  }

  @Override
  public void keyTyped(KeyEvent e) {
    return;
  }

  @Override
  public void keyPressed(KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_LEFT:
        this.changeBeatBy(-1);
        break;
      case KeyEvent.VK_RIGHT:
        this.changeBeatBy(1);
        break;
      case KeyEvent.VK_SPACE:
        //TODO move through at tempo
        break;
      default:
        return;
    }
    this.view.setCurrentBeat(this.currentBeat);
  }

  /**
   * Attempts to change the beat by the given amount.
   * @param delta the amount to change by
   */
  private void changeBeatBy(int delta) {
    if (this.currentBeat + delta < 0) {
      return;
    }
    this.currentBeat += delta;
    this.view.setCurrentBeat(this.currentBeat);
  }

  //TODO play at tempo

  @Override
  public void keyReleased(KeyEvent e) {
    return;
  }
}
