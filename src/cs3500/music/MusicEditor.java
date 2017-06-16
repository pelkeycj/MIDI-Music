package cs3500.music;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;

import cs3500.music.control.IController;
import cs3500.music.control.SimpleController;
import cs3500.music.model.MusicSheet;
import cs3500.music.model.MusicOperations;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MusicReader;
import cs3500.music.util.SheetBuilder;
import cs3500.music.view.GuiViewFrame;
import cs3500.music.view.AudioView;
import cs3500.music.view.TextView;

public class MusicEditor {

  public static void main(String[] args) throws IOException, InvalidMidiDataException {
    MusicOperations model = new MusicSheet();
    IController controller;

    // get view
    switch (args[0].toLowerCase()) {
      case "console":
        controller = new SimpleController(model, new TextView(System.out));
        break;
      case "visual":
        controller = new SimpleController(model, new GuiViewFrame());
        break;
      case "audio":
        // play and terminate at last beat
        controller = new SimpleController(model, true, AudioView.buildSoundView());
        break;
      case "audiovisual":
        controller = new SimpleController(model, new GuiViewFrame(), AudioView.buildSoundView());
        break;
      default:
        throw new IllegalArgumentException("Unsupported view: " + args[0]);
    }

    String fileName = args[1];
    FileReader file;
    try {
      file = new FileReader(fileName);
    }
    catch (FileNotFoundException e) {
      e.printStackTrace();
      return;
    }

    CompositionBuilder<IController> builder = new SheetBuilder(controller);
    MusicReader.parseFile(file, builder).go();
  }
}
