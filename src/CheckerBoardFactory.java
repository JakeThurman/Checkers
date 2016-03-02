import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class CheckerBoardFactory  {    
    public static Pane render(SafeSceneInteraction scene) {
        final GridPane         visual        = new GridPane();
        final SelectionManager sm            = new SelectionManager();
        final CircleFactory    circleFactory = new CircleFactory(new CircleInteractionFactory(scene, sm)); 

        CheckerboardInitialization.initialize(visual, circleFactory);
                
        return visual;
    }
}