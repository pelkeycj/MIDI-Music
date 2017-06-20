package cs3500.music.control;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import cs3500.music.model.MusicOperations;
import cs3500.music.model.NoteType;
import cs3500.music.model.NoteTypeWestern;
import cs3500.music.model.Octave;
import cs3500.music.model.OctaveNumber0To10;
import cs3500.music.view.IView;

/**
 * A simple controller to connect the view and the model.
 */
public class SimpleController implements IController {
  private MusicOperations model;
  private IView[] views;
  private int currentBeat;
  private int tempo;
  private boolean playing;
  private boolean terminateAtEnd;
  private int lastBeat;
  private KeyStrategy  keyStrategy;
  private MouseStrategy mouseStrategy;

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
    this.setKeyStrategy();
    this.setMouseStrategy();
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
  public void control() {
    this.setLastBeat();
    for (IView v : views) {
      v.setKeyListener(this.keyStrategy);
      v.setMouseListener(this.mouseStrategy);
      v.initialize();
    }

    this.setViewNotes();
    this.updateViewBeat();

    while (this.allViewsActive()) {
      if (this.playing) {
        this.sleep();
        this.changeBeatBy(1);
        this.updateViewBeat();
      }

      for (IView v : views) {
        v.refresh();
      }

      if (terminateAtEnd && this.currentBeat == this.lastBeat) {
        return;
      }
    }
  }

  //TODO scroll up/down
  /**
   * Sets the key strategy to use for handling keyboard input. <br>
   * LEFT/RIGHT     - move the cursor left and right. <br>
   * SPACE          - play through the piece. <br>
   * BACKSPACE/HOME - place cursor at start of piece. <br>
   * ENTER/END      - place cursor at end of piece.
   */
  private void setKeyStrategy() {
    Map<Integer, Runnable> keyTypes = new HashMap<>();
    Map<Integer, Runnable> keyPresses = new HashMap<>();
    Map<Integer, Runnable> keyReleases = new HashMap<>();

    SimpleController sc = this;
    keyPresses.put(KeyEvent.VK_LEFT, () -> {
      sc.playing = false;
      sc.changeBeatBy(-1);
      sc.updateViewBeat();
    });

    keyPresses.put(KeyEvent.VK_RIGHT, () -> {
      sc.playing = false;
      sc.changeBeatBy(1);
      sc.updateViewBeat();
    });

    keyPresses.put(KeyEvent.VK_SPACE, () -> sc.playing = !sc.playing);

    Runnable toStart = () -> {
      sc.playing = false;
      sc.currentBeat = 0;
      sc.updateViewBeat();
    };

    keyPresses.put(KeyEvent.VK_BACK_SPACE, toStart);
    keyPresses.put(KeyEvent.VK_HOME, toStart);

    Runnable toEnd = () -> {
      sc.playing = false;
      sc.currentBeat = sc.lastBeat;
      sc.updateViewBeat();
    };

    keyPresses.put(KeyEvent.VK_ENTER, toEnd);
    keyPresses.put(KeyEvent.VK_END, toEnd);


    keyPresses.put(KeyEvent.VK_UP, () -> {
      for (IView v : sc.views) {
        v.scrollVertical(-1);
      }
    });

    keyPresses.put(KeyEvent.VK_DOWN, () -> {
      for (IView v : sc.views) {
        v.scrollVertical(1);
      }
    });

    this.keyStrategy = new KeyHandler();
    this.keyStrategy.setKeyTypedStrategy(keyTypes);
    this.keyStrategy.setKeyPressedStrategy(keyPresses);
    this.keyStrategy.setKeyReleasedStrategy(keyReleases);
  }

  private void setMouseStrategy() {
    Map<Integer, Runnable> mouseClicks = new HashMap<>();
    Map<Integer, Runnable> mousePresses = new HashMap<>();
    Map<Integer, Runnable> mouseEnters = new HashMap<>();
    Map<Integer, Runnable> mouseExits = new HashMap<>();
    Map<Integer, Runnable> mouseReleases = new HashMap<>();

    SimpleController sc = this;

    mouseClicks.put(MouseEvent.MOUSE_CLICKED, () -> {
      if (sc.playing) {
        return;
      }

      //TODO get pitch, octave, add note

      //TODO TEMPORARY
      sc.addNote(OctaveNumber0To10.O6, NoteTypeWestern.A_SHARP, sc.currentBeat,
              sc.currentBeat + 1, 1, 100);
      sc.currentBeat++;

      for (IView v : sc.views) {
        v.setCurrentBeat(sc.currentBeat);
        v.setNotes(sc.model.getPitches());
      }
    });


    this.mouseStrategy = new MouseHandler();
    mouseStrategy.setMouseClicks(mouseClicks);
    mouseStrategy.setMousePresses(mousePresses);
    mouseStrategy.setMouseEnters(mouseEnters);
    mouseStrategy.setMouseExits(mouseExits);
    mouseStrategy.setMouseReleased(mouseReleases);
  }

  /**
   * Attempts to change the beat by the given amount.
   * @param delta the amount to change by
   */
  private void changeBeatBy(int delta) {
    if (this.currentBeat + delta < 0
            || this.currentBeat + delta > lastBeat) {
      return;
    }
    this.currentBeat += delta;
  }

  /**
   * Sleep the program for {@code tempo} microseconds.
   */
  private void sleep() {
    long elapsedTime = 0;
    final long INITIAL_TIME = System.nanoTime();
    final int MICRO_TO_NANO = 1000;

    while (elapsedTime < this.tempo * MICRO_TO_NANO) {
      elapsedTime = System.nanoTime() - INITIAL_TIME;
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

  /**
   * Sets the last beat of the sheet to be the last beat from the model to avoid
   * repeated work.
   */
  private void setLastBeat() {
    this.lastBeat = this.model.getLastBeat();
  }

  /**
   * Sets the current beat of all views.
   */
  private void updateViewBeat() {
    for (IView v : this.views) {
      v.setCurrentBeat(this.currentBeat);
    }
  }
}
