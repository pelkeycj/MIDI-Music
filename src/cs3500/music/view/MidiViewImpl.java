package cs3500.music.view;

import cs3500.music.model.PitchSequence;
import java.util.List;
import javax.sound.midi.*;

/**
 * A skeleton for MIDI playback
 */
public class MidiViewImpl extends AView {
  private final Synthesizer synth;
  private final Receiver receiver;

  public MidiViewImpl() throws MidiUnavailableException {
    this.synth = MidiSystem.getSynthesizer();
    this.receiver = synth.getReceiver();
    this.synth.open();
  }
  /**
   * Relevant classes and methods from the javax.sound.midi library:
   * <ul>
   *  <li>{@link MidiSystem#getSynthesizer()}</li>
   *  <li>{@link Synthesizer}
   *    <ul>
   *      <li>{@link Synthesizer#open()}</li>
   *      <li>{@link Synthesizer#getReceiver()}</li>
   *      <li>{@link Synthesizer#getChannels()}</li>
   *    </ul>
   *  </li>
   *  <li>{@link Receiver}
   *    <ul>
   *      <li>{@link Receiver#send(MidiMessage, long)}</li>
   *      <li>{@link Receiver#close()}</li>
   *    </ul>
   *  </li>
   *  <li>{@link MidiMessage}</li>
   *  <li>{@link ShortMessage}</li>
   *  <li>{@link MidiChannel}
   *    <ul>
   *      <li>{@link MidiChannel#getProgram()}</li>
   *      <li>{@link MidiChannel#programChange(int)}</li>
   *    </ul>
   *  </li>
   * </ul>
   * @see <a href="https://en.wikipedia.org/wiki/General_MIDI">
   *   https://en.wikipedia.org/wiki/General_MIDI
   *   </a>
   */

  public void playNote() throws InvalidMidiDataException {
    MidiMessage start = new ShortMessage(ShortMessage.NOTE_ON, 0, 60, 64);
    MidiMessage stop = new ShortMessage(ShortMessage.NOTE_OFF, 0, 60, 64);
    this.receiver.send(start, -1);
    this.receiver.send(stop, this.synth.getMicrosecondPosition() + 200000);

    /*
    The receiver does not "block", i.e. this method
    immediately moves to the next line and closes the
    receiver without waiting for the synthesizer to
    finish playing.

    You can make the program artificially "wait" using
    Thread.sleep. A better solution will be forthcoming
    in the subsequent assignments.
    */
    this.receiver.close(); // Only call this once you're done playing *all* notes
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
