package exceptions;

/**
 * 无效标识符异常
 */
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