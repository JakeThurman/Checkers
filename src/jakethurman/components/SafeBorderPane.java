package jakethurman.components;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

/* Wraps a border pane and only allows only for minimal interaction with the class */
public class SafeBorderPane extends SafeParent {
	private final BorderPane pane;
	
	public SafeBorderPane() {
		this(new BorderPane());
	}

	private SafeBorderPane(BorderPane pane) {
		super(pane);
		this.pane = pane;
	}
	
	public void setMinSize(double h, double w) {
		pane.setMinHeight(h);
		pane.setMinWidth(w);
	}
	
	public void setChildren(ReadOnlyPositionedNodes pn) {
		pane.setCenter(getUnsafe(pn.getCenter()));
		pane.setBottom(getUnsafe(pn.getBottom()));
		pane.setTop(getUnsafe(pn.getTop()));
		pane.setRight(getUnsafe(pn.getRight()));
		pane.setLeft(getUnsafe(pn.getLeft()));
	}
	
	// Helper for setChildren to handle nulls.
	private static Node getUnsafe(SafeNode node) {
		return node == null ? null : node.getUnsafe();
	}

	public void setPadding(double width) {
		pane.setPadding(new Insets(width));
	}
}
