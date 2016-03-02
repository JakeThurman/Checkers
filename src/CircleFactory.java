import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

public class CircleFactory {    	
	public Circle create(final Color fill, final Color border) {
		// Draw the circle
		Circle c = new Circle(Settings.CIRCLE_RADIUS, fill);
		c.setStroke(border);
        c.setStrokeWidth(Settings.CIRCLE_BORDER);
                
        return c;
	}
}
