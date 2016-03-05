import javafx.scene.Scene;
import javafx.scene.Cursor;

public class SafeSceneInteraction implements Disposable {
	private final Scene scene;
	
	public SafeSceneInteraction(Scene scene) {
		this.scene = scene;
	}
	
	public void setCursor(Cursor c) {
		scene.setCursor(c);
	}
	
	public void dispose() {
		//Nothing to cleanup
	}
}
