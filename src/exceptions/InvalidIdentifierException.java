package exceptions;

public class InvalidIdentifierException extends MrlException {
    String id;

    public InvalidIdentifierException() {
        super();
    }

    public InvalidIdentifierException(String x) {
        id = x;
    }

    @Override
    public String toString() {
        return "Invalid identifier: " + id + ".";
    }
}