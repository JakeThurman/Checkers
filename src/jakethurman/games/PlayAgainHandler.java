package jakethurman.games;

import jakethurman.foundation.Disposable;
import jakethurman.foundation.CleanupHandler;

public class PlayAgainHandler implements Runnable, Disposable {
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
	
	public void dispose() {
		cleanup.dispose();
	}
}