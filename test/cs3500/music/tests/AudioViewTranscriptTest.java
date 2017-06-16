package cs3500.music.tests;

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
 * output from the audio view. Note that this runs Mary Had a Little Lamb exactly as it would if it
 * were really playing the song to a "real" receiver so this creating this test will take some time
 * for it to play through the entire song.
 */
public class AudioViewTranscriptTest {

  /**
   * This main method runs the test that builds the transcript for Mary Had A Little Lamb.
   * @param args no input parameters required.
   */
  public static void main(String... args) {
    StringBuilder log = new StringBuilder();
    MusicOperations model = new MusicSheet();
    IController controller = new SimpleController(model, true, AudioView.buildTestView(log));

    String filename = "res/mary-little-lamb.txt";

    CompositionBuilder<IController> builder = new SheetBuilder(controller);
    try {
      MusicReader.parseFile(new FileReader(filename), builder).control();
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
