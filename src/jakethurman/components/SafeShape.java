package jakethurman.components;

import javafx.scene.shape.Shape;

public class SafeShape extends SafeNode {
	private final Shape shape;
	
	public SafeShape(Shape shape) {
		super(shape);
		
		this.shape = shape;
	}
	
	public void setFill(SafePaint fill) {
		shape.setFill(fill.getUnsafe());
	}
	
	public SafePaint getFill() {
		return new SafePaint(shape.getFill());
	}
}
