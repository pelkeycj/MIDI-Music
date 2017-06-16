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

  VIEW:
     As mentioned above, the model supports 3 different types of views. Each of these implements the
     IView interface that allows song data to be passed on the view for rendering and allows the
     current state of the view to be manipulated by changing the current beat.

     IView (interface) :
        Interface that specifies the public methods for a view.
        Methods:
          setNotes - loads note data into the view
          initialize - sets up the view for use
          setCurrentBeat - changes to state of the view such that is set to the given beat
          refresh - signals a view to redraw itself if necessary
          setKeyListener = Provides a keylistener that passes key commands up to the controller
          setTempo - modifies the pace at which the view "plays" its piece of music
          isActive - determines if the view is active and is currently playing a piece of music

     AView (abstract class) :
        Abstract class that implements some of the shared implementations of the IView interface
             and holds shared fields.

     TextView (concrete class) :
        An extension of AView.
        Upon construction of a TextView, the object is given an Appendable object.
        Whenever the view is given a set of notes, it immediately print textual view of those notes
        onto the Appendle object.

     AudioView (concerete class) :
         An extension of AView.
         The AudioView has two simple factory methods that control which type of Synthesizer object
         the object uses: the normal Midi synthesizer or the MockSynthesizer (described below).

         After notes have been loaded into the view by the setNotes method, the state of the
         AudioView can be controlled using setCurrentBeat. Each time this is called, the view checks
         which notes are set to be started at that beat and uses that information to create MIDIData
         objects - a custom private class that contains all the information and methods to sent a
         midimessage request to a Receiver. Each MIDIData object has a "run" method that causes the
         object set a start and stop request to the receiver playing a note at the correct pitch and
         duration on the current beat.

         TESTING: Using the buildSoundView() method provides a client with an AudioView instance
         that will play music using a midi reciever provided by the Midi Synthesizer.
         The buildTestView() method takes in an Appendable object. This instance is the exact same
         as the "normal" instance with the exception that the Synthesizer and Receiver in this test
         view are custom-made Mock objects that use the appendable object passed to factory method
         to the given log of the messages sent to the Receiver effectively capturing all the calls
         that the view makes to its Receiver object.

         MockSynthesizer (concrete class that implements Synthesizer) :
            This mock synthesizer class is used for testing the calls the the AudiView
            makes to a receiver. All synthesizer methods used by the AudioView class have some kind
            of mock implementation and those that are not used throw an
            UnsupportedOperationException.
            Most imporantly, this object takes in an Appendable object and passes it along to a
            MockReceiver object that is given to the client through the getReceiver() method. This
            allows a client to hand the Synthesizer a referecne to the Appendable object the are
            using as a log and all changes added to this log by the receivers from this
            MockSynthesier object will be made to the object held by the client so they can see the
            changes.

         MockReceiver (concrete class that implements Receiver) :
            This mock reciever collects midimessages set to it via the send() method and decomposes
            these messages into their different parts. This message is then pieced back together
            in a more human-readable format and added to an Appendable object that is assigned to
            the instance at the time of its creation.
            The effect of this, is that the object can interact with the AudioView model exactly
            as a standard midireceiver would and capture those messages that are sent the receivers
            by the view.
            This allows the AudioView to be tested and ensure that it makes all the calls to the
            reciever that is it supposed to make.


     GuiView (concrete class) :
         An extension of AView.
         The GuiView has two separate panels, a PianoPanel that shows on a simulated paino keyboard
         which notes are currently being played and a SheetPanel that diplays the notes of a piece
         of music and scrolls through the sheet as the music plays. Each of these panels hold the
         the logic for how they are supposed to look and react on each note, the GuiViewFrame
         almost acts like a controller for the two panes. It positions them in their respective
         positions and update them with new information as the state of the view is modified.

          PianoPanel (concrete class) :
              The piano panel holds a set of 120 PianoKey objects each of which represent a
              different note. The information for each piano key is stored in a private PianoKey
              class that serves to track what note the key represents and information about thow the
              key should be drawn.
              A generatePianoKeys method builds a full set of keys to be used by the controller.
              The state of the PianoPanel is mostly modified by switching different keys as being
              "pressed" or "unpressed". The panel receives this information from the view in the
              form of a set of currently playing notes. The panel can then modify the colors of
              the keys that represents these pitches which allows them to be displayed as playing.

          SheetPanel (concrete class) :
              The sheet panel contains a visual representation of the notes of a given piece of
              music. The panel a grid-like music sheet with a row for each note that is used in the
              piece of music and a column for each measure (4 beats) in the piece of music. The
              notes in the piece are drawn on this grid with a black box indicating the start of a
              new note and a green box indicating that this beat is a continuation of a note that
              started earlier.
              The panel is scrollable so if there are too many notes to display in the current
              size of the window, one can scroll up and down to see more of the rest of the notes.
              Using a SPACEBAR and LEFT and RIGHT arrow keys, a user can navigate through the piece
              of music. As they move through the music, a RED bar indicated their current position.
              The bar begins on the lefthand side of the music sheet and advances right until it
              reaches the middle of the panel. At this point, it remains in the middle of the sheet
              and the music sheet of notes "scrolls" behind it. This implementation was chosen so
              that users could clearly see which notes were upcoming in the piece which makes
              naviation through the piece feel more natural.



