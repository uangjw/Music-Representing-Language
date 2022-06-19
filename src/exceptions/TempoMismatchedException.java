package exceptions;

public class TempoMismatchedException extends MrlException{
    public TempoMismatchedException() {
        super();
    }

    @Override
    public String toString() {
        return "Section parts cannot match with TEMPO configuration.";
    }
}
