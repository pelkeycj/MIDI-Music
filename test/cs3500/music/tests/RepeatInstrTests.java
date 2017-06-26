package cs3500.music.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import cs3500.music.model.RepeatInstr;
import org.junit.Test;

/**
 * Tests of the repeat instructions class.
 */
public class RepeatInstrTests {

  @Test
  public void badInputsTest() {
    int[] inputs = {1, 0, -1, 0, -100, -10, -3, 4};
    for (int i = 0; i < inputs.length; i += 2) {
      String exception = "";
      try {
        RepeatInstr r = new RepeatInstr(inputs[i], inputs[i + 1]);
      } catch (IllegalArgumentException e) {
        exception = e.getMessage();
      }
      assertNotEquals("", exception);
    }
  }

  @Test
  public void completenessTest() {
    RepeatInstr r = new RepeatInstr(0, 3);
    assertFalse(r.isCompleted());
    r.complete();
    assertTrue(r.isCompleted());
    r.uncomplete();
    assertFalse(r.isCompleted());
  }

  @Test
  public void inputsAndCopy() {
    RepeatInstr r1 = new RepeatInstr(0, 3);
    RepeatInstr r2 = new RepeatInstr(0, 4);

    assertEquals(r1.firstBeat(), r2.firstBeat());
    assertNotEquals(r1.lastBeat(), r2.lastBeat());

    assertTrue(r1.equals(r1.copy()));
    assertFalse(r1 == r1.copy());
  }
}
