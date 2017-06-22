package cs3500.music.tests;

import cs3500.music.control.IController;
import cs3500.music.control.MockSimpleController;
import cs3500.music.model.MusicOperations;
import cs3500.music.model.MusicSheet;
import cs3500.music.view.GuiViewFrame;
import cs3500.music.view.IView;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import cs3500.music.control.MockMouseEvent;
import cs3500.music.control.MockMouseHandler;
import cs3500.music.control.MouseEventProcessor;
import cs3500.music.control.MouseHandler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

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
    mouseEvents.get(MouseEvent.MOUSE_CLICKED).process(new MockMouseEvent());
    assertEquals("Add note.\n", out.toString());
  }

  @Test
  public void testInvalidMouseEventDoesNothing() {
    assertFalse(mouseEvents.containsKey(MouseEvent.MOUSE_DRAGGED));
  }

  @Test
  public void testMousePitchAcquisitionNull() {
    MusicOperations model = new MusicSheet();
    IView guiView = new GuiViewFrame();
    MockSimpleController controller = new MockSimpleController(out, model, guiView);

    // place mouse click at 26,5
    MouseEvent mouse = new MouseEvent((Component) guiView, MouseEvent.MOUSE_CLICKED,0, 0,
            26, 0, 0, false);
    mouseEvents = controller.getMouseStrategy().getMouseEvents();
    mouseEvents.get(MouseEvent.MOUSE_CLICKED).process(mouse);

    assertEquals("", out.toString());
  }

  @Test
  public void testMousePitchAcquisitionC0() {
    MusicOperations model = new MusicSheet();
    IView guiView = new GuiViewFrame();
    MockSimpleController controller = new MockSimpleController(out, model, guiView);

    // place mouse click at 26,5
    MouseEvent mouse = new MouseEvent((Component) guiView, MouseEvent.MOUSE_CLICKED,0, 0,
            26, 255, 0, false);
    mouseEvents = controller.getMouseStrategy().getMouseEvents();
    mouseEvents.get(MouseEvent.MOUSE_CLICKED).process(mouse);

    assertEquals("Found pitch: C0", out.toString());
  }
}