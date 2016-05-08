package jakethurman.components;

import javafx.scene.shape.Shape;

/* Wraps a javafx shape. */
public class SafeShape extends SafeNode {
	private final Shape shape; // Core value
	
	// C'tor
	public SafeShape(Shape shape) {
		super(shape);
		
		this.shape = shape;
	}
	
	// Sets the fill of the object
	public void setFill(SafePaint fill) {
		shape.setFill(fill == null ? null : fill.getUnsafe());
	}
	
	// Gets the current fill of the object
	public SafePaint getFill() {
		return new SafePaint(shape.getFill());
	}
}
