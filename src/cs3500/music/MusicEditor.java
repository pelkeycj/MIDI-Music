package cs3500.music;

import cs3500.music.view.IView;
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
import cs3500.music.view.AudioVisualView;
import cs3500.music.view.GuiViewFrame;
import cs3500.music.view.AudioView;
import cs3500.music.view.TextView;
import javax.sound.midi.MidiUnavailableException;

public class MusicEditor {

  /**
   * Main entry point for the Music Editor program.
   * Runs the program with the view and music piece provided as arguments.
   * @param args the view, music piece
   * @throws IOException if file cannot be read
   * @throws InvalidMidiDataException if the midi cannot be accesssed
   */
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
        try {
          controller = new SimpleController(model, true, new AudioView());
        } catch (MidiUnavailableException e) {
          throw new RuntimeException("Audio Unavailable.");
        }
        break;
      case "audiovisual":
        try {
          controller = new SimpleController(model, new AudioVisualView(new GuiViewFrame(),
              new AudioView()));
        } catch (MidiUnavailableException e) {
          throw new RuntimeException("Audio Unavailable.");
        }
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
    MusicReader.parseFile(file, builder).control();
  }
}
