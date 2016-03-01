import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;

public class CheckerboardInteractionManager {
	private final SafeSceneInteraction scene;
	
	public CheckerboardInteractionManager(SafeSceneInteraction scene) {
		this.scene = scene;
	}
	
	public void onCircleClick(MouseEvent e) {
	}
	
	public void onClicleMouseOver(MouseEvent e) {
		scene.setCursor(Cursor.HAND);
	}
	
	public void onClicleMouseOut(MouseEvent e) {
		scene.setCursor(Cursor.DEFAULT);
	}
}
