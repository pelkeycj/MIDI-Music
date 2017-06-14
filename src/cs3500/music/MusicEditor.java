package cs3500.music;

import cs3500.music.model.MIDI;
import cs3500.music.model.MusicOperations;
import cs3500.music.model.NoteTypeWestern;
import cs3500.music.model.Octave;
import cs3500.music.model.OctaveNumber1To10;
import cs3500.music.view.GuiViewFrame;
import cs3500.music.view.IView;
import cs3500.music.view.MidiViewImpl;

import cs3500.music.view.TextView;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import javax.sound.midi.InvalidMidiDataException;


public class MusicEditor {
  public static void main(String[] args) throws IOException, InvalidMidiDataException {
    IView textView = new TextView();
    IView guiView = new GuiViewFrame();
    guiView.initialize();
    MusicOperations model = new MIDI();

    Scanner scanner = new Scanner(System.in);

    Random rand = new Random();

    for (int i = 0; i < 100; i++) {
      Octave o = OctaveNumber1To10.intToOctave(rand.nextInt(10) + 1);
      NoteTypeWestern n = NoteTypeWestern.intToNote(rand.nextInt(12));
      try {
        int start = rand.nextInt(50);
        model.addNote(o, n, start, start + rand.nextInt(2) + 1);
      } catch (Exception e) {
        //ignore bad note
      }
    }

    guiView.setNotes(model.getPitches());

    int curr = 0;
    while (true) {
      scanner.nextLine();
      guiView.setCurrentBeat(curr);
      guiView.refresh();
      curr++;
    }
    //TODO read input and create notes in model.
    //TODO connect views, models in controller and launch

   // MidiViewImpl midiView = new MidiViewImpl();
    // You probably need to connect these views to your model, too...
  }
}
