package cs3500.music.view;

import cs3500.music.model.Pitch;
import cs3500.music.model.PitchSequence;
import java.awt.Dimension;
import java.awt.BorderLayout;

import java.awt.event.KeyListener;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * <p>Graphical View for the music sheets. Contains two main panels:</p>
 * <p>- a sheet-like view on the upper
 *   portion that shows the notes of a musical piece as well as one's current position in the piece.
 *   The current position is marked by the position of a red bar that is position on the left-hand
 *   edge of the current beat. </p>
 * <p>- a piano view on the lower portion of the window.
 *   The keys of this piano highlight to show which
 *   notes are being played on the current beat.</p>
 *
 * <p>The current beat beat displayed by this view can be set by a public method and the
 * notes to display on both these panels is also controlled by this view. Each panel receives
 * information specific to its type of display. This distribution of information is handled by
 * the view.</p>
 */
public class GuiViewFrame extends AView {

  private final int MIN_WIDTH = 1000;
  private final int MIN_HEIGHT = 500;


  private SheetPanel sheetPanel;
  private PianoPanel pianoPanel;
  private ScrollingSheet scrollingSheet;

  private List<PitchSequence> pitches;

  /**
   * Public constructor for the Gui view. Defines the layout for the view and initializes the two
   * main panels of the view.
   */
  public GuiViewFrame() {
    super();
    this.pitches = new ArrayList<>();

    this.setTitle("GUI view");
    this.setSize(MIN_WIDTH,MIN_HEIGHT);
    this.setMinimumSize(new Dimension(MIN_WIDTH,MIN_HEIGHT));
    this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

    //use a border layout with a note panel up top and a piano panel south
    this.setLayout(new BorderLayout());
    pianoPanel = new PianoPanel(MIN_WIDTH, MIN_HEIGHT / 2);
    pianoPanel.setPreferredSize(new Dimension(MIN_WIDTH,MIN_HEIGHT / 2));
    this.add(pianoPanel, BorderLayout.SOUTH);

    this.sheetPanel = new SheetPanel();
    this.sheetPanel.setPreferredSize(new Dimension(MIN_WIDTH, MIN_HEIGHT / 2));
    this.scrollingSheet = new ScrollingSheet(sheetPanel);
    this.scrollingSheet.setPreferredSize(new Dimension(MIN_WIDTH, MIN_HEIGHT / 2));
    this.scrollingSheet.setRowHeaderView(new RowHeaderPanel(this.pitches));
    this.add(scrollingSheet, BorderLayout.NORTH);

    //this.getContentPane().add(displayPanel);
    this.pack();
  }

  @Override
  public void initialize() {
    this.setVisible(true);
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(100, 100);
  }

  @Override
  public void setNotes(List<PitchSequence> pitches) {
    this.pitches = this.deepCopyPitches(pitches);
    this.sheetPanel.setNotes(this.deepCopyPitches(pitches));
    this.scrollingSheet.setRowHeaderView(new RowHeaderPanel(this.deepCopyPitches(pitches)));
  }

  @Override
  public void setCurrentBeat(int beat) throws IllegalArgumentException {
    //check which beats are current playing to toggle them on
    HashSet<Pitch> playingPitches = new HashSet<>();
    for (PitchSequence p : this.pitches) {
      if (p.playingAt(beat)) {
        playingPitches.add(p.getPitchCopy());
      }
    }
    pianoPanel.setOnKeys(playingPitches);
    sheetPanel.setCurrentBeat(beat);
  }

  @Override
  public boolean isActive() {
    return this.isVisible();
  }

  @Override
  public void refresh() {
    Dimension currentSize = this.getSize();
    int width = currentSize.width;
    int height = currentSize.height;
    this.scrollingSheet.setPreferredSize(new Dimension(width, height - MIN_HEIGHT / 2));
    this.repaint();
  }

  @Override
  public void setKeyListener(KeyListener keyListener) {
    super.setKeyListener(keyListener);
  }

  @Override
  public void scrollVertical(int direction) {
    this.scrollingSheet.scrollVertical(direction);
  }

  @Override
  public Pitch getPitchAt(int x, int y){
    // modify y to be used by piano panel
    int bottomPaneLocation = (int) this.scrollingSheet.getSize().getHeight();

    y = y - bottomPaneLocation;

    if (y < 0) {
      return null;
    }

    return this.pianoPanel.keyAt(x, y);
  }

  /**
   * Gets a deep copy of a list of {@link PitchSequence}s.
   * @param p a list of {@link PitchSequence}s
   * @return a deep copy of {@code p}
   */
  private List<PitchSequence> deepCopyPitches(List<PitchSequence> p) {
    List<PitchSequence> copy = new ArrayList<PitchSequence>();
    for (PitchSequence pitch : p) {
      copy.add(pitch.copy());
    }
    return copy;
  }
}
