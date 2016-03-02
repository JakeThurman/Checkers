import javafx.scene.Cursor;
import javafx.scene.shape.Shape;

public class CheckerInteractionManager {
	private final SafeSceneInteraction scene;
	private final SelectionManager selection;
	
	public CheckerInteractionManager(SafeSceneInteraction scene, SelectionManager selection) {
		this.scene = scene;
		this.selection = selection;
	}
	
	public void initalize(Checker c) {
		Shape node = c.getNode();
        node.setOnMouseEntered((e) -> onClicleMouseOver());
        node.setOnMouseExited((e) -> onClicleMouseOut());
        node.setOnMouseClicked((e) -> onCircleClick(c));
	}
	
	private void onCircleClick(Checker c) {
		boolean anySelected  = selection.hasSelected();
		boolean thisSelected = selection.isSelected(c);
		
		if (thisSelected || (!thisSelected && anySelected))
			selection.unselect();
		
		if (!thisSelected)
			selection.setSelected(c);
	}
	
	private void onClicleMouseOver() {
		scene.setCursor(Cursor.HAND);
	}
	
	private void onClicleMouseOut() {
		scene.setCursor(Cursor.DEFAULT);
	}
}
