package jakethurman.components;

import jakethurman.foundation.Disposable;

public interface ReadOnlyPositionedNodes extends Disposable {
	public SafeNode getCenter();
	public SafeNode getBottom();
	public SafeNode getTop();
	public SafeNode getLeft();
	public SafeNode getRight();
}
