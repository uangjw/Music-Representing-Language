package exceptions;

/**
 * 无效演奏强度设置异常
 */
public class InvalidVelocityConfigException extends MrlException {
    public InvalidVelocityConfigException() {
        super();
    }

    @Override
    public String toString() {
        return "Invalid velocity configuration.";
    }
}
