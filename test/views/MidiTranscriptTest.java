package views;

import cs3500.music.control.IController;
import cs3500.music.control.SimpleController;
import cs3500.music.model.MusicOperations;
import cs3500.music.model.MusicSheet;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MusicReader;
import cs3500.music.util.SheetBuilder;
import cs3500.music.view.AudioView;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;

/**
 * Used to create midi transcripts of music files using the mock receiver to collect the
 * output from the audio view.
 */
public class MidiTranscriptTest {
  public static void main(String... args) {
    StringBuilder log = new StringBuilder();
    MusicOperations model = new MusicSheet();
    IController controller = new SimpleController(model, true, AudioView.buildTestView(log));

    String filename = "res/mary-little-lamb.txt";

    CompositionBuilder<IController> builder = new SheetBuilder(controller);
    try {
      MusicReader.parseFile(new FileReader(filename), builder).go();
    } catch (FileNotFoundException e) {
      throw new RuntimeException("File failed to open");
    }

    try(  PrintWriter out = new PrintWriter( "midi-transcript.txt" )  ){
      out.println( log.toString() );
    } catch (FileNotFoundException e) {
      throw new RuntimeException("File failed to open");
    }
  }
}
