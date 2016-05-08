package jakethurman.components.factories;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import jakethurman.components.SafePaint;
import jakethurman.components.SafeShape;
import jakethurman.foundation.Disposable;

/* Helper class for rendering shapes */
public class ShapeFactory implements Disposable {
	private final ShapeSettings settings;
	
	/* 
	 * Takes settings as an input object.
	 * This may not have been the best way
	 * to do this but it is the way I did
	 * it. I can always change it later.
	 */
	public ShapeFactory(final ShapeSettings settings) {
		this.settings = settings;
	}
	
	/* Private common circle generation implementation method */
	private Circle createUnsafeCircle(final SafePaint fill) {
		Circle c = new Circle(settings.getCircleRadius(), fill.getUnsafe());
		c.setStroke(Color.WHITE);
        c.setStrokeWidth(settings.getCircleBorder());
        
        return c;
	}
	
	/* Creates a circle */
	public SafeShape createCircle(final SafePaint fill) {
		// return a protected version of the circle returned by createUnsafeCircle()
        return new SafeShape(createUnsafeCircle(fill)); 
	}
	
	/* Creates a circle*/ 
	public SafeShape createOpaqueCircle(final SafePaint fill) {
		Circle c = createUnsafeCircle(fill);
		c.setOpacity(0.7); // Set the opacity to 70%. This should probably be a parameter, but it does not need to be right now
		return new SafeShape(c); // return a protected version of c
	}
	
	/* 
	 * Creates a rectangle... Actually a square. 
	 * TODO: Rename to createSquare()
	 */
	public SafeShape createRect(final SafePaint fill) {
		// Get the size of the square
		final int squareSize = settings.getSquareSize();
		Rectangle r = new Rectangle(squareSize, squareSize, fill.getUnsafe());
		return new SafeShape(r);
	}

	@Override
	public void dispose() {
		settings.dispose();
	}
}
