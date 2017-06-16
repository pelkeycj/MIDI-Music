CONTENTS:
    1. USAGE
    2. DESIGN


1. USAGE
    To run the MIDIMusic jar:
        run java -jar MIDIMusic.jar [view] [song]

    Valid view arguments:
        1. console - a text rendering of the sheet of music to the console.

        2. visual - an interactive visual view of the sheet with scrolling and
                    a piano panel that highlights current notes being played.
                    Use keys LEFT and RIGHT to move the cursor left and right respectively.
                    Use key SPACEBAR to toggle visually "play" the sheet of music.

        3. audio - plays the song without any GUI. The program quits after the song is finished.

        4. audiovisual - combination of the audio and visual options. Plays notes when the cursor
                         reaches them and does not end the program until the window is closed.
                         Use keys LEFT and RIGHT to move the cursor left and right respectively.
                         Use key SPACEBAR to toggle playing the song.

    Valid song arguments:
        1. df-ttfaf.txt     NOTE: visual, audiovisual views may not handle the
                                    size of this song, however audio will.
        2. lnl.txt
        3. mystery-1.txt
        4. mystery-2.txt
        5. mystery-3.txt
        6. zoot-lw.txt
        7. zoot-zl.txt

2. DESIGN
    //TODO

