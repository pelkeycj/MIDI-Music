package cs3500.music;

import cs3500.music.model.MIDI;
import cs3500.music.model.MusicOperations;
import cs3500.music.model.NoteTypeWestern;
import cs3500.music.model.OctaveNumber1To10;
import cs3500.music.view.GuiViewFrame;
import cs3500.music.view.IView;
import cs3500.music.view.MidiViewImpl;

import cs3500.music.view.TextView;
import java.io.IOException;
import javax.sound.midi.InvalidMidiDataException;


public class MusicEditor {
  public static void main(String[] args) throws IOException, InvalidMidiDataException {
    IView textView = new TextView();
    IView guiView = new GuiViewFrame();
    guiView.initialize();
    MusicOperations model = new MIDI();
    model.addNote(OctaveNumber1To10.O1, NoteTypeWestern.C, 0, 2);
    guiView.setNotes(model.getPitches());
    guiView.setCurrentBeat(1);
    //TODO read input and create notes in model.
    //TODO connect views, models in controller and launch

   // MidiViewImpl midiView = new MidiViewImpl();
    // You probably need to connect these views to your model, too...
  }
}
