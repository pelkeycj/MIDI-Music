OVERVIEW OF CLASSES, INTERFACES, ENUMS:

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


Note (class) :
    Represents an individual note with a start beat and an end beat. Implements methods such as
    toString() to obtain a string representation of the Note (eg "X|||"),compareTo() to
    sort Notes by start time, and equals() to determine note equality based on start and end.


PitchSequence (class) :
    A pitch sequence represents a sequence of notes at a specified Octave and NoteType.
    Implements methods to add and remove individual notes, merge all notes from another pitch
    sequence, get a string representation of the sequence (eg " X-X-- X- X----"), get a string
    represenation of the header (eg "C#5"), and compareTo() and equals() methods to sort and
    determine equality.


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

MIDI (class) :
    Implements the MusicOperations interface and all specified methods.
    Contains an ArrayList pitches of PitchSequences that does not contain any empty
    PitchSequences.


StringUtilities (class) :
    A utilities class with static methods to center, pad left, and pad right
    a given string.
