package jakethurman.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.function.Consumer;
import jakethurman.foundation.Disposable;

public class FileHandler implements Disposable {
	private static final String UTF8 = "UTF-8";
	private static final String ERROR_OCCURED = "------ AN ERROR OCCURED ------";
	
	private final Consumer<Exception> errorHandler;
	
	public FileHandler(Consumer<Exception> errorHandler) {
		this.errorHandler = errorHandler;
	}
		
	public void overwriteFile(Path file, Iterable<String> lines) {
		ExceptionHelpers.tryRun(() -> Files.write(file, lines, Charset.forName(UTF8)), errorHandler);
	}

	public void appendToFile(Path file, Iterable<String> lines) {
		ExceptionHelpers.tryRun(() -> Files.write(file, lines, Charset.forName(UTF8), StandardOpenOption.APPEND), errorHandler);
	}
	
	public void appendToFile(Path file, String line) {
		appendToFile(file, Arrays.asList(line));
	}
	
	public String readFile(File f) throws IOException {
		return ExceptionHelpers.tryGet(() -> {
			try(FileInputStream fis = new FileInputStream(f)) {
				byte[] data = new byte[(int) f.length()];
				fis.read(data);
				fis.close();
				
				return new String(data, UTF8);
			}
		}, (e) -> { 
			errorHandler.accept(e); 
			return ERROR_OCCURED; 
		});
	}
	
	@Override
	public void dispose() {
		// Nothing to dispose
	}
}
