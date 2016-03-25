package jakethurman.games.checkers.components;

import java.util.function.Consumer;
import jakethurman.foundation.CleanupHandler;
import jakethurman.components.factories.ButtonFactory;
import jakethurman.components.factories.ChartFactory;
import jakethurman.components.factories.ShapeFactory;
import jakethurman.components.factories.TextFactory;
import jakethurman.components.PositionedNodes;
import jakethurman.components.ReadOnlyPositionedNodes;
import jakethurman.components.SafeScene;
import jakethurman.games.EndGameHandler;
import jakethurman.games.Renderer;
import jakethurman.games.checkers.CheckerCellValidator;
import jakethurman.games.checkers.CheckerInteractionManager;
import jakethurman.games.checkers.CheckersTurnManager;
import jakethurman.games.checkers.Messages;
import jakethurman.games.checkers.SelectionManager;
import jakethurman.games.checkers.Settings;

public class CheckerboardRenderer implements Renderer {	
    @Override
	public ReadOnlyPositionedNodes render(final SafeScene scene, final Runnable rerender, final Consumer<SafeScene> setScene) {
        final Settings            settings = new Settings();
        final CheckersTurnManager ctm      = new CheckersTurnManager(settings);
        final Checkerboard        data     = new Checkerboard(new CheckerCellValidator(settings), ctm, settings);
        
        final CheckerboardInitialization ci = new CheckerboardInitialization(
    		new ShapeFactory(settings), 
    		new CheckerInteractionManager(
    			scene, 
    			new SelectionManager(settings),
    			ctm), 
    		settings);
        
        ci.initialize(data);
        
        // Initialize the status bar's dependencies 
        CleanupHandler endGameClean = new CleanupHandler(ctm, data, ci);    
        Messages       msgs         = new Messages(settings);
        ButtonFactory  bttnFactory  = new ButtonFactory(scene);
        TextFactory    textFactory  = new TextFactory();   
        
        // Initialize the status bar
        GameStatusBar statusBar = new GameStatusBar(
            msgs, 
            ctm, 
            new EndGameHandler(
            	endGameClean, 
            	new CheckersStatsGenerator(
            		ctm, 
            		msgs,
            		new ChartFactory()), 
            	msgs, 
            	bttnFactory, 
            	textFactory, 
            	rerender, 
            	setScene, 
            	scene),
            bttnFactory,
            textFactory);
                
        return new PositionedNodes()
        	.setCenter(data.getNode())
        	.setBottom(statusBar.getNode());
    }
}
