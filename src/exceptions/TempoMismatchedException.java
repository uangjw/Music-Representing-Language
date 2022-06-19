package exceptions;

/**
 * 全局节奏设置与某小节节拍数不符合时，抛出此异常
 */
public class TempoMismatchedException extends MrlException{
    public TempoMismatchedException() {
        super();
    }

    @Override
    public String toString() {
        return "Section parts cannot match with TEMPO configuration.";
    }
}
