package cs3500.music.view;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.List;

import cs3500.music.model.Pitch;
import cs3500.music.model.PitchSequence;

/**
 * Created by connor on 6/19/17.
 */
public class AudioVisualView implements IView {
  IView visual;
  IView audio;

  public AudioVisualView(IView visual, IView audio) {
    this.visual = visual;
    this.audio = audio;
  }


  @Override
  public void setNotes(List<PitchSequence> pitches) {
    this.visual.setNotes(pitches);
    this.audio.setNotes(pitches);
  }

  @Override
  public void initialize() {
    this.visual.initialize();
    this.audio.initialize();
  }

  @Override
  public void setCurrentBeat(int beat) {
    this.visual.setCurrentBeat(beat);
    this.audio.setCurrentBeat(beat);
  }

  @Override
  public void refresh() {
    this.visual.refresh();
    this.audio.refresh();
  }

  @Override
  public void setKeyListener(KeyListener keyListener) {
    this.visual.setKeyListener(keyListener);
    this.audio.setKeyListener(keyListener);
  }

  @Override
  public void setTempo(int tempo) {
    this.visual.setTempo(tempo);
    this.audio.setTempo(tempo);
  }

  @Override
  public boolean isActive() {
    return this.visual.isActive() && this.audio.isActive();
  }

  @Override
  public void setMouseListener(MouseListener mouseListener) {
    this.visual.setMouseListener(mouseListener);
    this.audio.setMouseListener(mouseListener);
  }

  @Override
  public void scrollVertical(int direction) {
    this.visual.scrollVertical(direction);
    this.audio.scrollVertical(direction);
  }

  @Override
  public Pitch getPitchAt(int x, int y) throws IllegalArgumentException {
    return this.visual.getPitchAt(x, y);
  }
}
