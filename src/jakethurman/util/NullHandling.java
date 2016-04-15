package jakethurman.util;

public class NullHandling {
	@SafeVarargs
	public static <T> T firstNonNull(T...objs) {
		for (T obj : objs) {
			if (obj != null)
				return obj;
		}
		
		return null;
	}
}
