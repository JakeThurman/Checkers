package jakethurman.components;

import javafx.scene.shape.Circle;
import jakethurman.foundation.Disposable;
import jakethurman.games.checkers.Settings;
import javafx.scene.paint.Color;

public class CircleFactory implements Disposable {    
	private Circle createUnsafe(final Color fill) {
		Circle c = new Circle(Settings.CIRCLE_RADIUS, fill);
		c.setStroke(Color.WHITE);
        c.setStrokeWidth(Settings.CIRCLE_BORDER);
        
        return c;
	}
	
	public SafeShape create(final Color fill) {
        return new SafeShape(createUnsafe(fill));
	}
	
	public SafeShape createOpaque(final Color fill) {
		Circle c = createUnsafe(fill);
		c.setOpacity(0.7);
		return new SafeShape(c);
	}
	
	public void dispose() {
		//Nothing to dispose
	}
}
