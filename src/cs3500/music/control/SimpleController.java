package cs3500.music.control;

import cs3500.music.model.RepeatInstr;
import cs3500.music.view.PlayingMode;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import cs3500.music.model.MusicOperations;
import cs3500.music.model.NoteType;
import cs3500.music.model.Octave;
import cs3500.music.model.Pitch;
import cs3500.music.view.IView;

/**
 * A simple controller to connect the view and the model.
 */
public class SimpleController implements IController {
  protected MusicOperations model;
  protected IView[] views;
  protected int currentBeat;
  protected int tempo;
  protected boolean playing;
  protected boolean terminateAtEnd;
  protected boolean practice;
  protected int lastBeat;

  protected KeyStrategy  keyStrategy;
  protected MouseStrategy mouseStrategy;

  protected boolean addingNote;
  protected int mousePressStartBeat;
  protected long mousePressTime;

  protected final double TEMPO_MULTIPLIER = .1; // 10% of tempo
  protected final int MICRO_TO_NANO = 1000;


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
    this.practice = false;
    this.addingNote = false;
    this.mousePressTime = 0;
    this.mousePressStartBeat = 0;
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
    this.practice = false;
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
      v.setRepeats(this.model.getRepeats());
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
  public void addRepeat(RepeatInstr r) {
    this.model.addRepeat(r);
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

    while (this.allViewsActive()) {
      if (this.playing || (this.practice && allViewsReady())) {
        this.updateViewBeat();
        this.sleep();
        this.changeBeatBy(1);
        repeatCheck();
      }

      // if currently adding a note (mouse was pressed down),
      // keep moving cursor at a rate of this.tempo until mouse released
      while (this.addingNote) {
        long elapsedTime = System.nanoTime() - this.mousePressTime;
        if (elapsedTime >= this.tempo * MICRO_TO_NANO) {
          mousePressTime = System.nanoTime();
          this.changeBeatBy(1);
          this.updateViewBeat();
          this.refreshAll();
        }
        repeatCheck();
      }
      refreshAll();

      if (terminateAtEnd && this.currentBeat == this.lastBeat) {
        return;
      }
    }
  }

  /**
   * Sets the key strategy to use for handling keyboard input. <br>
   * LEFT/RIGHT     - move the cursor left and right. <br>
   * SPACE          - play through the piece. <br>
   * BACKSPACE/HOME - place cursor at start of piece. <br>
   * ENTER/END      - place cursor at end of piece. <br>
   * A/D            - decrease/increase tempo by 10% <br.
   * P              - toggle practice/performance modes. <br>
   */
  protected void setKeyStrategy() {
    Map<Integer, Runnable> keyTypes = new HashMap<>();
    Map<Integer, Runnable> keyPresses = new HashMap<>();
    Map<Integer, Runnable> keyReleases = new HashMap<>();

    SimpleController sc = this;

    // increase/decrease tempo by 10%
    keyPresses.put(KeyEvent.VK_A, () -> {
      sc.tempo += sc.tempo * sc.TEMPO_MULTIPLIER;// increase seconds per beat by 10%
    });

    keyPresses.put(KeyEvent.VK_D, () -> {
      int temp =  sc.tempo -  (int) (Math.floor(sc.tempo * sc.TEMPO_MULTIPLIER)); //decrease by 10%
      if (!(temp < 0)) {
        sc.tempo = temp;
      }
    });

    // move cursor left/right
    keyPresses.put(KeyEvent.VK_LEFT, () -> {
      sc.playing = false;
      sc.changeBeatBy(-1);
      sc.updateViewBeat();
      sc.refreshAll();
    });

    keyPresses.put(KeyEvent.VK_RIGHT, () -> {
      sc.playing = false;
      sc.changeBeatBy(1);
      sc.updateViewBeat();
      sc.refreshAll();
    });

    // toggle play through
    keyPresses.put(KeyEvent.VK_SPACE, () -> {
      if (!(this.practice)) {
        sc.playing = !sc.playing;
      }
      sc.refreshAll();
    });

    // move to start of piece
    Runnable toStart = () -> {
      //sc.playing = false;
      sc.currentBeat = 0;
      sc.updateViewBeat();
      sc.model.resetRepeats();
    };

    keyPresses.put(KeyEvent.VK_BACK_SPACE, toStart);
    keyPresses.put(KeyEvent.VK_HOME, toStart);

    // move to end of piece
    Runnable toEnd = () -> {
      sc.playing = false;
      sc.currentBeat = sc.lastBeat + 1;
      sc.updateViewBeat();
    };

    keyPresses.put(KeyEvent.VK_ENTER, toEnd);
    keyPresses.put(KeyEvent.VK_END, toEnd);

    // scroll sheet up/down
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

    keyPresses.put(KeyEvent.VK_P, () -> {
      if (sc.practice) {
        for (IView v : sc.views) {
          v.setPlayingMode(PlayingMode.PERFORMANCE);
        }
        sc.practice = false;
        sc.playing = true;
      } else {
        for (IView v : sc.views) {
          v.setPlayingMode(PlayingMode.PRACTICE);
        }
        sc.practice = true;
        sc.playing = false;
      }
      sc.refreshAll();
    });

    this.keyStrategy = new KeyHandler();
    this.keyStrategy.setKeyTypedStrategy(keyTypes);
    this.keyStrategy.setKeyPressedStrategy(keyPresses);
    this.keyStrategy.setKeyReleasedStrategy(keyReleases);
  }

  /**
   * Sets the mouse strategy to use for handling user mouse input.<br>
   * Mouse Click - activate note if practicing. <br>
   * Mouse Press - begin adding note to sheet based on tempo. <br>
   * Mouse Release - add note to sheet. <br>
   *
   */
  protected void setMouseStrategy() {
    Map<Integer, MouseEventProcessor> mouseEvents = new HashMap<>();

    SimpleController sc = this;

    mouseEvents.put(MouseEvent.MOUSE_CLICKED, e -> {
      int x = e.getX();
      int y = e.getY();

      Pitch p = null;
      for (IView v : sc.views) {
        p = v.getPitchAt(x, y);
        if (p != null) {
          break;
        }
      }
      if (p == null) {
        return;
      }
      else if (p.getOctave().getValue() * 10 + p.getNote().getValue() > 127) {
        return; // midi cannot handle
      }

      if (sc.practice) {
        for (IView v : this.views) {
          v.activateNote(p);
        }
      }
    });

    // press down
    mouseEvents.put(MouseEvent.MOUSE_PRESSED, e -> {
      if (!this.practice) {
        sc.addingNote = true;
        sc.mousePressStartBeat = sc.currentBeat;
        sc.mousePressTime = System.nanoTime();
      }
    });

    // release
    mouseEvents.put(MouseEvent.MOUSE_RELEASED, e -> {
      if (!this.addingNote || this.practice) {
        return;
      }

      sc.addingNote = false;
      int x = e.getX();
      int y = e.getY();

      Pitch p = null;
      for (IView v : sc.views) {
        p = v.getPitchAt(x, y);
        if (p != null) {
          break;
        }
      }
      if (p == null) {
        return;
      }
      else if (p.getOctave().getValue() * 10 + p.getNote().getValue() > 127) {
        return; // midi cannot handle
      }

      try {
        sc.addNote(p.getOctave(), p.getNote(),
                sc.mousePressStartBeat, sc.currentBeat + 1, 3, 100);
        sc.currentBeat++;
        sc.updateViewBeat();
        sc.lastBeat = sc.model.getLastBeat()  + 1;
        for (IView v : sc.views) {
          v.setNotes(sc.model.getPitches());
        }
      }
      catch (IllegalArgumentException exception) {
        // ignore duplicate notes
        return;
      }
      sc.refreshAll();
    });

    this.mouseStrategy = new MouseHandler();
    mouseStrategy.setMouseEvents(mouseEvents);
  }

  /**
   * Attempts to change the beat by the given amount.
   * @param delta the amount to change by
   */
  private void changeBeatBy(int delta) {
    if (this.currentBeat + delta < 0) {
            //|| this.currentBeat + delta > lastBeat + 1) {
      return;
    }
    this.currentBeat += delta;
  }

  /**
   * Sets the song to the repeat beat if the current beat is the last beat of an uncompleted beat.
   */
  private void repeatCheck() {
    RepeatInstr r = model.repeatAt(this.currentBeat);
    if (r != null && !(r.isCompleted()) && this.currentBeat == r.lastBeat()) {
      this.currentBeat = r.firstBeat();
      r.complete();
    }
  }

  /**
   * Sleep the program for {@code tempo} microseconds.
   */
  private void sleep() {
    long elapsedTime = 0;
    final long INITIAL_TIME = System.nanoTime();

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

  /**
   * Refresh all views at once.
   */
  private void refreshAll() {
    for (IView v : this.views) {
      v.refresh();
    }
  }


  //NEW FOR HW 9=================================================================

  /**
   * Determines if all views are ready to play.
   * @return true of all views are ready to play.
   */
  private boolean allViewsReady() {
    boolean output = true;
    for (IView v : this.views) {
      output = output && v.advanceReady();
    }
    return output;
  }
}
