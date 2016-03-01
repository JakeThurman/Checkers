import javafx.scene.shape.Circle;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;

public class CircleFactory {
    private final int CIRCLE_SIZE,
                      BORDER_WIDTH;
    
    private EventHandler<? super MouseEvent> mouseOver = null,
                                             mouseOut = null,
                                             clicked = null;
    
    public CircleFactory(int circleSize, int borderWidth) {
        this.CIRCLE_SIZE = circleSize;
        this.BORDER_WIDTH = borderWidth;
    }
    
	public CircleFactory setMouseOut(EventHandler<? super MouseEvent> mouseOut) {
		this.mouseOut = mouseOut;
		return this; // Allow this method to be chained
	}
	
	public CircleFactory setMouseOver(EventHandler<? super MouseEvent> mouseOver) {
		this.mouseOver = mouseOver;
		return this; // Allow this method to be chained
	}
	
	public CircleFactory setClicked(EventHandler<? super MouseEvent> clicked) {
		this.clicked = clicked;
		return this; // Allow this method to be chained
	}
	
	public Circle create(Color fill, Color border) {
		//Draw the circle
		Circle c = new Circle(CIRCLE_SIZE, fill);
		c.setStroke(border);
        c.setStrokeWidth(BORDER_WIDTH);
        
        // Set the built in events
        c.setOnMouseEntered(this.mouseOver);
        c.setOnMouseExited(this.mouseOut);
        c.setOnMouseClicked(this.clicked);
        
        return c;
	}
}
