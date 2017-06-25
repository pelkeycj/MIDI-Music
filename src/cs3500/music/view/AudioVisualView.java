package cs3500.music.view;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.List;

import cs3500.music.model.Pitch;
import cs3500.music.model.PitchSequence;

/**
 * An audiovisual view for a piece of music. The view contains both an
 * audio view and a visual view. This 'wrapper' view keeps the two contained
 * views in sync so that music is heard audibly at the same rate as it is played visually.
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

  @Override
  public boolean advanceReady() {
    return this.visual.advanceReady() && this.audio.advanceReady();
  }

  @Override
  public void setPlayingMode(PlayingMode mode) {
    this.visual.setPlayingMode(mode);
    this.audio.setPlayingMode(mode);
  }

  @Override
  public void activateNote(Pitch p) {
    this.visual.activateNote(p);
    this.audio.activateNote(p);
  }
}
