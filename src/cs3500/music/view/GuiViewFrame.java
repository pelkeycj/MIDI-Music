package cs3500.music.view;

import cs3500.music.model.PitchSequence;
import java.awt.*;

import java.awt.event.KeyListener;
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
    super();

    this.setTitle("GUI view");
    this.setSize(500,500);
    this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

    //use a border layout with a note panel up top and a piano panel south
    this.setLayout(new BorderLayout());
    pianoPanel = new PianoPanel();
    pianoPanel.setPreferredSize(new Dimension(500,250));
    this.add(pianoPanel, BorderLayout.SOUTH);

    this.sheetPanel = new SheetPanel();
    this.add(sheetPanel, BorderLayout.NORTH);

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

  }

  @Override
  public void setCurrentBeat(int beat) throws IllegalArgumentException {

  }

  @Override
  public void refresh() {

  }

  @Override
  public void setKeyListener(KeyListener keyListener) {
    this.addKeyListener(keyListener);
  }
}
