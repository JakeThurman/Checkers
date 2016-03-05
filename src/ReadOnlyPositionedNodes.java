import javafx.scene.Node;

public interface ReadOnlyPositionedNodes extends Disposable {
	public Node getCenter();
	public Node getBottom();
	public Node getTop();
	public Node getLeft();
	public Node getRight();
}
