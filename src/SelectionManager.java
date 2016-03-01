import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class SelectionManager {
	private Paint  originalColor = null;
	private Circle selected      = null;
		
	public boolean hasSelected() {
		return this.selected != null;
	}
	
	public boolean isSelected(Circle c) {
		return this.selected == c;
	}
	
	public void setSelected(Circle c) {
		this.selected = c;
		
		//Color management
		this.originalColor = c.getFill();
		c.setFill(Settings.SELECTED_COLOR);
	}
	
	public void unselect() {
		//Color management
		if (hasSelected())
			this.selected.setFill(originalColor);
		
		this.selected = null;
	}
}
