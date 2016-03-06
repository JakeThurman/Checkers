package jakethurman.games.checkers.components;

import jakethurman.foundation.CleanupHandler;
import jakethurman.components.factories.ButtonFactory;
import jakethurman.components.factories.ShapeFactory;
import jakethurman.components.factories.TextFactory;
import jakethurman.components.PositionedNodes;
import jakethurman.components.ReadOnlyPositionedNodes;
import jakethurman.components.SafeSceneInteraction;
import jakethurman.games.PlayAgainHandler;
import jakethurman.games.Renderer;
import jakethurman.games.checkers.CheckerInteractionManager;
import jakethurman.games.checkers.CheckersTurnManager;
import jakethurman.games.checkers.Messages;
import jakethurman.games.checkers.SelectionManager;
import jakethurman.games.checkers.Settings;

public class CheckerboardRenderer implements Renderer {	
    public ReadOnlyPositionedNodes render(final SafeSceneInteraction scene, Runnable rerender) {
        final CheckersTurnManager ctm  = new CheckersTurnManager();
        final Checkerboard        data = new Checkerboard(ctm);
        
        final CheckerboardInitialization ci = new CheckerboardInitialization(
    		new ShapeFactory(new Settings()), 
    		new CheckerInteractionManager(
    			scene, 
    			new SelectionManager(),
    			ctm));
        
        ci.initialize(data);
        
        // Initialize the status bar's dependencies 
        Messages       msgs         = new Messages();
        ButtonFactory  bttnFactory  = new ButtonFactory();
        TextFactory    textFactory  = new TextFactory();
        CleanupHandler endGameClean = new CleanupHandler(ctm, data, ci, msgs, bttnFactory, textFactory);       
        
        // Initialize the status bar
        GameStatusBar statusBar = new GameStatusBar(
            msgs, 
            ctm, 
            new PlayAgainHandler(endGameClean, rerender),
            bttnFactory,
            textFactory);
                
        return new PositionedNodes()
        	.setCenter(data.visual)
        	.setBottom(statusBar.getNode());
    }
}
