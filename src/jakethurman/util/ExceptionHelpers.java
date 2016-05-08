package jakethurman.util;

import java.util.function.Consumer;
import java.util.function.Function;

// Basic static Exception helper methods
public class ExceptionHelpers {
	// Trys to run a given method and calls the error handler if something goes wrong
	public static <T extends Exception> void tryRun(RunnableThrower<T> r, Consumer<Exception> onError) {
		try {
			r.run();
		}
		catch(Exception e) {
			onError.accept(e);
		}
	}
	
	// Trys to get a value and if something goes wrong, it calls an error handler
	// to try and correct the problem which should return some default value to get
	// anyway in this error case.
	public static <V> V tryGet(SupplierThrower<V> s, Function<Exception, V> onError) {
		try {
			return s.get();
		}
		catch(Exception e) {
			return onError.apply(e);
		}
	}
	
	// Helper Interface for tryRun
	public static interface RunnableThrower<T extends Exception> {
		public void run() throws T;
	}
	
	// Helper Interface for tryGet
	public static interface SupplierThrower<V> {
		public V get() throws Exception;
	}
}
