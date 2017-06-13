package cs3500.music.view;

import cs3500.music.model.PitchSequence;
import java.awt.*;

import java.util.*;
import java.util.List;
import javax.swing.*;

/**
 * A skeleton Frame (i.e., a window) in Swing
 */
public class GuiViewFrame extends javax.swing.JFrame implements IView {


  private SheetPanel sheetPanel;
  private PianoPanel pianoPanel;

  /**
   * Creates new GuiView
   */
  public GuiViewFrame() {
    this.pianoPanel = new PianoPanel();
    this.sheetPanel = new SheetPanel();

    this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    this.getContentPane().add(displayPanel);
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

  }

  @Override
  public void makeVisible() {

  }

  @Override
  public void setCurrentBeat(int beat) throws IllegalArgumentException {

  }

  @Override
  public void refresh() {

  }
}
