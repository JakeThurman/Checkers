public class CheckerboardRenderer implements Renderer {
    public ReadOnlyPositionedNodes render(final SafeSceneInteraction scene, Runnable rerender) {
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
        Messages       msgs         = new Messages();
        ButtonFactory  bttnFactory  = new ButtonFactory();
        CleanupHandler endGameClean = new CleanupHandler(ctm, data, ci, msgs, bttnFactory);       
        
        // Initialize the status bar
        GameStatusBar statusBar = new GameStatusBar(
            msgs, 
            ctm, 
            new PlayAgainHandler(endGameClean, rerender),
            bttnFactory);
                
        return new PositionedNodes()
        	.setCenter(data.visual)
        	.setBottom(statusBar.getNode());
    }
}
