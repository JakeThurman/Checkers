package jakethurman.components;

import javafx.scene.Node;
import jakethurman.foundation.Disposable;

public interface ReadOnlyPositionedNodes extends Disposable {
	public Node getCenter();
	public Node getBottom();
	public Node getTop();
	public Node getLeft();
	public Node getRight();
}
