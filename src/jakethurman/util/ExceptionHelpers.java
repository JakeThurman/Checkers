package jakethurman.util;

import java.util.function.Consumer;
import java.util.function.Function;

public class ExceptionHelpers {
	public static <T extends Exception> void tryRun(RunnableThrower<T> r, Consumer<Exception> onError) {
		try {
			r.run();
		}
		catch(Exception e) {
			onError.accept(e);
		}
	}
	
	public static <V> V tryGet(SupplierThrower<V> s, Function<Exception, V> onError) {
		try {
			return s.get();
		}
		catch(Exception e) {
			return onError.apply(e);
		}
	}
	
	public static interface RunnableThrower<T extends Exception> {
		public void run() throws T;
	}
	
	public static interface SupplierThrower<V> {
		public V get() throws Exception;
	}
}
