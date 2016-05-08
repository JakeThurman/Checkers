package jakethurman.components;

import javafx.scene.Scene;
import javafx.scene.Cursor;

/*
 *  Minimally wraps the javafx Scene to allow for only the needed interactions.
 */
public class SafeScene {
	private final Scene scene;
	
	public SafeScene(SafeParent safeNode) {
		this(new Scene(safeNode.getUnsafe_Parent()));
	}
	
	public SafeScene(Scene scene) {
		this.scene = scene;
	}
	
	/* Cursor handling */
	public void setDefaultCursor() {
		scene.setCursor(Cursor.DEFAULT);
	}
	public void setSelectableCursor() {
		scene.setCursor(Cursor.HAND);
	}
	
	
	/* Gets the core Scene value */
	public Scene getUnsafe() {
		return scene;
	}
}
