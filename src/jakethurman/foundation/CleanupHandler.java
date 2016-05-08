package jakethurman.foundation;

/*
 * Helper class for Disposable interface.
 * Takes any number of Disposable items
 * and compiles there dispose() methods 
 * so that they can all be invoked from
 * a single call to:
 * 
 * CleanupHandler(xDisposable, yDisposable).dispose();
 * 
 * This is useful for keeping all of the
 * code needed to add a new dependency 
 * to a class in one place: The c'tor.
 */
public class CleanupHandler implements Disposable {
	private final Disposable[] toDispose;
	public CleanupHandler(Disposable...toDispose) {
		this.toDispose = toDispose;
	}

	@Override
	public void dispose() {
		for (Disposable d : toDispose)
			d.dispose();
	}
}
