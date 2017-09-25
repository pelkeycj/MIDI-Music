# MIDI-Music
An interactive sheet music player and editor.


Usage
------
To run the MIDIMusic jar file:
`java -jar MIDIMusic.jar [view] [song]`

Valid [view] arguments:
- console: a text rendering of the sheet of music to the console.
- visual: an interactive visual view of the sheet with scrolling and a piano
  panel that highlights current notes being played. Use keys `LEFT` and `RIGHT`
  to move the cursor left and right respectively. Use key `SPACEBAR` to toggle
  visually playing the music.
- audio: plays the song without any GUI. The program quits after the song is
  finished.
- audiovisual: combination of audio and visual options. Plays notes when the
  cursor reaches them.

Valid [song] arguments:
- df-ttfaf.txt
- lnl.txt
- mystery-1.txt
- mystery-2.txt
- mystery-3.txt
- zoot.lw.txt
- zoot.zl.txt
- mary-little-lamb.txt
- mary-little-lamb-repeats.txt


**NOTE:** visual, audiovisual views may not handle the size of df-ttfaf.txt, however audio will.
