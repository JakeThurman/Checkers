package jakethurman.games.checkers;

import jakethurman.components.SafePaint;
import jakethurman.components.SafeShape;
import jakethurman.foundation.Disposable;
import jakethurman.games.checkers.components.Checker;

public class SelectionManager implements Disposable {	
	private SafePaint originalColor = null;
	private SafeShape selected      = null;
			
	public boolean hasSelected() {
		return this.selected != null;
	}
	
	public boolean isSelected(Checker c) {
		return c.getNode().equals(this.selected);
	}
	
	public void setSelected(Checker c) {
		this.selected      = c.getNode();
		this.originalColor = this.selected.getFill();
		
		//Color management
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
