import javafx.scene.layout.Pane;

public class Rendering  {    
    public static Pane renderCheckerboard(final SafeSceneInteraction scene) {
        final CheckersTurnManager ctm  = new CheckersTurnManager();
        final Checkerboard        data = new Checkerboard(ctm);
        
        final CheckerboardInitialization ci = new CheckerboardInitialization(
    		new CircleFactory(), 
    		new CheckerInteractionManager(
    			scene, 
    			new SelectionManager(),
    			ctm));

        // Temp player handling for user
        ctm.setOnChange((player1, checkersRemaining) -> System.out.println("It's " + (player1 ? "red" : "black") + "s turn. " + (player1 ? "Red" : "Black") + " has " + checkersRemaining + " checkers remaining."));
        
        ci.initialize(data);
                
        return data.visual;
    }
}