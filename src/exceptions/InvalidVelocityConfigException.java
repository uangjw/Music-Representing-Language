package exceptions;

public class InvalidVelocityConfigException extends MrlException {
    public InvalidVelocityConfigException() {
        super();
    }

    @Override
    public String toString() {
        return "Invalid velocity configuration.";
    }
}
