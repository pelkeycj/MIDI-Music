package cs3500.music.model;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;

/**
 * Mocks a midi receiver. Used to output a debugging log for the midi view by
 * printing output when the send message is called.
 */
public class MockReceiver implements Receiver {

  @Override
  public void send(MidiMessage m, long timestamp) {
    //prints out a log of the thing that is sent
  }

  @Override
  public void close() {

  }
}
