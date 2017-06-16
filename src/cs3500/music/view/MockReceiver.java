package cs3500.music.view;

import cs3500.music.model.Pitch;
import java.io.IOException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

/**
 * Mocks a midi receiver. Used to output a debugging log for the midi view by
 * printing output when the send message is called.
 */
public class MockReceiver implements Receiver {

  Appendable log;

  /**
   * Public constructor for the mock receiver to modified the string builder it has been passed.
   * @param log
   */
  public MockReceiver(Appendable log) {
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

    String commandString = command == 144 ? "NOTE_ON" : "NOTE_OFF";
    String pitchString = Pitch.intToPitch(pitch).toString();

    try {
      log.append("Command: ").append(commandString).append("\t");
      log.append("Channel: ").append(Integer.toString(channel)).append("\t");
      log.append("Pitch: ").append(pitchString).append("\t");
      log.append("Loudness: ").append(Integer.toString(loudness)).append("\t");
      log.append("Timestamp: ").append(Long.toString(timestamp)).append("\n");
    } catch (IOException e) {
      throw new RuntimeException("Failed to append information to the log.");
    }

    //System.out.println(log);
  }

  @Override
  public void close() {
    //does nothing
  }
}
