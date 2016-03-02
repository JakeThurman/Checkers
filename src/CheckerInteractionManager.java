import java.util.function.Consumer;
import javafx.scene.Cursor;
import javafx.scene.Node;

public class CheckerInteractionManager {
	private final SafeSceneInteraction scene;
	private final SelectionManager selection;
	
	private Consumer<Checker> afterSelect    = null;
	private Runnable          afterUnselect = null;
	
	public CheckerInteractionManager(SafeSceneInteraction scene, SelectionManager selection) {
		this.scene = scene;
		this.selection = selection;
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
		
		initializeMouseOver(node);
	}
	
	public void initializeMoveOption(Node node, Runnable moveHere) {
		initializeMouseOver(node);
		
		node.setOnMouseClicked((e) -> {
			doUnselect();
			moveHere.run();
		});
	}
	
	private void initializeMouseOver(Node node) {
        node.setOnMouseEntered((e) -> onClicleMouseOver());
        node.setOnMouseExited((e) -> onClicleMouseOut());
	}
	
	private void doSelection(Checker c) {
		boolean anySelected    = selection.hasSelected();
		boolean thisSelected   = selection.isSelected(c);
		boolean shouldUnselect = thisSelected || (!thisSelected && anySelected);
		
		if (shouldUnselect)
			doUnselect();
		
		if (!thisSelected)
			selection.setSelected(c);
		
		if (!thisSelected && this.afterSelect != null)
			afterSelect.accept(c);
	}
	
	private void doUnselect() {
		selection.unselect();
		
		if (this.afterUnselect != null)
			afterUnselect.run();
	}
	
	private void onClicleMouseOver() {
		scene.setCursor(Cursor.HAND);
	}
	
	private void onClicleMouseOut() {
		scene.setCursor(Cursor.DEFAULT);
	}
}
