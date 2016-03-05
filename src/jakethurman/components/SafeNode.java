package jakethurman.components;

import javafx.scene.Node;

public class SafeNode {
	private final Node node;
	
	public SafeNode(Node node) {
		this.node = node;
	}
	
	public Node getUnsafe() {
		return node;
	}
	
	public void setOnMouseEntered(Runnable handler) {
		node.setOnMouseEntered(e -> handler.run());
	}

	public void setOnMouseExited(Runnable handler) {
		node.setOnMouseExited(e -> handler.run());
	}

	public void setOnMouseClicked(Runnable handler) {
		node.setOnMouseClicked(e -> handler.run());
	}
}
