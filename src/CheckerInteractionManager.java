import java.util.function.Consumer;
import javafx.scene.Cursor;
import javafx.scene.Node;

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
		final Node node = c.getNode();
		node.setOnMouseClicked((e) -> doSelection(c));

        node.setOnMouseExited((e) -> scene.setCursor(Cursor.DEFAULT));
        node.setOnMouseEntered((e) -> { 
        	if(c.getIsPlayer1() == turnManager.isPlayer1sTurn()) 
        		scene.setCursor(Cursor.HAND); 
        });
	}
	
	public void initializeMoveOption(Node node, Runnable moveHere) {
        node.setOnMouseEntered((e) -> scene.setCursor(Cursor.HAND));
        node.setOnMouseExited((e) -> scene.setCursor(Cursor.DEFAULT));
		
		node.setOnMouseClicked((e) -> {
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
