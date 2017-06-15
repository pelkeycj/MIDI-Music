package cs3500.music.util;

/**
 * Class to hold constants used in various view classes.
 */
public class ViewConstants {
  public static final int DEFAULT_WIDTH = 1000;
  public static final int BEAT_WIDTH = 20;
  public static final int BEAT_HEIGHT = 14;
  public static final int MEASURE_WIDTH = BEAT_WIDTH * 4;

  public static final int BORDER_WIDTH = 1;

  public static final int BEAT_BORDER_HEIGHT = BEAT_HEIGHT + 2 * BORDER_WIDTH;
  public static final int MEASURE_BORDER_WIDTH = MEASURE_WIDTH + 2 * BORDER_WIDTH;
  public static final int MEASURE_BORDER_HEIGHT = BEAT_BORDER_HEIGHT;

  public static final int SHEET_START_X = 50;
  public static final int SHEET_START_Y = 50;

  public static final int HEADER_FONT_SIZE = BEAT_HEIGHT - 2;

}
