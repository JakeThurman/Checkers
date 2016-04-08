package jakethurman.games.checkers.components;

import jakethurman.foundation.CleanupHandler;
import jakethurman.foundation.Disposable;
import jakethurman.components.factories.ButtonFactory;
import jakethurman.components.factories.TextFactory;
import jakethurman.components.PositionedNodes;
import jakethurman.components.SafeBorderPane;
import jakethurman.components.SafeNode;
import jakethurman.components.SafeText;
import jakethurman.games.EndGameHandler;
import jakethurman.games.GlobalSettings;
import jakethurman.games.checkers.CheckersTurnManager;
import jakethurman.games.checkers.Messages;
import jakethurman.games.checkers.ScoreInfo;
import jakethurman.games.checkers.Settings;

public class GameStatusBar implements Disposable {
	private final CheckersTurnManager turnManager;
	private final EndGameHandler      endGameHandler;
	private final ButtonFactory       buttonFactory;
	private final Messages            msgs;
	private final CleanupHandler      cleanup;
	private final Settings            settings;
	
	private final SafeBorderPane parent;
	private final SafeText       score;
	private final SafeText       turn;
	
	public GameStatusBar(Messages msgs, Settings settings, CheckersTurnManager turnManager, EndGameHandler endGameHandler, ButtonFactory buttonFactory, TextFactory textFactory) {
		this.turnManager    = turnManager;
		this.endGameHandler = endGameHandler;
		this.buttonFactory  = buttonFactory;
		this.settings       = settings;
		this.msgs           = msgs;
		this.cleanup        = new CleanupHandler(turnManager, endGameHandler, buttonFactory, msgs, textFactory);

		this.score          = textFactory.createLeftAlign();
		this.turn           = textFactory.createCenteredBold();
		this.parent         = new SafeBorderPane();
		
		init();
	}
	
	//Holds basic initialization logic
	public void init() {
		updateText(turnManager.getCurrentScore());
		turnManager.addOnChangeHandler((s) -> updateText(s));
		
		parent.setPadding(4);
		
		parent.setChildren(new PositionedNodes()
			.setCenter(this.turn)
			.setBottom(this.score));
	}
	
	private void updateText(ScoreInfo currScore) {
		// Check for a win first.
		if (currScore.player1.getPiecesRemaining() == 0 || currScore.player2.getPiecesRemaining() == 0) {
			turnManager.gameDidEnd();
			handleWin(currScore.player2.getPiecesRemaining() == 0);
			return;
		}

		String scoreText  = msgs.getScoreStatus(
				currScore.player1.getPiecesRemaining(), currScore.player2.getPiecesRemaining(), 
				currScore.player1.getKingCount(), currScore.player2.getKingCount());
		
		String playerText = msgs.getTurnStatus(currScore.currentPlayerIsPlayer1);
		
		this.score.setText(scoreText);
		this.turn.setText(playerText);
	}
	
	private void handleWin(boolean isPlayer1) {
		String msg = msgs.getWinnerMessage(isPlayer1);
		this.score.setText("");
		this.turn.setText(msg);
		
		SafeNode playAgain = buttonFactory.create(msgs.getPlayAgain(), endGameHandler::playAgain);	
		SafeNode gameStats = buttonFactory.create(msgs.getViewGameStats(), () -> endGameHandler.viewStats(settings.getSaveFileLocation()));
		
		SafeBorderPane bottom = new SafeBorderPane();
		bottom.setChildren(new PositionedNodes()
			.setLeft(GlobalSettings.IS_DEBUG ? gameStats : null)
			.setRight(playAgain));
		
		parent.setPadding(10);
		parent.setChildren(new PositionedNodes()
			.setCenter(this.turn)
			.setBottom(bottom));
	}
	
	public SafeNode getNode() {
		return parent;
	}

	@Override
	public void dispose() {
		cleanup.dispose();
	}
}
