package exceptions;

public class MrlException extends Exception {
    protected int line = -1;
	protected int column = -1;

	public MrlException(int line, int column) {
		this.line = line;
		this.column = column;
	}

	public MrlException() {
	}

	@Override
	public String toString() {
		if (line == -1 && column == -1)
			return "Mrl language error";
		else
			return "Mrl language error at " + line + ", " + column;
	}
}
