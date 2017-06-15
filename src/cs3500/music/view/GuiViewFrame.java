package cs3500.music.view;

import cs3500.music.model.Pitch;
import cs3500.music.model.PitchSequence;
import java.awt.*;

import java.awt.event.KeyListener;
import java.util.*;
import java.util.List;

/**
 * A skeleton Frame (i.e., a window) in Swing
 */

//TODO create jpanel class to hold row headers
public class GuiViewFrame extends javax.swing.JFrame implements IView {

  private final int MIN_WIDTH = 1000;
  private final int MIN_HEIGHT = 500;


  private SheetPanel sheetPanel;
  private PianoPanel pianoPanel;
  private ScrollingSheet scrollingSheet;

  private List<PitchSequence> pitches;

  /**
   * Creates new GuiView
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
  public void initialize(){
    this.setVisible(true);
  }

  @Override
  public Dimension getPreferredSize(){
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
  public void refresh() {
    Dimension currentSize = this.getSize();
    int width = currentSize.width;
    int height = currentSize.height;
    this.scrollingSheet.setPreferredSize(new Dimension(width, height - MIN_HEIGHT / 2));
    this.repaint();
  }

  @Override
  public void setKeyListener(KeyListener keyListener) {
    this.addKeyListener(keyListener);
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
