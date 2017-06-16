CONTENTS:
    1. USAGE
    2. DESIGN
---------------------------------------------------------------------------------------------------

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
        1. df-ttfaf.txt
        2. lnl.txt
        3. mystery-1.txt
        4. mystery-2.txt
        5. mystery-3.txt
        6. zoot-lw.txt
        7. zoot-zl.txt

NOTE: visual, audiovisual views may not handle the size of df-ttfaf.txt, however audio will.

---------------------------------------------------------------------------------------------------

2. DESIGN

  MODEL: // all package-private methods/classes were changed to public in assignment 6
      NoteType (interface):
          An interface to specify notes that can be used
          in a music player. This is to allow different systems of
          notes to be used (eg. western vs eastern).
          Methods specified:
              -- toString() = string representation of the note.
              -- getValue() = integer value to represent the ordering of each note type.

      NoteTypeWestern (enum) :
          Implements the NoteType interface and specifies
          the 13 notes C through B used in the western style of music.


      Octave (interface) :
          An interface to specify an octave range to be used in a music player.
          This allows different ranges of octaves to be allowed.
          Methods specified:
              -- toString() = string representation of the octave number.
              -- getValue() = integer representation of the octave number.


      OctaveNumber1To10 (enum) :
          Implements the Octave interface and specifies an octave range from 1-10 to be used
          in a music player.

      OctaveNumber0To10 (enum) : //ADDED IN ASSIGNMENT 6
          Implements the Octave interface and specifies an octave range from 0-10 to be used in
          a music player. This was added to accomodate the ranges used in Midi.


      Note (class) :
          Represents an individual note with a start beat and an end beat. Implements methods such
          as toString() to obtain a string representation of the Note (eg "X|||"), compareTo() to
          sort Notes by start time, and equals() to determine note equality based on start and end.
          End beats are exclusive and must not be less than or equal to start beats.

          //CHANGES FROM ASSIGNMENT 6:
            Notes can be constructed with a loudness and instrument as arguments.


     Pitch (class) : //ADDED IN ASSIGNMENT 6
        Class used to represent a pitch as both a note type and an octave.
        Contains a method getValue() to obtain the value of the pitch
        to be used for playing through Midi.


     PitchSequence (class) :
        A pitch sequence represents a sequence of notes at a specified Octave and NoteType.
        Implements methods to add and remove individual notes, merge all notes from another pitch
        sequence, get a string representation of the sequence (eg " X-X-- X- X----"), get a string
        represenation of the header (eg "C#5"), and compareTo() and equals() methods to sort and
        determine equality.

        //CHANGES FROM ASSIGNMENT 6:
        Methods noteAt(int beat) and playingAt(int beat) were added to obtain a note at
        a given beat and determine if a note is playing at a given beat, respectively.
        A method getPitchCopy() was added to obtain an instance of Pitch representing the
        note type and octave.


     MusicOperations (interface) :
        Interface to specify the required methods for the model of a music player.
            Methods specified:
                -- addNote
                -- removeNote
                -- changeNote
                -- mergeSheet    = to merge another sheet of notes from a MusicOperations
                -- appendSheet   = to append another sheet of notes from a MusicOperations
                -- getSheet      = to get a string representation of the sheet of notes
                -- getPitches    = to get the list of pitch sequences
                -- consoleRender = to display the string representation of the sheet to the console

        //CHANGES FROM ASSIGNMENT 6:
        addNote method was overloaded to allow for notes with instrument and loudness information.

      MusicSheet (class) :
          Represents a sheet of music notes.
          Implements the MusicOperations interface and all specified methods.
          Contains an ArrayList pitches of PitchSequences that does not contain any empty
          PitchSequences.


  CONTROL:
    IController (interface) :
        Interface to specify methods used for controllers.
        Methods:
            setViewNotes
            setTempo
            addNote (overloaded to allow optional instrument and loudness fields)
            go

    SimpleController (class) :
        Implements IController interface and KeyListener.
        Accepts a MusicOperations model and one or more IViews to control.
        Listens for keyboard input from the views and updates the current beat accordingly.




    UTIL:
        CompositionBuilder (interface) :
            Starter code provided to add notes, set tempo, and build
            a composition (unmodified)

        MusicReader (class) :
            Started code provided to parse a text file containing
            composition information such as tempo and notes.
            Modified only to include a try-catch block to prevent illegal notes
            from being added (eg. notes with same start and end).

        SheetBuilder (class) :
            Implements CompositionBuilder interface.
            Accepts an IController as an argument to its constructor
            and add notes to the model through the IController.

        StringUtilities (class) :
            Class that includes methods to format strings such
            as center(), padLeft(), and padRight().

        ViewConstants (class) :
            Contains static, final constants to be used globally throughout
            multiple view classes. This is to prevent multiple classes from
            containing identical constants that may end up being out of sync.



    MAIN:
        MusicEditor (class) :
            Main entry point for the program. Accepts arguments that
            determine what view to use and what song to play.
            Uses the MusicReader to parse the specified input file and
            launch a controller with the specified view.
