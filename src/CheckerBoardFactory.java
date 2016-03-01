import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.Cursor;

public class CheckerBoardFactory  {
  
    private static final int BOARD_SIZE    = 8,
                             SQUARE_SIZE   = 60,
                             NUM_PIECES    = 12,
                             CIRCLE_RADIUS = SQUARE_SIZE/2-6,
                             CIRCLE_BORDER = 2;
    
    public Pane render(SafeSceneInteraction scene) {
        final GridPane   checkerBoard = new GridPane();
                
        CircleFactory circleFactory = new CircleFactory(CIRCLE_RADIUS, CIRCLE_BORDER)
            .setMouseOver((e) -> {
            	scene.setCursor(Cursor.HAND); // Change cursor to hand
            })
            .setMouseOut((e) -> {
            	scene.setCursor(Cursor.DEFAULT); // Change cursor back to the normal one
            })
            .setClicked((e) -> {
            	
            }); 
        
        new CheckerboardInitialization(NUM_PIECES, BOARD_SIZE, SQUARE_SIZE)
            .initialize(checkerBoard, circleFactory);
        
        return checkerBoard;
    }
}