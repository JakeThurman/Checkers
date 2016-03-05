package jakethurman.components;

import javafx.scene.shape.Circle;
import jakethurman.foundation.Disposable;
import jakethurman.games.checkers.Settings;
import javafx.scene.paint.Color;

public class CircleFactory implements Disposable {    	
	public Circle create(final Color fill) {
		// Draw the circle
		Circle c = new Circle(Settings.CIRCLE_RADIUS, fill);
		c.setStroke(Color.WHITE);
        c.setStrokeWidth(Settings.CIRCLE_BORDER);
                
        return c;
	}
	
	public Circle createOpaque(final Color fill) {
		Circle c = create(fill);
		c.setOpacity(0.7);
		return c;
	}
	
	public void dispose() {
		//Nothing to dispose
	}
}
