package jakethurman.games.checkers;

import java.util.function.Consumer;
import jakethurman.foundation.CleanupHandler;
import jakethurman.foundation.Disposable;
import jakethurman.components.SafeNode;
import jakethurman.components.SafeScene;
import jakethurman.games.Difficulty;
import jakethurman.games.checkers.components.Checker;

/*
 * Handles Interactions with checkers and move options
 */
public class CheckerInteractionManager implements Disposable {
	// Data
	private final SafeScene           scene;
	private final SelectionManager    selection;
	private final CheckersTurnManager turnManager;
	private final CleanupHandler      cleanup;
	private final boolean             isVsAI;
	
	// Events
	private Consumer<Checker> afterSelect    = null;
	private Runnable          afterUnselect = null;
	
	// C'tor
	public CheckerInteractionManager(SafeScene scene, SelectionManager selection, CheckersTurnManager turnManager, Settings settings) {
		this.scene = scene;
		this.selection = selection;
		this.turnManager = turnManager;
		this.cleanup = new CleanupHandler(selection, turnManager);
		this.isVsAI = settings.getDifficulty() != Difficulty.HUMAN;
	}
	
	// Sets the event listener to be run after selection occurs
	public void setAfterSelect(Consumer<Checker> afterSelect) {
		this.afterSelect = afterSelect;
	}
	
	// Sets the event listener to be run after unselection occurs
	public void setAfterUnselect(Runnable afterUnselect) {
		this.afterUnselect = afterUnselect;
	}
	
	// Initializes a checker for a checker board.
	public void initalizeChecker(Checker c) {
		// Only allow for actions to be performed on non AI checkers
		// Unless of course, this is a non-AI game completely!
		if (c.getIsPlayer1() || !isVsAI) {
			final SafeNode node = c.getNode();
			
			// Set the click event on the node
			node.setOnMouseClicked(() -> doSelection(c));
	        node.setOnMouseExited(() -> scene.setDefaultCursor());
	        node.setOnMouseEntered(() -> { 
	        	// Don't show the user that you can click the checker
	        	// if THEY CAN'T CLICK THE CHECKER - because it doesn't belong to them
	        	if(c.getIsPlayer1() == turnManager.isPlayer1sTurn()) 
	        		scene.setSelectableCursor(); 
	        });
		}
	}
	
	// Initialize the events for a move option. 
	// 	- 	A move option is the transparent circle 
	//		that appears when you click on a checker)
	public void initializeMoveOption(SafeNode node, Runnable moveHere) {
        node.setOnMouseEntered(() -> scene.setSelectableCursor());
        node.setOnMouseExited(() -> scene.setDefaultCursor());
		
		node.setOnMouseClicked(() -> {
			doUnselect(); // Unselect the current piece, and then move it.
			moveHere.run();
		});
	}
	
	// Handles selection of a piece
	private void doSelection(Checker c) {
		// Check if the user has the ability to do 
		// things and what things have already been done
		boolean canSelect      = turnManager.isPlayer1sTurn() == c.getIsPlayer1();
		boolean anySelected    = selection.hasSelected();
		boolean thisSelected   = selection.isSelected(c);
		boolean shouldUnselect = thisSelected || (!thisSelected && anySelected);
		boolean shouldSelect   = !thisSelected && canSelect;
		
		// Unselect if we should first
		if (shouldUnselect)
			doUnselect();
		
		// Then select if we should
		if (shouldSelect)
			selection.setSelected(c);
		
		// Finally call the after select event 
		// if we need to, and it exists.
		if (shouldSelect && this.afterSelect != null)
			this.afterSelect.accept(c);
	}
	
	// Handles unselection of the current pieces
	private void doUnselect() {
		// Unselect the piece
		this.selection.unselect();
		
		// Fire the event listener if there is one.
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
