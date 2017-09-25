# MIDI-Music
An interactive sheet music player and editor.

![GIF of MIDI program](https://github.com/pelkeycj/MIDI-Music/blob/master/res/sample.gif)

Usage
------
To run the MIDIMusic jar file:
`java -jar MIDIMusic.jar [view] [song]`

Valid [view] arguments:
- console: a text rendering of the sheet of music to the console.
- visual: an interactive visual view of the sheet with scrolling and a piano
  panel that highlights current notes being played. 
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
- zoot-lw.txt
- zoot-zl.txt
- mary-little-lamb.txt
- mary-little-lamb-repeats.txt

Key commands:
- `LEFT`: move cursor left
- `RIGHT`: move cursor right
- `SPACEBAR`: toggle automatic playing of music
- `ENTER`: jump to end of sheet
- `BACKSPACE`: jump to beginning of sheet
- `A/D`: decrease/increase tempo by %10
- `P`: toggle 'practice mode', where the user must click on the highlighted keys
       in order to advance the cursor
       
Mouse commands:
- Clicking and holding on a piano key adds a note corresponding to the chosen pitch.

**NOTE:** visual, audiovisual views may not handle the size of df-ttfaf.txt, however audio will.
