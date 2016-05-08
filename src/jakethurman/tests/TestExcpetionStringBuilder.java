package jakethurman.tests;

import java.io.PrintWriter;
import java.io.StringWriter;

/* Helper class for printing exceptions in unit tests */
public class TestExcpetionStringBuilder {
	private final Exception exception;
	
	public TestExcpetionStringBuilder(Exception exception) {
		this.exception = exception;
	}
	
	@Override
	public String toString() {
		String msg = "FAILURE\n   ";
		
		//We don't need a stack trace for a TestFailureException so just return out current message and the exception message.
		if (this.exception instanceof TestFailureException)
			return msg + this.exception.getMessage() + "\n";
		
		//Generate the stack trace
		StringWriter sw = new StringWriter();
		this.exception.printStackTrace(new PrintWriter(sw));
		
		return msg + this.exception.getMessage() + "\n\n" + sw.toString();
	}
}
