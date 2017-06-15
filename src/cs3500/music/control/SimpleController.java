package cs3500.music.control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

import cs3500.music.model.MusicOperations;
import cs3500.music.model.NoteType;
import cs3500.music.model.Octave;
import cs3500.music.model.PitchSequence;
import cs3500.music.view.IView;

/**
 * A simple controller to connect the view and the model.
 */
public class SimpleController implements IController, KeyListener {
  private MusicOperations model;
  private IView view;
  private int currentBeat;
  private int tempo;
  private boolean playing;

  public SimpleController(MusicOperations model, IView view) {
    this.model = model;
    this.view = view;
    this.currentBeat = 0;
    this.playing = false;
    this.tempo = 1000000; // 1 second per beat default (in microseconds)
  }


  @Override
  public void setTempo(int tempo) {
    this.tempo = tempo;
  }

  @Override
  public void setViewNotes() {
    this.view.setNotes(this.model.getPitches());
  }

  @Override
  public void addNote(Octave o, NoteType nt, int start, int end) {
    this.model.addNote(o, nt, start, end);
  }

  @Override
  public void addNote(Octave o, NoteType nt, int start, int end, int instrument, int loudness) {
    this.model.addNote(o, nt, start, end, instrument, loudness);
  }

  @Override
  public void go() {
    this.view.setKeyListener(this);
    this.view.initialize();
    this.setViewNotes();

    while (true) {
      if (this.playing) {
        this.sleep();
        this.changeBeatBy(1);
      }
      this.view.refresh();
    }
  }

  @Override
  public void keyPressed(KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_LEFT:
        this.playing = false;
        this.changeBeatBy(-1);
        break;
      case KeyEvent.VK_RIGHT:
        this.playing = false;
        this.changeBeatBy(1);
        break;
      case KeyEvent.VK_SPACE:
        this.playing = true;
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
    int lastBeat = this.model.getLastBeat();
    int numMeasures = (lastBeat / 4) + 1;
    lastBeat = numMeasures * 4;

    if (this.currentBeat + delta < 0
            || this.currentBeat + delta > lastBeat) {
      return;
    }
    this.currentBeat += delta;
    this.view.setCurrentBeat(this.currentBeat);
  }

  /**
   * Sleep the program for {@code tempo} microseconds.
   */
  private void sleep() {
    long elapsedTime = 0;
    final long initialTime = System.nanoTime();
    final int microToNano = 1000;

    while (elapsedTime < this.tempo * microToNano) {
      elapsedTime = System.nanoTime() - initialTime;
    }


  }

  @Override
  public void keyTyped(KeyEvent e) {
    return;
  }

  @Override
  public void keyReleased(KeyEvent e) {
    return;
  }
}
