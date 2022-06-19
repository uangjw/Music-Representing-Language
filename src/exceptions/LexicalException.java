package exceptions;

/**
 * 词法分析异常
 */
public class LexicalException extends MrlException{
    public LexicalException(int line, int column, String str) {
		super(line, column);
		System.out.println("Lexical error matching " + str);
	}
    public LexicalException() {
		super();
	}

	@Override
	public String toString() {
		if (line == -1 && column == -1)
			return "Mrl lexical error";
		else
			return "Mrl lexical error at " + line + ", " + column;
	}

}
