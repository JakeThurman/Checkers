import javafx.scene.shape.Shape;
import javafx.scene.paint.Paint;

public class SelectionManager {	
	private final Checkerboard data;	
	
	private Paint originalColor = null;
	private Shape selected      = null;
	
	public SelectionManager(Checkerboard data) {
		this.data = data;
	}
		
	public boolean hasSelected() {
		return this.selected != null;
	}
	
	public boolean isSelected(Checker c) {
		return c.getNode().equals(this.selected);
	}
	
	public void setSelected(Checker c) {
		this.selected = c.getNode();
		
		//Color management
		this.originalColor = this.selected.getFill();
		this.selected.setFill(Settings.SELECTED_COLOR);
		
		//TODO set data selected
		data.getAvailableSpaces(c);
	}
	
	public void unselect() {
		//Color management
		if (hasSelected())
			this.selected.setFill(originalColor);
		
		this.selected = null;
	}
}
