package jakethurman.components;

import javafx.geometry.Insets;
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
		pane.setCenter(pn.getCenter().getUnsafe());
		pane.setBottom(pn.getBottom().getUnsafe());
		pane.setTop(pn.getTop().getUnsafe());
		pane.setRight(pn.getRight().getUnsafe());
		pane.setLeft(pn.getLeft().getUnsafe());
	}

	public void setPadding(double width) {
		pane.setPadding(new Insets(width));
	}
}
