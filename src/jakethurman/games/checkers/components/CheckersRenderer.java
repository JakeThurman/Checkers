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

public class CheckersRenderer implements Renderer {
	private final boolean isVsAI;
	private final Settings settings;
	private final Messages msgs; 
	
	public CheckersRenderer(boolean isVsAI) {
		this.isVsAI  = isVsAI;
		this.settings = new Settings();
		this.msgs     = new Messages(settings);
	}
	
    @Override
	public void render(final Runnable rerender, final Consumer<SafeScene> setScene) {
    	final SafeBorderPane      content  = new SafeBorderPane();
    	final SafeScene           scene    = new SafeScene(content);
        final CheckersTurnManager ctm      = new CheckersTurnManager(settings, this.isVsAI);
        final Checkerboard        data     = new Checkerboard(ctm, settings);
        
        final ShapeFactory shapeFactory = new ShapeFactory(settings);
        final CheckersInitialization ci = new CheckersInitialization(
        	shapeFactory,
        	new GridHelpers(shapeFactory),
    		new CheckerInteractionManager(
    			scene, 
    			new SelectionManager(settings),
    			ctm,
    			this.isVsAI), 
    		settings);
        
        ci.initialize(data);
        
        // Initialize the status bar's dependencies
        ButtonFactory bttnFactory = new ButtonFactory(scene);
        TextFactory   textFactory = new TextFactory();   
        
        // Initialize the status bar
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
        
        CheckersBot bot = new CheckersBot(data, ctm);
                
        //Now actually handle content rendering
        content.setChildren(new PositionedNodes()
        	.setCenter(data.getNode())
        	.setBottom(statusBar.getNode())
        	.setTop(maybeGetDebugBar(bot, bttnFactory, this.isVsAI)));
        
        setScene.accept(scene);
        
        
        //Initialize the AI Player if we need be
        if (this.isVsAI)
        	bot.init(false);
    }
        
	private static SafeNode maybeGetDebugBar(CheckersBot bot, ButtonFactory bttnFactory, boolean vsAI) {
		//Don't render anything if we're not in testing mode
		if (!GlobalSettings.IS_DEBUG)
			return SafeNode.NONE;
		
		BooleanMemory gameEnded = new BooleanMemory(false);
		
		//When debugging, we want a button to set both players to AI players
		return bttnFactory.create("End Game (Double AI)", () -> {
			// Only end the game once dummy!
			if (gameEnded.get()) return;
			
			bot.init(true);
			//Only initialize player2 AI is we aren't already doing so.
			if (!vsAI) bot.init(false);
			
			gameEnded.set(true);
		});
	}

	@Override
	public String getTitle() {
		return msgs.getGameTitle(this.isVsAI);
	}
}
