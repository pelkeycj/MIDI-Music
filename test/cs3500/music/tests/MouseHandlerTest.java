package cs3500.music.tests;

import org.junit.Before;
import org.junit.Test;

import java.awt.event.MouseEvent;
import java.util.Map;

import cs3500.music.control.MockMouseHandler;
import cs3500.music.control.MouseEventProcessor;
import cs3500.music.control.MouseHandler;

import static org.junit.Assert.*;

/**
 * Tests for the {@link MouseHandler} class.
 */
public class MouseHandlerTest {
  StringBuilder out;
  Map<Integer, MouseEventProcessor> mouseEvents;

  @Before
  /**
   * Reset and initialize fields.
   */
  public void init() {
    out = new StringBuilder();
    mouseEvents = MockMouseHandler.getMouseEvents(out);
  }

  @Test
  public void testMouseClickAddsNote() {
    //TODO
  }

  @Test
  public void testInvalidMouseEventDoesNothing() {
    //TODO
  }

}