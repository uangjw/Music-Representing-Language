package exceptions;

/**
 * 访问特殊类型Note对象的音符信息时，抛出此异常
 */
public class NoteInfoException extends MrlException{
    public NoteInfoException() {
        super();
    }

    @Override
    public String toString() {
        return "Invalid information request for notes of type rest/cont/null.";
    }
}
