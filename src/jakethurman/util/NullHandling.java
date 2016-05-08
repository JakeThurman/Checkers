package jakethurman.util;

/* Helper methods for null handling */
public class NullHandling {
	@SafeVarargs
	/* Takes the first argument that does not equal null. If all are null, it still returns null. */
	public static <T> T firstNonNull(T...objs) {
		for (T obj : objs) {
			if (obj != null)
				return obj;
		}
		
		return null;
	}
}
