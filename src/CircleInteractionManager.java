import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

public class CircleInteractionManager {
	private final SafeSceneInteraction scene;
	private final SelectionManager selection;
	private final Circle circle;
	
	public CircleInteractionManager(SafeSceneInteraction scene, Circle circle, SelectionManager selection) {
		this.scene = scene;
		this.circle = circle;
		this.selection = selection;
	}
	
	public void onCircleClick(MouseEvent e) {
		boolean anySelected  = selection.hasSelected();
		boolean thisSelected = selection.isSelected(this.circle);
		
		if (thisSelected) {
			selection.unselect();
		}
		else {
			if (anySelected)
				selection.unselect();
			
			selection.setSelected(this.circle);
		}
	}
	
	public void onClicleMouseOver(MouseEvent e) {
		scene.setCursor(Cursor.HAND);
	}
	
	public void onClicleMouseOut(MouseEvent e) {
		scene.setCursor(Cursor.DEFAULT);
	}
}
