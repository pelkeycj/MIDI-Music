package cs3500.music;

import java.io.FileReader;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import cs3500.music.control.IController;
import cs3500.music.control.SimpleController;
import cs3500.music.model.MIDI;
import cs3500.music.model.MusicOperations;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MusicReader;
import cs3500.music.util.SheetBuilder;
import cs3500.music.view.GuiViewFrame;
import cs3500.music.view.IView;
import cs3500.music.view.MidiViewImpl;
import cs3500.music.view.TextView;

public class MusicEditor {


  public static void main(String[] args) throws IOException, InvalidMidiDataException {
    IView view; // the view to be used (determined through CLI args)
    MusicOperations model = new MIDI(); // model to use

    // get view
    switch (args[0].toLowerCase()) {
      case "console":
        view = new TextView();
        break;
      case "gui":
        view = new GuiViewFrame();
        break;
      case "midi":
        try {
          view = new MidiViewImpl();
        }
        catch (MidiUnavailableException e) {
          e.printStackTrace();
          return;
        }
        break;
      default:
        throw new IllegalArgumentException("Unsupported view: " + args[0]);
    }

    IController controller = new SimpleController(model, view);

    System.out.println(System.getProperty("user.dir"));
    String fileName = "res/" + args[1];

    CompositionBuilder<IController> builder = new SheetBuilder(controller);
    MusicReader.parseFile(new FileReader(fileName), builder).go();
  }
}
