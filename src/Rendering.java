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
        
        // Initialize the status bar's dependencies 
        Messages       msgs    = new Messages();
        CleanupHandler cleanup = new CleanupHandler(ctm, data, ci, msgs);       
        
        // Initialize the status bar
        GameStatusBar statusBar = new GameStatusBar(
            msgs,
            ctm,
            new PlayAgainHandler(cleanup));
                
        return new PositionedNodes()
        	.setCenter(data.visual)
        	.setBottom(statusBar.getNode());
    }
}