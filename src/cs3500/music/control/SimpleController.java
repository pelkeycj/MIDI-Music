package cs3500.music.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import cs3500.music.model.MIDI;
import cs3500.music.model.MusicOperations;
import cs3500.music.view.GuiViewFrame;
import cs3500.music.view.IView;

/**
 * A simple controller to connect the view and the model.
 */
public class SimpleController implements IController, KeyListener {
  private MusicOperations model;
  private IView view;
  private int currentBeat;

  public SimpleController(MusicOperations model, IView view) {
    this.model = model;
    this.view = view;
    this.currentBeat = 0;
  }


  @Override
  public void go() {
    this.view.setKeyListener(this);
    this.view.initialize(); //TODO do we need this?
  }

  @Override
  public void keyTyped(KeyEvent e) {
    //TODO
  }

  @Override
  public void keyPressed(KeyEvent e) {
    //TODO
  }

  @Override
  public void keyReleased(KeyEvent e) {
    //TODO
  }
}
