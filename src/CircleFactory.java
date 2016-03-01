import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

public class CircleFactory {
    private final int CIRCLE_RADIUS,
                      BORDER_WIDTH;
        
    private final CheckerboardInteractionManager interactions;
    
    public CircleFactory(int circleRadius, int borderWidth, CheckerboardInteractionManager interactions) {
        this.CIRCLE_RADIUS = circleRadius;
        this.BORDER_WIDTH  = borderWidth;
        this.interactions = interactions;
    }
    	
	public Circle create(Color fill, Color border) {
		//Draw the circle
		Circle c = new Circle(CIRCLE_RADIUS, fill);
		c.setStroke(border);
        c.setStrokeWidth(BORDER_WIDTH);
        
        // Set the built in events
        c.setOnMouseEntered((e) -> this.interactions.onClicleMouseOver(e));
        c.setOnMouseExited((e) -> this.interactions.onClicleMouseOut(e));
        c.setOnMouseClicked((e) -> this.interactions.onCircleClick(e));
        
        return c;
	}
}
