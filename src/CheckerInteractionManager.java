import java.util.function.Consumer;
import javafx.scene.Cursor;
import javafx.scene.Node;

public class CheckerInteractionManager {
	private final SafeSceneInteraction scene;
	private final SelectionManager selection;
	
	private Consumer<Checker> afterClick    = null,
			                  afterUnselect = null;
	
	public CheckerInteractionManager(SafeSceneInteraction scene, SelectionManager selection) {
		this.scene = scene;
		this.selection = selection;
	}
	
	public void setAfterClick(Consumer<Checker> afterClick) {
		this.afterClick = afterClick;
	}
	
	public void setAfterUnselect(Consumer<Checker> afterUnselect) {
		this.afterUnselect = afterUnselect;
	}
	
	public void initalizeAll(Checker c) {
		final Node node = c.getNode();
		node.setOnMouseClicked((e) -> onCircleClick(c));
		
		initializeNoClick(node);
	}
	
	public void initializeNoClick(Node node) {
        node.setOnMouseEntered((e) -> onClicleMouseOver());
        node.setOnMouseExited((e) -> onClicleMouseOut());
	}
	
	private void onCircleClick(Checker c) {
		boolean anySelected    = selection.hasSelected();
		boolean thisSelected   = selection.isSelected(c);
		boolean shouldUnselect = thisSelected || (!thisSelected && anySelected);
		
		if (shouldUnselect)
			selection.unselect();
		
		if (shouldUnselect && this.afterUnselect != null)
			afterUnselect.accept(c);
		
		if (!thisSelected)
			selection.setSelected(c);
		
		if (!shouldUnselect && this.afterClick != null)
			afterClick.accept(c);
	}
	
	private void onClicleMouseOver() {
		scene.setCursor(Cursor.HAND);
	}
	
	private void onClicleMouseOut() {
		scene.setCursor(Cursor.DEFAULT);
	}
}
