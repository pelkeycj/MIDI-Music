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
import java.io.StringReader;
import java.util.Random;
import javax.sound.midi.InvalidMidiDataException;


public class MusicEditor {
  public static void main(String[] args) throws IOException, InvalidMidiDataException {
    IView textView = new TextView();
    IView guiView = new GuiViewFrame();
    guiView.initialize();
    MusicOperations model = new MIDI();
    IController controller = new SimpleController(model, guiView);

    IView audioView = new MidiViewImpl();

    Random rand = new Random();

    for (int i = 0; i < 50; i++) {
      try {
        int start = rand.nextInt(20);
        model.addNote(OctaveNumber1To10.intToOctave(rand.nextInt(4) + 3), NoteTypeWestern.intToNote(rand.nextInt(12)),
            start, start + rand.nextInt(3));
      } catch (Exception e) {
        //do nothing
      }
    }

    audioView.setNotes(model.getPitches());
    textView.setNotes(model.getPitches());

    for (int i = 0; i < model.getLastBeat(); i++) {
      long startTime = System.nanoTime();
      while (System.nanoTime() - startTime < 1000000000) {
        //wait
      }
      System.out.println("Beat: " + Integer.toString(i));
      audioView.setCurrentBeat(i);
    }

    /*CompositionBuilder<IController> builder = new SheetBuilder(controller);

    Readable rd = new StringReader(args.toString());

    //TODO
    MusicReader.parseFile(rd, builder).go();





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
