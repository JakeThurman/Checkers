package jakethurman.components;

import javafx.scene.Node;
public class PositionedNodes implements ReadOnlyPositionedNodes {
	private Node center;
	private Node bottom;
	private Node top;
	private Node left;
	private Node right;
	
	public PositionedNodes setCenter(final Node n) {
		this.center = n;
		return this;
	}
	
	public Node getCenter() {
		return center;
	}
	
	public PositionedNodes setBottom(final Node n) {
		this.bottom = n;
		return this;
	}
	
	public Node getBottom() {
		return bottom;
	}

	public PositionedNodes setTop(final Node n) {
		this.top = n;
		return this;
	}
	
	public Node getTop() {
		return top;
	}

	public PositionedNodes setLeft(final Node n) {
		this.left = n;
		return this;
	}
	
	public Node getLeft() {
		return left;
	}

	public PositionedNodes setRight(final Node n) {
		this.right = n;
		return this;
	}
	
	public Node getRight() {
		return right;
	}
	
	public void dispose() {
		this.center = null;
		this.bottom = null;
		this.top    = null;
		this.left   = null;
		this.right  = null;
	}
}
