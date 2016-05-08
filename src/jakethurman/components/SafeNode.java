package jakethurman.components;

import javafx.scene.Node;

/* Keeps a node to a much more confined API */
public class SafeNode {
	/* Static Instance representing an empty node */
	public static final SafeNode NONE = new SafeNode(null);
	
	private final Node node;
	
	public SafeNode(Node node) {
		this.node = node;
	}
	
	// Gets the unsafe javafx node value.
	protected Node getUnsafe() {
		return node;
	}
	
	// Sets the event to be run when the mouse is over this item
	public void setOnMouseEntered(Runnable handler) {
		node.setOnMouseEntered(e -> handler.run());
	}

	// Sets the event to be run when the mouse leaves this item
	public void setOnMouseExited(Runnable handler) {
		node.setOnMouseExited(e -> handler.run());
	}

	// Set the event to be run on mouse click of this item
	public void setOnMouseClicked(Runnable handler) {
		node.setOnMouseClicked(e -> handler.run());
	}
	
	@Override
	public String toString() {
		return "SAFE_" + node.toString();
	}
}
