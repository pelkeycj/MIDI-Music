package cs3500.music;

import cs3500.music.control.IController;
import cs3500.music.control.SimpleController;
import cs3500.music.model.MIDI;
import cs3500.music.model.MusicOperations;
import cs3500.music.model.NoteTypeWestern;
import cs3500.music.model.Octave;
import cs3500.music.model.OctaveNumber0To10;
import cs3500.music.model.OctaveNumber1To10;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MusicReader;
import cs3500.music.util.SheetBuilder;
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
    IController controller = new SimpleController(model, guiView);

    CompositionBuilder<IController> builder = new SheetBuilder(controller);
    //TODO use music reader to parse file

    Scanner scanner = new Scanner(System.in);

    Random rand = new Random();

    model.addNote(OctaveNumber1To10.O1, NoteTypeWestern.A_SHARP,0,10);

    for (int i = 0; i < 20; i++) {
      Octave o = OctaveNumber0To10.intToOctave(rand.nextInt(10) );
      NoteTypeWestern n = NoteTypeWestern.intToNote(rand.nextInt(12));
      try {
        int start = rand.nextInt(50);
        controller.addNote(o, n, start, start + rand.nextInt(2) + 1);
      } catch (Exception e) {
        //ignore bad note
      }
    }

    controller.go();

    /**
    guiView.setNotes(model.getPitches());

    int curr = 0;
    while (true) {

      guiView.setCurrentBeat(curr);
      guiView.refresh();
      curr++;
    }

     */

  }
}
