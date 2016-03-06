package jakethurman.components;

import javafx.scene.Scene;
import javafx.scene.Cursor;

public class SafeSceneInteraction {
	private final Scene scene;
	
	public SafeSceneInteraction(Scene scene) {
		this.scene = scene;
	}
	
	public void setDefaultCursor() {
		scene.setCursor(Cursor.DEFAULT);
	}
	public void setSelectableCursor() {
		scene.setCursor(Cursor.HAND);
	}
}
