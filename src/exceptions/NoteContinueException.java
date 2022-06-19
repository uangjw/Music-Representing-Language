package exceptions;

public class NoteContinueException extends MrlException{
    public NoteContinueException() {
        super();
    }

    @Override
    public String toString() {
        return "Cannot use continue symbol '.' after rest symbol '%'.";
    }
}
