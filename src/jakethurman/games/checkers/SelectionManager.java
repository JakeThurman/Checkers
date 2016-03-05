package jakethurman.games.checkers;

import javafx.scene.shape.Shape;
import javafx.scene.paint.Paint;
import jakethurman.foundation.Disposable;
import jakethurman.games.checkers.components.Checker;

public class SelectionManager implements Disposable {	
	private Paint originalColor = null;
	private Shape selected      = null;
			
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
	}
	
	public void unselect() {
		//Color management
		if (hasSelected())
			this.selected.setFill(originalColor);
		
		this.selected = null;
	}
	
	public void dispose() {
		// Clear out stored variables
		originalColor = null;
		selected      = null;
	}
}
