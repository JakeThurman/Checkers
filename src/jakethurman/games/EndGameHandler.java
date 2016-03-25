package jakethurman.games;

import jakethurman.foundation.Disposable;

import java.util.function.Consumer;

import jakethurman.components.PositionedNodes;
import jakethurman.components.SafeBorderPane;
import jakethurman.components.SafeNode;
import jakethurman.components.SafeScene;
import jakethurman.components.SafeText;
import jakethurman.components.factories.ButtonFactory;
import jakethurman.components.factories.TextFactory;
import jakethurman.foundation.CleanupHandler;

public class EndGameHandler implements Disposable {
	private final CleanupHandler     cleanup;
	private final Runnable           rerender;
	private final ButtonFactory      buttonFactory;
	private final TextFactory        textFactory;
	private final GameMessages       msgs;
	private final StatsGenerator     statsGen;
	
	private final Consumer<SafeScene> setScene;
	private final SafeScene           gameScene;
	
	public EndGameHandler(CleanupHandler cleanup, StatsGenerator statsGen, GameMessages msgs, ButtonFactory buttonFactory, TextFactory textFactory, Runnable rerender, Consumer<SafeScene> setScene, SafeScene gameScene) {
		this.buttonFactory = buttonFactory;
		this.textFactory   = textFactory;
		this.msgs          = msgs;
		this.statsGen      = statsGen;
		this.cleanup       = new CleanupHandler(cleanup, buttonFactory, textFactory, msgs, statsGen);
		this.setScene      = setScene;
		this.gameScene     = gameScene;
		this.rerender      = rerender;
	}

	public void playAgain() {
		cleanup.dispose();
		rerender.run();
	}
	
	public void viewStats() {
		SafeNode back  = buttonFactory.create(msgs.getBackButton(), () -> setScene.accept(gameScene));
		SafeNode chart = statsGen.getChart(StatChartType.PIECES_OVER_TIME);
		SafeNode text  = textFactory.createLeftAlign(statsGen.getStatusText());
		
		SafeBorderPane pane   = new SafeBorderPane();
		SafeBorderPane bottom = new SafeBorderPane();
		
		bottom.setChildren(new PositionedNodes()
			.setRight(back)
			.setCenter(text));
		
		pane.setChildren(new PositionedNodes()
			.setCenter(chart)
			.setBottom(bottom));
		
		setScene.accept(new SafeScene(pane));
	}

	@Override
	public void dispose() {
		cleanup.dispose();
	}
}
