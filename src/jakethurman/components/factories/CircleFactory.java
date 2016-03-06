package jakethurman.components.factories;

import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import jakethurman.components.SafePaint;
import jakethurman.components.SafeShape;
import jakethurman.foundation.Disposable;
import jakethurman.games.checkers.Settings;

public class CircleFactory implements Disposable {    
	private Circle createUnsafe(final SafePaint fill) {
		Circle c = new Circle(Settings.CIRCLE_RADIUS, fill.getUnsafe());
		c.setStroke(Color.WHITE);
        c.setStrokeWidth(Settings.CIRCLE_BORDER);
        
        return c;
	}
	
	public SafeShape create(final SafePaint fill) {
        return new SafeShape(createUnsafe(fill));
	}
	
	public SafeShape createOpaque(final SafePaint fill) {
		Circle c = createUnsafe(fill);
		c.setOpacity(0.7);
		return new SafeShape(c);
	}
	
	public void dispose() {
		//Nothing to dispose
	}
}
