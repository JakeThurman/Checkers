import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class CheckerBoardFactory  {
  
    private static final int BOARD_SIZE    = 8,
                             SQUARE_SIZE   = 60,
                             NUM_PIECES    = 12,
                             CIRCLE_RADIUS = SQUARE_SIZE/2-6,
                             CIRCLE_BORDER = 2;
    
    public Pane render(CheckerboardInteractionManager interactions) {
        final GridPane                       checkerBoard  = new GridPane();
        final CircleFactory                  circleFactory = new CircleFactory(CIRCLE_RADIUS, CIRCLE_BORDER, interactions); 
        
        new CheckerboardInitialization(NUM_PIECES, BOARD_SIZE, SQUARE_SIZE)
            .initialize(checkerBoard, circleFactory);
        
        return checkerBoard;
    }
}