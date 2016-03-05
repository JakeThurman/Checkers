public class CleanupHandler implements Disposable {
	private final Disposable[] toDispose;
	public CleanupHandler(Disposable...toDispose) {
		this.toDispose = toDispose;
	}
	
	public void dispose() {
		for (Disposable d : toDispose)
			d.dispose();
	}
}
