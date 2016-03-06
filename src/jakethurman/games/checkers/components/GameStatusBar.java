package jakethurman.games.checkers.components;

import jakethurman.foundation.CleanupHandler;
import jakethurman.foundation.Disposable;
import jakethurman.components.factories.ButtonFactory;
import jakethurman.components.factories.TextFactory;
import jakethurman.components.PositionedNodes;
import jakethurman.components.SafeBorderPane;
import jakethurman.components.SafeNode;
import jakethurman.components.SafeText;
import jakethurman.games.PlayAgainHandler;
import jakethurman.games.checkers.CheckersTurnManager;
import jakethurman.games.checkers.Messages;
import jakethurman.games.checkers.ScoreInfo;

public class GameStatusBar implements Disposable {
	private final CheckersTurnManager turnManager;
	private final PlayAgainHandler    playAgainHandler;
	private final ButtonFactory       buttonFactory;
	private final Messages            msgs;
	private final CleanupHandler      cleanup;
	
	private final SafeBorderPane parent;
	private final SafeText       score;
	private final SafeText       turn;
	
	public GameStatusBar(Messages msgs, CheckersTurnManager turnManager, PlayAgainHandler playAgain, ButtonFactory buttonFactory, TextFactory textFactory) {
		this.turnManager      = turnManager;
		this.playAgainHandler = playAgain;
		this.buttonFactory    = buttonFactory;
		this.msgs             = msgs;
		this.cleanup          = new CleanupHandler(turnManager, playAgain, buttonFactory, msgs, textFactory);

		this.score            = textFactory.createLeftAlign();
		this.turn             = textFactory.createCenteredBold();
		this.parent           = new SafeBorderPane();
		
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
	
	private void updateText(ScoreInfo score) {
		// Check for a win first.
		if (score.player1CheckersRemaining == 0 || score.player2CheckersRemaining == 0) {
			handleWin(score.player2CheckersRemaining == 0);
			return;
		}

		String currScore  = msgs.getScoreStatus(score.player1CheckersRemaining, score.player2CheckersRemaining, score.player1Kings, score.player2Kings);
		String currPlayer = msgs.getTurnStatus(score.currentPlayerIsPlayer1);
		
		this.score.setText(currScore);
		this.turn.setText(currPlayer);
	}
	
	private void handleWin(boolean isPlayer1) {
		String msg = msgs.getWinnerMessage(isPlayer1);
		this.score.setText("");
		this.turn.setText(msg);
		
		SafeNode playAgain = buttonFactory.create(msgs.getPlayAgain(), playAgainHandler);		
		
		parent.setPadding(10);
		parent.setChildren(new PositionedNodes()
			.setCenter(this.turn)
			.setRight(playAgain)
			.setBottom(this.score));
	}
	
	public SafeNode getNode() {
		return parent;
	}
	
	public void dispose() {
		cleanup.dispose();
	}
}
