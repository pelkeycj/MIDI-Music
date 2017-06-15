package cs3500.music;

import cs3500.music.control.IController;
import cs3500.music.control.SimpleController;
import cs3500.music.model.MusicSheet;
import cs3500.music.model.MusicOperations;
import cs3500.music.model.NoteTypeWestern;
import cs3500.music.model.OctaveNumber0To10;
import cs3500.music.model.OctaveNumber1To10;
import cs3500.music.view.GuiViewFrame;
import cs3500.music.view.IView;
import cs3500.music.view.MidiViewImpl;

import cs3500.music.view.TextView;
import java.io.IOException;
import java.util.Random;
import javax.sound.midi.InvalidMidiDataException;


public class MusicEditor {
  public static void main(String[] args) throws IOException, InvalidMidiDataException {
    IView textView = new TextView();
    IView guiView = new GuiViewFrame();
    guiView.initialize();
    MusicOperations model = new MusicSheet();


    int tempo = 120;
    int microSecondsPerBeat = 60 * ((1000000) / tempo);
    IView audioView = new MidiViewImpl(microSecondsPerBeat);

    Random rand = new Random();

    for (int i = 0; i < 10; i++) {
      try {
        int start = rand.nextInt(5);
        model.addNote(OctaveNumber0To10.intToOctave(rand.nextInt(4) + 4), NoteTypeWestern.intToNote(rand.nextInt(12)),
            start, start + rand.nextInt(4), 1, 127);
      } catch (Exception e) {
        //do nothing
      }
    }


    IController controller = new SimpleController(model, audioView, guiView);
    controller.setViewNotes();
    controller.setTempo(microSecondsPerBeat);
    controller.go();



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
