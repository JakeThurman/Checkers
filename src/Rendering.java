public class Rendering  {    
    public static ReadOnlyPositionedNodes renderCheckerboard(final SafeSceneInteraction scene) {
        final CheckersTurnManager ctm  = new CheckersTurnManager();
        final Checkerboard        data = new Checkerboard(ctm);
        
        final CheckerboardInitialization ci = new CheckerboardInitialization(
    		new CircleFactory(), 
    		new CheckerInteractionManager(
    			scene, 
    			new SelectionManager(),
    			ctm));
        
        ci.initialize(data);
        
        // Init the status bar
        GameStatusBar statusBar = new GameStatusBar(
            new Messages(),
        	ctm);
                
        return new PositionedNodes()
        	.setCenter(data.visual)
        	.setBottom(statusBar.getNode());
    }
}