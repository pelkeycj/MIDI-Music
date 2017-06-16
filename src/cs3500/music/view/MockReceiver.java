package cs3500.music.view;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

/**
 * Mocks a midi receiver. Used to output a debugging log for the midi view by
 * printing output when the send message is called.
 */
public class MockReceiver implements Receiver {

  StringBuilder log;

  /**
   * Public constructor for the mock receiver to modified the string builder it has been passed.
   * @param log
   */
  public MockReceiver(StringBuilder log) {
    this.log = log;
  }


  @Override
  //modifies its string builder to add information about what has been sent this receiver.
  public void send(MidiMessage m, long timestamp) {
    log.append(m.toString()).append(" time stamp ").append(timestamp).append("\n");
  }

  @Override
  public void close() {
    //does nothing
  }
}
