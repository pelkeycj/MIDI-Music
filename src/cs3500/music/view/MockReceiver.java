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
    ShortMessage s = (ShortMessage) m;
    int channel = s.getChannel();
    int pitch = s.getData1();
    int loudness = s.getData2();
    int command = s.getCommand();
    log.append("Command: ").append(command).append(" ");
    log.append("Channel: ").append(channel).append(" ");
    log.append("Data1: ").append(pitch).append(" ");
    log.append("Data2: ").append(loudness).append(" ");
    log.append("Timestamp: ").append(timestamp).append("\n");
  }

  @Override
  public void close() {
    //does nothing
  }
}
