package jakethurman.components;

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
	
	public SafeNode getCenter() {
		return center;
	}
	
	public PositionedNodes setBottom(final SafeNode n) {
		this.bottom = n;
		return this;
	}
	
	public SafeNode getBottom() {
		return bottom;
	}

	public PositionedNodes setTop(final SafeNode n) {
		this.top = n;
		return this;
	}
	
	public SafeNode getTop() {
		return top;
	}

	public PositionedNodes setLeft(final SafeNode n) {
		this.left = n;
		return this;
	}
	
	public SafeNode getLeft() {
		return left;
	}

	public PositionedNodes setRight(final SafeNode n) {
		this.right = n;
		return this;
	}
	
	public SafeNode getRight() {
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
