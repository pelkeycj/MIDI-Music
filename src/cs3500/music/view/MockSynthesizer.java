package cs3500.music.view;

import java.util.List;
import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Patch;
import javax.sound.midi.Receiver;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Transmitter;
import javax.sound.midi.VoiceStatus;

/**
 * Mocks a MIDI synthesizer for testing purposes.
 * Produces a mock receiver that modifies this objects string builder object.
 */
public class MockSynthesizer implements Synthesizer {

  //string builder object tracks the log of what has been sent to this synthesizer's receivers
  StringBuilder log;

  /**
   *  Public constructor for the mock synthesizer takes in a string builder object. This object
   *  will be modified by this synthesizer's receivers and the original owner of the instance can
   *  track those changes.
   * @param log StringBuilder instance to be modified and tracked
   */
  public MockSynthesizer(StringBuilder log) {
    this.log = log;
  }

  @Override
  public Receiver getReceiver() throws MidiUnavailableException {
    return new MockReceiver(this.log);
  }

  @Override
  public void close() {
    //does nothing
  }

  @Override
  public int getMaxPolyphony() {
    throw new UnsupportedOperationException("MockSynthesizer does not support this operation.");
  }

  @Override
  public long getLatency() {
    throw new UnsupportedOperationException("MockSynthesizer does not support this operation.");
  }

  @Override
  public MidiChannel[] getChannels() {
    throw new UnsupportedOperationException("MockSynthesizer does not support this operation.");
  }

  @Override
  public VoiceStatus[] getVoiceStatus() {
    throw new UnsupportedOperationException("MockSynthesizer does not support this operation.");
  }

  @Override
  public boolean isSoundbankSupported(Soundbank soundbank) {
    throw new UnsupportedOperationException("MockSynthesizer does not support this operation.");
  }

  @Override
  public boolean loadInstrument(Instrument instrument) {
    return true;
  }

  @Override
  public void unloadInstrument(Instrument instrument) {
    throw new UnsupportedOperationException("MockSynthesizer does not support this operation.");
  }

  @Override
  public boolean remapInstrument(Instrument from, Instrument to) {
    throw new UnsupportedOperationException("MockSynthesizer does not support this operation.");
  }

  @Override
  public Soundbank getDefaultSoundbank() {
    return null;
  }

  @Override
  public Instrument[] getAvailableInstruments() {
    throw new UnsupportedOperationException("MockSynthesizer does not support this operation.");
  }

  @Override
  public Instrument[] getLoadedInstruments() {
    throw new UnsupportedOperationException("MockSynthesizer does not support this operation.");
  }

  @Override
  public boolean loadAllInstruments(Soundbank soundbank) {
    throw new UnsupportedOperationException("MockSynthesizer does not support this operation.");
  }

  @Override
  public void unloadAllInstruments(Soundbank soundbank) {

  }

  @Override
  public boolean loadInstruments(Soundbank soundbank, Patch[] patchList) {
    throw new UnsupportedOperationException("MockSynthesizer does not support this operation.");
  }

  @Override
  public void unloadInstruments(Soundbank soundbank, Patch[] patchList) {

  }

  @Override
  public Info getDeviceInfo() {
    throw new UnsupportedOperationException("MockSynthesizer does not support this operation.");
  }

  @Override
  public void open() throws MidiUnavailableException {
    //do nothing
  }


  @Override
  public boolean isOpen() {
    throw new UnsupportedOperationException("MockSynthesizer does not support this operation.");
  }

  @Override
  public long getMicrosecondPosition() {
    return 0;
  }

  @Override
  public int getMaxReceivers() {
    throw new UnsupportedOperationException("MockSynthesizer does not support this operation.");
  }

  @Override
  public int getMaxTransmitters() {
    throw new UnsupportedOperationException("MockSynthesizer does not support this operation.");
  }

  @Override
  public List<Receiver> getReceivers() {
    throw new UnsupportedOperationException("MockSynthesizer does not support this operation.");
  }

  @Override
  public Transmitter getTransmitter() throws MidiUnavailableException {
    throw new UnsupportedOperationException("MockSynthesizer does not support this operation.");
  }

  @Override
  public List<Transmitter> getTransmitters() {
    throw new UnsupportedOperationException("MockSynthesizer does not support this operation.");
  }
}
