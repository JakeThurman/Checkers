package jakethurman.components.factories;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import jakethurman.components.SafePaint;
import jakethurman.components.SafeShape;
import jakethurman.foundation.Disposable;

public class ShapeFactory implements Disposable {
	private final ShapeSettings settings;
	
	public ShapeFactory(final ShapeSettings settings) {
		this.settings = settings;
	}
	
	private Circle createUnsafeCircle(final SafePaint fill) {
		Circle c = new Circle(settings.getCircleRadius(), fill.getUnsafe());
		c.setStroke(Color.WHITE);
        c.setStrokeWidth(settings.getCircleBorder());
        
        return c;
	}
	
	public SafeShape createCircle(final SafePaint fill) {
        return new SafeShape(createUnsafeCircle(fill));
	}
	
	public SafeShape createOpaqueCircle(final SafePaint fill) {
		Circle c = createUnsafeCircle(fill);
		c.setOpacity(0.7);
		return new SafeShape(c);
	}
	
	public SafeShape createRect(final SafePaint fill) {
		final int squareSize = settings.getSquareSize();
		Rectangle r = new Rectangle(squareSize, squareSize, fill.getUnsafe());
		return new SafeShape(r);
	}
	
	public void dispose() {
		settings.dispose();
	}
}
