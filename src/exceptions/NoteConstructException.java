package exceptions;

/**
 * Note对象构造异常
 */
public class NoteConstructException extends MrlException {
    public NoteConstructException() {
        super();
    }

    @Override
    public String toString() {
        return "Invalid note construction.";
    }
}
