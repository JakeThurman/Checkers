package jakethurman.games.checkers.components;

import java.util.function.Consumer;
import jakethurman.foundation.CleanupHandler;
import jakethurman.components.factories.ButtonFactory;
import jakethurman.components.factories.ChartFactory;
import jakethurman.components.factories.ListViewFactory;
import jakethurman.components.factories.ShapeFactory;
import jakethurman.components.factories.TextFactory;
import jakethurman.components.PositionedNodes;
import jakethurman.components.SafeBorderPane;
import jakethurman.components.SafeNode;
import jakethurman.components.SafeScene;
import jakethurman.components.helpers.GridHelpers;
import jakethurman.games.Difficulty;
import jakethurman.games.EndGameHandler;
import jakethurman.games.GlobalSettings;
import jakethurman.games.Renderer;
import jakethurman.games.checkers.CheckerInteractionManager;
import jakethurman.games.checkers.CheckersBot;
import jakethurman.games.checkers.CheckersTurnManager;
import jakethurman.games.checkers.Messages;
import jakethurman.games.checkers.SelectionManager;
import jakethurman.games.checkers.Settings;
import jakethurman.util.BooleanMemory;
import jakethurman.util.FileHandler;

/*
 * Handles all of the rendering, and dependencies of a checkers game.
 */
public class CheckersRenderer implements Renderer {
	private final Settings settings;
	private final Messages msgs; 
	
	public CheckersRenderer(Difficulty aiDifficulty) {
		// Store all out our local variables
		// and common dependencies.
		this.settings = new Settings(aiDifficulty);
		this.msgs     = new Messages(settings);
	}
	
	/*
	 * Renders a new checkers game
	 * 
	 * @param rerender: Should tell the caller to create a new instance of this.
	 * @param setScene: Sets the current scene.
	 */
    @Override
	public void render(final Runnable rerender, final Consumer<SafeScene> setScene) {
    	// Create all of the classes our content depends on as well as the actual content classes.
    	final SafeBorderPane      content  = new SafeBorderPane();
    	final SafeScene           scene    = new SafeScene(content);
        final CheckersTurnManager ctm      = new CheckersTurnManager(settings);
        final Checkerboard        data     = new Checkerboard(ctm, settings); // Create the initial checker board
        
        // Instantiate more dependencies
        final ShapeFactory shapeFactory = new ShapeFactory(settings);
        final CheckersInitialization ci = new CheckersInitialization(
        	shapeFactory,
        	new GridHelpers(shapeFactory),
    		new CheckerInteractionManager(
    			scene, 
    			new SelectionManager(settings),
    			ctm,
    			settings), 
    		settings);
        
        // Instantiate the checker board.
        ci.initialize(data);
        
        // Instantiate the status bar's dependencies
        ButtonFactory bttnFactory = new ButtonFactory(scene);
        TextFactory   textFactory = new TextFactory();   
        
        // Instantiate the status bar
        GameStatusBar statusBar = new GameStatusBar(
            msgs,
            settings,
            ctm, 
            new EndGameHandler(
            	new CleanupHandler(ctm, data, ci), 
            	new ListViewFactory(),
            	new FileHandler((e) -> e.printStackTrace()),
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
        
        // Create an new checkers AI player
        CheckersBot bot = new CheckersBot(data, ctm, settings);
                
        //Now actually handle content rendering
        content.setChildren(new PositionedNodes()
        	.setCenter(data.getNode())
        	.setBottom(statusBar.getNode())
        	.setTop(maybeGetDebugBar(bot, bttnFactory, settings))); //The debug bar is a helper used while adding some features
        
        setScene.accept(scene);
        
        //Initialize the AI Player if need be
        if (settings.getDifficulty() != Difficulty.HUMAN)
        	bot.init(false);
    }
        
	private static SafeNode maybeGetDebugBar(CheckersBot bot, ButtonFactory bttnFactory, Settings settings) {
		//Don't render anything if we're not in testing mode
		if (!GlobalSettings.IS_DEBUG)
			return SafeNode.NONE;
		
		// We're using the boolean memory class a 
		// total hack around the restriction of 
		// setting values outside of lambda scope.
		BooleanMemory gameEnded = new BooleanMemory(false);
		
		//When debugging, we want a button to set both players to AI players
		return bttnFactory.create("End Game (Double AI)", () -> {			
			// Only end the game once dummy!
			if (gameEnded.get()) return;
			
			// Change the difficulty to to hard in order
			// to move the game as fast as possible
			settings.DEBUGchangeDifficulty(Difficulty.HARD);
			
			bot.init(true);
			//Only initialize player2 AI is we aren't already doing so.
			if (settings.getDifficulty() == Difficulty.HUMAN) bot.init(false);
			
			// Remember that we have done this
			gameEnded.set(true);
		});
	}

	@Override
	// Gets the name of the game!
	public String getTitle() { 
		return msgs.getGameTitle();
	}
}
