package jakethurman.games.checkers.components;

import java.util.function.Consumer;
import jakethurman.foundation.CleanupHandler;
import jakethurman.components.factories.ButtonFactory;
import jakethurman.components.factories.ChartFactory;
import jakethurman.components.factories.ShapeFactory;
import jakethurman.components.factories.TextFactory;
import jakethurman.components.PositionedNodes;
import jakethurman.components.ReadOnlyPositionedNodes;
import jakethurman.components.SafeNode;
import jakethurman.components.SafeScene;
import jakethurman.games.EndGameHandler;
import jakethurman.games.GridHelpers;
import jakethurman.games.Renderer;
import jakethurman.games.checkers.CheckerCellValidator;
import jakethurman.games.checkers.CheckerInteractionManager;
import jakethurman.games.checkers.CheckersTurnManager;
import jakethurman.games.checkers.Messages;
import jakethurman.games.checkers.SelectionManager;
import jakethurman.games.checkers.Settings;

public class CheckersRenderer implements Renderer {	
    @Override
	public ReadOnlyPositionedNodes render(final SafeScene scene, final Runnable rerender, final Consumer<SafeScene> setScene) {
        final Settings            settings = new Settings();
        final CheckersTurnManager ctm      = new CheckersTurnManager(settings);
        final Checkerboard        data     = new Checkerboard(new CheckerCellValidator(settings), ctm, settings);
        
        final ShapeFactory shapeFactory = new ShapeFactory(settings);
        final CheckersInitialization ci = new CheckersInitialization(
        	shapeFactory,
        	new GridHelpers(shapeFactory),
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
            settings,
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
        	.setBottom(statusBar.getNode())
        	.setTop(maybeGetDebugBar(settings, ctm, bttnFactory));
    }

	private static SafeNode maybeGetDebugBar(Settings settings, CheckersTurnManager ctm, ButtonFactory bttnFactory) {
		if (!settings.isDebug())
			return SafeNode.NONE;
		
		return bttnFactory.create("End Game", ctm::hackPlayer2ToZeroPieces);
	}
}
