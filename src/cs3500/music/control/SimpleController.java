package cs3500.music.control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

import javax.smartcardio.TerminalFactory;

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
  private IView[] views;
  private int currentBeat;
  private int tempo;
  private boolean playing;
  private boolean terminateAtEnd;

  /**
   * Constructs a simple controller with a model and one or more views.
   * @param model the model to connect
   * @param view the views to connect
   */
  public SimpleController(MusicOperations model, IView... view) {
    this.model = model;
    this.views = view;
    this.currentBeat = 0;
    this.playing = false;
    this.terminateAtEnd = false;
    this.tempo = 1000000; // 1 second per beat default (in microseconds)
  }

  /**
   * Constructs a simple controller with a model, one or more views, and a default {@code playing}
   * argument to allow the controller to immediately start playing.
   * @param model the model to connect
   * @param playToLastBeat whether the controller should immediately play and terminate upon the
   *                       reaching the last beat
   * @param view the views to connect
   */
  public SimpleController(MusicOperations model, boolean playToLastBeat,  IView... view) {
    this(model, view);
    this.playing = true;
    this.terminateAtEnd = true;
  }


  @Override
  public void setTempo(int tempo) {
    this.tempo = tempo;
    for (IView v : this.views) {
      v.setTempo(tempo);
    }
  }

  @Override
  public void setViewNotes() {
    for (IView v : views) {
      v.setNotes(this.model.getPitches());
    }
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
    for (IView v : views) {
      v.setKeyListener(this);
      v.initialize();
    }

    this.setViewNotes();

    while (this.allViewsActive()) {
      if (this.playing) {
        this.sleep();
        this.changeBeatBy(1);
      }
      for (IView v : views) {
        v.refresh();
      }

      if (terminateAtEnd && this.currentBeat == model.getLastBeat()) {
        return;
      }

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
        this.playing = !this.playing;
        break;
      default:
        return;
    }
    for (IView v : views) {
      v.setCurrentBeat(this.currentBeat);
    }
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
    for (IView v : views) {
      v.setCurrentBeat(this.currentBeat);
    }
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

  /**
   * Determine if all views are active.
   * @return true if all views are active
   */
  private boolean allViewsActive() {
    for (IView view : this.views) {
      if (!view.isActive()) {
        return false;
      }
    }
    return true;
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
