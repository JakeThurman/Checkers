package jakethurman.games;

import java.io.File;
import java.util.ArrayList;
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
		ArrayList<String> savedHighScores = new ArrayList<>();
		fileHandler.readFile(new File(saveFileLocation), 
			() -> {}, 
			(s) -> savedHighScores.add(s));
		
		SafeNode back  = buttonFactory.create(msgs.getBackButton(), () -> setScene.accept(gameScene));
		SafeNode chart = statsGen.getChart(StatChartType.PIECES_OVER_TIME);
		SafeNode text  = textFactory.createLeftAlign(statsGen.getStatusText());
		
		SafeBorderPane pane   = new SafeBorderPane();
		SafeBorderPane bottom = new SafeBorderPane();
		SafeBorderPane right  = new SafeBorderPane();
		
		bottom.setChildren(new PositionedNodes()
			.setRight(back)
			.setCenter(text));
		
		right.setChildren(new PositionedNodes()
			.setTop(textFactory.createCenteredBold(msgs.getHighScoreListHeader()))
			.setCenter(lvf.create(savedHighScores)));
		
		pane.setChildren(new PositionedNodes()
			.setRight(right)
			.setCenter(chart)
			.setBottom(bottom));
		
		setScene.accept(new SafeScene(pane));
	}

	@Override
	public void dispose() {
		cleanup.dispose();
	}
}
