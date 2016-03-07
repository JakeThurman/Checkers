package jakethurman.components;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

public class SafeBorderPane extends SafeNode {
	private final BorderPane pane;
	
	public SafeBorderPane() {
		this(new BorderPane());
	}

	public SafeBorderPane(BorderPane pane) {
		super(pane);
		this.pane = pane;
	}
	
	public void setChildren(ReadOnlyPositionedNodes pn) {
		pane.setCenter(getUnsafe(pn.getCenter()));
		pane.setBottom(getUnsafe(pn.getBottom()));
		pane.setTop(getUnsafe(pn.getTop()));
		pane.setRight(getUnsafe(pn.getRight()));
		pane.setLeft(getUnsafe(pn.getLeft()));
	}
	
	private static Node getUnsafe(SafeNode node) {
		return node == null ? null : node.getUnsafe();
	}

	public void setPadding(double width) {
		pane.setPadding(new Insets(width));
	}
}
