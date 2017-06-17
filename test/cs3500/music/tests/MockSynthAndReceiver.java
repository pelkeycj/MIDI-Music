package cs3500.music.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import cs3500.music.view.MockReceiver;
import cs3500.music.view.MockSynthesizer;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import org.junit.Test;

/**
 * Tests of the MockSynthesizer and MockReceiver obejcts.
 */
public class MockSynthAndReceiver {

  @Test
  public void testGetReceiver() throws MidiUnavailableException {
    StringBuilder log = new StringBuilder();
    Synthesizer synth = new MockSynthesizer(log);

    assertTrue(synth.getReceiver() instanceof Receiver);
    assertTrue(synth.getReceiver() instanceof MockReceiver);
  }


  @Test
  public void testLogUpdate() throws MidiUnavailableException, InvalidMidiDataException {
    StringBuilder log = new StringBuilder();
    Synthesizer synth = new MockSynthesizer(log);
    Receiver r = synth.getReceiver();

    assertEquals("", log.toString());

    MidiMessage m = new ShortMessage(ShortMessage.NOTE_ON, 0, 60, 70);
    r.send(m, 1000);

    assertEquals("Command: NOTE_ON\tChannel: 0\tPitch: C5\tLoudness: 70\tTimestamp: 1000\n",
        log.toString());
  }

  @Test
  public void testLogOrderPreservation() throws MidiUnavailableException, InvalidMidiDataException {
    StringBuilder log = new StringBuilder();
    Synthesizer synth = new MockSynthesizer(log);
    Receiver r = synth.getReceiver();

    assertEquals("", log.toString());

    MidiMessage m = new ShortMessage(ShortMessage.NOTE_ON, 0, 60, 70);
    r.send(m, 1000);

    MidiMessage m1 = new ShortMessage(ShortMessage.NOTE_OFF, 0, 42, 00);
    r.send(m1, 3000);

    assertEquals("Command: NOTE_ON\tChannel: 0\tPitch: C5\tLoudness: 70\tTimestamp: 1000\n"
            + "Command: NOTE_OFF\tChannel: 0\tPitch: F#3\tLoudness: 0\tTimestamp: 3000\n",
        log.toString());
  }



}
