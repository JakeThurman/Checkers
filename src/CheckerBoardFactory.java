import javafx.scene.layout.Pane;

public class CheckerBoardFactory  {    
    public static Pane render(final SafeSceneInteraction scene) {
        final Checkerboard data = new Checkerboard();
        
        final CheckerboardInitialization ci = new CheckerboardInitialization(
    		new CircleFactory(), 
    		new CheckerInteractionManager(
    			scene, 
    			new SelectionManager()));

        ci.initialize(data);
                
        return data.visual;
    }
}