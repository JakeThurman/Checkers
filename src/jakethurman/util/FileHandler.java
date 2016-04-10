package jakethurman.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.function.Consumer;
import jakethurman.foundation.Disposable;

public class FileHandler implements Disposable {
	private static final String UTF8 = "UTF-8";	
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
	
	public void appendToFileOrCreate(Path file, Iterable<String> lines) {
		ExceptionHelpers.tryRun(() -> Files.write(file, lines, Charset.forName(UTF8), StandardOpenOption.APPEND), 
								(e) -> overwriteFile(file, lines));
	}
	
	public void readFile(File f, Runnable onFileNotFound, Consumer<String> handleLine) {
		ExceptionHelpers.tryRun(() -> {
			try (BufferedReader br = new BufferedReader(new FileReader(f))) {
			    String line;
			    while ((line = br.readLine()) != null) {
			    	handleLine.accept(line);
			    }
			}
			catch(FileNotFoundException fnfe) {
				onFileNotFound.run();
			}
		}, errorHandler);
	}
	
	@Override
	public void dispose() {
		// Nothing to dispose
	}
}
