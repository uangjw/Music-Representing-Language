package exceptions;

public class NoteConstructException extends MrlException {
    public NoteConstructException() {
        super();
    }

    @Override
    public String toString() {
        return "Invalid note construction.";
    }
}
