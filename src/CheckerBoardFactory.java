import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class CheckerBoardFactory  {    
    public static Pane render(SafeSceneInteraction scene) {
        final GridPane         visual        = new GridPane();
        final CircleFactory    circleFactory = new CircleFactory(new CircleInteractionFactory(scene, new SelectionManager())); 
        final Checkerboard     data          = CheckerboardInitialization.initialize(visual, circleFactory);
        
        return visual;
    }
}