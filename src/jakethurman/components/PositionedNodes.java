package jakethurman.components;

import javafx.scene.Node;

public class PositionedNodes implements ReadOnlyPositionedNodes {
	private SafeNode center;
	private SafeNode bottom;
	private SafeNode top;
	private SafeNode left;
	private SafeNode right;
	
	public PositionedNodes setCenter(final SafeNode n) {
		this.center = n;
		return this;
	}
	
	public Node getCenter() {
		return center.getUnsafe();
	}
	
	public PositionedNodes setBottom(final SafeNode n) {
		this.bottom = n;
		return this;
	}
	
	public Node getBottom() {
		return bottom.getUnsafe();
	}

	public PositionedNodes setTop(final SafeNode n) {
		this.top = n;
		return this;
	}
	
	public Node getTop() {
		return top.getUnsafe();
	}

	public PositionedNodes setLeft(final SafeNode n) {
		this.left = n;
		return this;
	}
	
	public Node getLeft() {
		return left.getUnsafe();
	}

	public PositionedNodes setRight(final SafeNode n) {
		this.right = n;
		return this;
	}
	
	public Node getRight() {
		return right.getUnsafe();
	}
	
	public void dispose() {
		this.center = null;
		this.bottom = null;
		this.top    = null;
		this.left   = null;
		this.right  = null;
	}
}
