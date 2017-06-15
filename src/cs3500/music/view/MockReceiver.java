package cs3500.music.view;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;

/**
 * Mocks a midi receiver. Used to output a debugging log for the midi view by
 * printing output when the send message is called.
 */
public class MockReceiver implements Receiver {

  StringBuilder log;

  public MockReceiver(StringBuilder log) {
    this.log = log;
  }


  @Override
  public void send(MidiMessage m, long timestamp) {
    log.append(m.toString()).append("time stamp ").append(timestamp).append("\n");
  }

  @Override
  public void close() {

  }
}
