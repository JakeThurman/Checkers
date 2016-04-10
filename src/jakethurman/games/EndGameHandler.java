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

public class EndGameHandler implements Disposable {
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

	public void playAgain() {
		cleanup.dispose();
		rerender.run();
	}
	
	public void viewStats(String saveFileLocation) {
		//TODO: Add a loading screen to show while waiting for the file.
		
		//Read all of the high scores from the high score file
		ArrayList<SimpleScoreData> savedHighScoreValues = new ArrayList<>();
		fileHandler.readFile(new File(saveFileLocation),
			() -> {}, 
			(s) -> savedHighScoreValues.add(SimpleScoreData.deserialize(s)));
		
		String[] highScores = new String[savedHighScoreValues.size()];
		int shsvSize = savedHighScoreValues.size();
		for (int i = 0; i < shsvSize; i++)
			highScores[i] = msgs.getHighScoreLine(savedHighScoreValues.get(i));
		
		//Create nodes
		SafeNode back  = buttonFactory.create(msgs.getBackButton(), () -> setScene.accept(gameScene));
		SafeNode chart = statsGen.getChart(StatChartType.PIECES_OVER_TIME);
		SafeNode text  = textFactory.createLeftAlign(statsGen.getStatusText());
		
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

	public void writeScore(String saveFileLocation, int scoreNumber, String name) {
		String newLine = new SimpleScoreData(System.currentTimeMillis(), scoreNumber, name).serialize();
		
		fileHandler.appendToFileOrCreate(Paths.get(saveFileLocation), Arrays.asList(newLine));
	}
}
