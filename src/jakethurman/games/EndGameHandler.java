package jakethurman.games;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;
import jakethurman.foundation.Disposable;
import jakethurman.util.FileHandler;
import jakethurman.components.PositionedNodes;
import jakethurman.components.SafeBorderPane;
import jakethurman.components.SafeNode;
import jakethurman.components.SafeScene;
import jakethurman.components.factories.ButtonFactory;
import jakethurman.components.factories.ListViewFactory;
import jakethurman.components.factories.TextFactory;
import jakethurman.foundation.CleanupHandler;

/* 
 * Does stuff that needs to be done when a game completes 
 */
public class EndGameHandler implements Disposable {
	// Dependencies
	private final CleanupHandler  cleanup;
	private final Runnable        rerender;
	private final ButtonFactory   buttonFactory;
	private final TextFactory     textFactory;
	private final GameMessages    msgs;
	private final StatsGenerator  statsGen;
	private final FileHandler     fileHandler;
	private final ListViewFactory lvf;
	private final Consumer<SafeScene> setScene;
	private final SafeScene           gameScene;
	
	// C'tor
	public EndGameHandler(CleanupHandler cleanup, ListViewFactory lvf, FileHandler fileHandler, StatsGenerator statsGen, GameMessages msgs, ButtonFactory buttonFactory, TextFactory textFactory, Runnable rerender, Consumer<SafeScene> setScene, SafeScene gameScene) {
		this.buttonFactory = buttonFactory;
		this.textFactory   = textFactory;
		this.msgs          = msgs;
		this.statsGen      = statsGen;
		this.fileHandler   = fileHandler;
		this.lvf           = lvf;
		this.cleanup       = new CleanupHandler(cleanup, lvf, buttonFactory, textFactory, msgs, statsGen, fileHandler);
		this.setScene      = setScene;
		this.gameScene     = gameScene;
		this.rerender      = rerender;
	}
	
	// Play again
	public void playAgain() {
		cleanup.dispose();
		rerender.run();
	}
	
	// Opens a statistics view
	public void viewStats(String saveFileLocation, int finalScore) {		
		//Read all of the high scores from the high score file
		ArrayList<SimpleScoreData> savedHighScoreValues = new ArrayList<>();
		fileHandler.readFile(new File(saveFileLocation), () -> {}, 
			(s) -> savedHighScoreValues.add(SimpleScoreData.deserialize(s)));
		
		//Convert high scores to an array of converted messages.
		ArrayList<String> highScores = new ArrayList<>();
	
		savedHighScoreValues.stream()
			.sorted((s1, s2) -> Integer.compare(s2.score, s1.score)) // Sort the saved-score objects by score
			.limit(20)                              // Take only the top 20 high scores
			.map(val -> msgs.getHighScoreLine(val)) // Map each saved-score to an appropriate display message
			.forEach((str) -> highScores.add(str)); // Add each item to the ArrayList
		
		//Create nodes
		SafeNode back  = buttonFactory.create(msgs.getBackButton(), () -> setScene.accept(gameScene));
		SafeNode chart = statsGen.getChart(StatChartType.PIECES_OVER_TIME);
		SafeNode text  = textFactory.createLeftAlign(statsGen.getStatsText(finalScore));
		
		SafeNode highScoreListHeader = textFactory.createCenteredBold(msgs.getHighScoreListHeader());
		SafeNode highScoreList       = lvf.create(highScores);
		
		//Create containers
		SafeBorderPane pane   = new SafeBorderPane();
		SafeBorderPane bottom = new SafeBorderPane();
		SafeBorderPane right  = new SafeBorderPane();
		
		//Add nodes to containers
		bottom.setChildren(new PositionedNodes()
			.setRight(back)
			.setCenter(text));
		
		right.setChildren(new PositionedNodes()
			.setTop(highScoreListHeader)
			.setCenter(highScoreList));
		
		pane.setChildren(new PositionedNodes()
			.setRight(right)
			.setCenter(chart)
			.setBottom(bottom));
		
		//Set the scene to the container
		setScene.accept(new SafeScene(pane));
	}

	@Override
	public void dispose() {
		cleanup.dispose();
	}

	// Writes the score from a game to a save file.
	public void writeScore(String saveFileLocation, int scoreNumber, String name) {		
		// Get the serialized data
		String newLine = new SimpleScoreData(System.currentTimeMillis(), scoreNumber, name).serialize();
		
		// Post to the file
		fileHandler.appendToFileOrCreate(Paths.get(saveFileLocation), Arrays.asList(newLine));
	}
}
