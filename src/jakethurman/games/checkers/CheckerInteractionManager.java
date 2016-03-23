package jakethurman.games.checkers;

import java.util.function.Consumer;
import jakethurman.foundation.CleanupHandler;
import jakethurman.foundation.Disposable;
import jakethurman.components.SafeNode;
import jakethurman.components.SafeScene;
import jakethurman.games.checkers.components.Checker;

public class CheckerInteractionManager implements Disposable {
	private final SafeScene           scene;
	private final SelectionManager    selection;
	private final CheckersTurnManager turnManager;
	private final CleanupHandler      cleanup;
	
	private Consumer<Checker> afterSelect    = null;
	private Runnable          afterUnselect = null;
	
	public CheckerInteractionManager(SafeScene scene, SelectionManager selection, CheckersTurnManager turnManager) {
		this.scene = scene;
		this.selection = selection;
		this.turnManager = turnManager;
		this.cleanup = new CleanupHandler(selection, turnManager);
	}
	
	public void setAfterSelect(Consumer<Checker> afterSelect) {
		this.afterSelect = afterSelect;
	}
	
	public void setAfterUnselect(Runnable afterUnselect) {
		this.afterUnselect = afterUnselect;
	}
	
	public void initalizeChecker(Checker c) {
		final SafeNode node = c.getNode();
		node.setOnMouseClicked(() -> doSelection(c));

        node.setOnMouseExited(() -> scene.setDefaultCursor());
        node.setOnMouseEntered(() -> { 
        	if(c.getIsPlayer1() == turnManager.isPlayer1sTurn()) 
        		scene.setSelectableCursor(); 
        });
	}
	
	public void initializeMoveOption(SafeNode node, Runnable moveHere) {
        node.setOnMouseEntered(() -> scene.setSelectableCursor());
        node.setOnMouseExited(() -> scene.setDefaultCursor());
		
		node.setOnMouseClicked(() -> {
			doUnselect();
			moveHere.run();
		});
	}
	
	private void doSelection(Checker c) {
		boolean canSelect      = turnManager.isPlayer1sTurn() == c.getIsPlayer1();
		boolean anySelected    = selection.hasSelected();
		boolean thisSelected   = selection.isSelected(c);
		boolean shouldUnselect = thisSelected || (!thisSelected && anySelected);
		boolean shouldSelect   = !thisSelected && canSelect;
		
		if (shouldUnselect)
			doUnselect();
		
		if (shouldSelect)
			selection.setSelected(c);
		
		if (shouldSelect && this.afterSelect != null)
			this.afterSelect.accept(c);
	}
	
	private void doUnselect() {
		this.selection.unselect();
		
		if (this.afterUnselect != null)
			this.afterUnselect.run();
	}

	@Override
	public void dispose() {
		this.cleanup.dispose();
		
		// Clear out stored handlers
		this.afterSelect   = null;
		this.afterUnselect = null;
	}
}
