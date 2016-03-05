public class PlayAgainHandler {
	private final CleanupHandler cleanup;
	private final Runnable       rerender;
	
	public PlayAgainHandler(CleanupHandler cleanup, Runnable rerender) {
		this.cleanup  = cleanup;
		this.rerender = rerender;
	}
	
	public void run() {
		cleanup.dispose();
		rerender.run();
	}
}
