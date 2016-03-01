import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

public class CircleFactory {        
    private final CircleInteractionFactory interactionFactory;
    
    public CircleFactory(final CircleInteractionFactory interactionFactory) {
        this.interactionFactory = interactionFactory;
    }
    	
	public Circle create(final Color fill, final Color border) {
		// Draw the circle
		Circle c = new Circle(Settings.CIRCLE_RADIUS, fill);
		c.setStroke(border);
        c.setStrokeWidth(Settings.CIRCLE_BORDER);
        
        // Set the built in events
        CircleInteractionManager cim = this.interactionFactory.create(c);
        c.setOnMouseEntered((e) -> cim.onClicleMouseOver(e));
        c.setOnMouseExited((e) -> cim.onClicleMouseOut(e));
        c.setOnMouseClicked((e) -> cim.onCircleClick(e));
        
        return c;
	}
}
