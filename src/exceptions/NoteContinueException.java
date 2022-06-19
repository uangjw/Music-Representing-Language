package exceptions;

/**
 * 试图延续一个休止符时，抛出此异常
 */
public class NoteContinueException extends MrlException{
    public NoteContinueException() {
        super();
    }

    @Override
    public String toString() {
        return "Cannot use continue symbol '.' after rest symbol '%'.";
    }
}
