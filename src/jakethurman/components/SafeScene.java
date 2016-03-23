package jakethurman.components;

import javafx.scene.Scene;
import javafx.scene.Cursor;

public class SafeScene {
	private final Scene scene;
	
	public SafeScene(SafeParent safeNode) {
		this(new Scene(safeNode.getUnsafe_Parent()));
	}
	
	public SafeScene(Scene scene) {
		this.scene = scene;
	}
	
	public void setDefaultCursor() {
		scene.setCursor(Cursor.DEFAULT);
	}
	public void setSelectableCursor() {
		scene.setCursor(Cursor.HAND);
	}
	
	public Scene getUnsafe() {
		return scene;
	}
}
