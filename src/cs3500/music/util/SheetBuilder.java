package cs3500.music.util;

import cs3500.music.control.IController;
import cs3500.music.model.NoteTypeWestern;
import cs3500.music.model.OctaveNumber0To10;

/**
 * Builds a sheet of music by adding notes and setting the tempo. These modifications are passed
 * onto an IController object passed in by the client. Any made to this IController will be passed
 * on to and handled by the views(s) and model with which it was constructed.
 */
public class SheetBuilder implements CompositionBuilder<IController> {
  IController controller;

  /**
   * Public constructor for a SheetBuilder object. Sets the controller field to be the contoller
   * passed in by the client.
   * @param controller the controller passed in by the that will be modified by the SheetBuilder's
   *     methods.
   */
  public SheetBuilder(IController controller) {
    this.controller = controller;
  }

  @Override
  public IController build() {
    return this.controller;
  }

  @Override
  public CompositionBuilder setTempo(int tempo) {
    controller.setTempo(tempo);
    return this;
  }

  @Override
  public CompositionBuilder addNote(int start, int end, int instrument, int pitch, int volume) {
    int octaveNum = (pitch / 12);
    int noteNum = pitch % 12;

    this.controller.addNote(OctaveNumber0To10.intToOctave(octaveNum),
            NoteTypeWestern.intToNote(noteNum), start, end, instrument, volume);
    return this;
  }
}
