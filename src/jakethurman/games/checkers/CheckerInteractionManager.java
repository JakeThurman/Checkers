package jakethurman.games.checkers;

import java.util.function.Consumer;
import javafx.scene.Cursor;
import jakethurman.foundation.CleanupHandler;
import jakethurman.foundation.Disposable;
import jakethurman.components.SafeNode;
import jakethurman.components.SafeSceneInteraction;
import jakethurman.games.checkers.components.Checker;

public class CheckerInteractionManager implements Disposable {
	private final SafeSceneInteraction scene;
	private final SelectionManager     selection;
	private final CheckersTurnManager  turnManager;
	private final CleanupHandler       cleanup;
	
	private Consumer<Checker> afterSelect    = null;
	private Runnable          afterUnselect = null;
	
	public CheckerInteractionManager(SafeSceneInteraction scene, SelectionManager selection, CheckersTurnManager turnManager) {
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

        node.setOnMouseExited(() -> scene.setCursor(Cursor.DEFAULT));
        node.setOnMouseEntered(() -> { 
        	if(c.getIsPlayer1() == turnManager.isPlayer1sTurn()) 
        		scene.setCursor(Cursor.HAND); 
        });
	}
	
	public void initializeMoveOption(SafeNode node, Runnable moveHere) {
        node.setOnMouseEntered(() -> scene.setCursor(Cursor.HAND));
        node.setOnMouseExited(() -> scene.setCursor(Cursor.DEFAULT));
		
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
			afterSelect.accept(c);
	}
	
	private void doUnselect() {
		selection.unselect();
		
		if (this.afterUnselect != null)
			afterUnselect.run();
	}

	public void dispose() {
		cleanup.dispose();
		
		// Clear out stored handlers
		afterSelect   = null;
		afterUnselect = null;
	}
}
