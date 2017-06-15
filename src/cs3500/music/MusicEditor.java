package cs3500.music;

import java.io.FileReader;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import cs3500.music.control.IController;
import cs3500.music.control.SimpleController;
import cs3500.music.model.MusicSheet;
import cs3500.music.model.MusicOperations;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MusicReader;
import cs3500.music.util.SheetBuilder;
import cs3500.music.view.GuiViewFrame;
import cs3500.music.view.MidiViewImpl;
import cs3500.music.view.TextView;

public class MusicEditor {


  public static void main(String[] args) throws IOException, InvalidMidiDataException {
    MusicOperations model = new MusicSheet(); // model to use
    IController controller;

    // get view
    switch (args[0].toLowerCase()) {
      case "console":
        controller = new SimpleController(model, new TextView());
        break;
      case "gui":
        controller = new SimpleController(model, new GuiViewFrame());
        break;
      case "midi":
        controller = new SimpleController(model, new GuiViewFrame(), new MidiViewImpl());
        break;
      default:
        throw new IllegalArgumentException("Unsupported view: " + args[0]);
    }

    String fileName = "res/" + args[1];

    CompositionBuilder<IController> builder = new SheetBuilder(controller);
    MusicReader.parseFile(new FileReader(fileName), builder).go();
  }
}
