import javafx.scene.shape.Circle;

public class CircleInteractionFactory {
	private final SafeSceneInteraction scene;
	private final SelectionManager selection;
	
	public CircleInteractionFactory(SafeSceneInteraction scene, SelectionManager selection) {
		this.scene = scene;
		this.selection = selection;
	}
	
	public CircleInteractionManager create(Circle c) {
		return new CircleInteractionManager(scene, c, selection);
	}
}
