public class PlayAgainHandler {
	private final CleanupHandler cleanup;
	
	public PlayAgainHandler(CleanupHandler cleanup) {
		this.cleanup = cleanup;
	}
	
	public void run() {
		cleanup.dispose();
	}
}
