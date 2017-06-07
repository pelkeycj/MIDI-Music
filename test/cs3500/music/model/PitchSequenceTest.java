package cs3500.music.model;

import org.junit.Before;

import static org.junit.Assert.*;

/**
 * Tests for the {@link PitchSequence} class.
 */
public class PitchSequenceTest {
  PitchSequence<OctaveNumber1To10, NoteTypeWestern> p1D;
  PitchSequence<OctaveNumber1To10, NoteTypeWestern> p1A;
  PitchSequence<OctaveNumber1To10, NoteTypeWestern> p2C;

  /**
   * Initialize and reset.
   */
  @Before
  public void init() {
    p1D = new PitchSequence<>(OctaveNumber1To10.O1, NoteTypeWestern.D);
    p1A = new PitchSequence<>(OctaveNumber1To10.O1, NoteTypeWestern.A);
    p2C = new PitchSequence<>(OctaveNumber1To10.O2, NoteTypeWestern.C);
  }

  //Add note

  //remove note

}