package cs3500.music.util;

/**
 * Utility class to contain methods to modify {@code String}s.
 */
public class StringUtilities {

  /**
   * Centers string {@code s} with final width of {@code size}.
   *
   * @param s    the string to center
   * @param size the final length of the string
   * @return the centered string
   * @throws IllegalArgumentException if the size is less than the length of s
   */
  public static String center(String s, int size) throws IllegalArgumentException {
    if (size < s.length()) {
      throw new IllegalArgumentException("Size must be >= length of string");
    }
    int leftPad = (int) Math.floor((size - s.length()) / 2);
    int rightPad = size - (leftPad + s.length());

    return padLeft(padRight(s, rightPad), leftPad);
  }

  /**
   * Pad {@code s} by {@code numPads} spaces on the left.
   *
   * @param s       the string to left pad
   * @param numPads the number of spaces to pad with
   * @return the left padded string
   */
  public static String padLeft(String s, int numPads) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < numPads; i++) {
      sb.append(" ");
    }
    sb.append(s);
    return sb.toString();
  }


  /**
   * Pad {@code s} by {@code numPads} spaces on the right.
   * @param s the string to right pad
   * @param numPads the number of spaces to pad with
   * @return the right padded string
   */
  public static String padRight(String s, int numPads) {
    StringBuilder sb = new StringBuilder();
    sb.append(s);
    for (int i = 0; i < numPads; i++) {
      sb.append(" ");
    }
    return sb.toString();
  }

}
