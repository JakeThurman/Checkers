package jakethurman.games.checkers;

import jakethurman.components.SafePaint;
import jakethurman.components.SafeShape;
import jakethurman.foundation.Disposable;
import jakethurman.games.checkers.components.Checker;

/*
 * Manages the currently selected checker.
 */
public class SelectionManager implements Disposable {	
	// Dependencies
	private final Settings settings;
	
	// We stores the selected item and its original color so we can 
	// revert the color when it is unselected.
	private SafePaint originalColor = null;
	private SafeShape selected      = null;
	
	// C'tor
	public SelectionManager(Settings settings) {
		this.settings = settings;
	}
	
	// Is there a currently selected item?
	public boolean hasSelected() {
		return this.selected != null;
	}
	
	// Is this checker selected?
	public boolean isSelected(Checker c) {
		return c.getNode().equals(this.selected);
	}
	
	// Selects this checker.
	public void setSelected(Checker c) {
		
		this.selected      = c.getNode();
		this.originalColor = this.selected.getFill();
		
		//Color management
		this.selected.setFill(settings.getSelectColor());
	}
	
	// Removes the current selection and restores its color
	public void unselect() {
		//Color management
		if (hasSelected())
			this.selected.setFill(originalColor);
		
		// Set that we have no selected item
		this.selected = null;
	}

	@Override
	public void dispose() {
		// Clear out stored variables
		originalColor = null;
		selected      = null;
	}
}
