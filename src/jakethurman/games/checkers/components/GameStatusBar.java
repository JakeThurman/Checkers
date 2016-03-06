package jakethurman.games.checkers.components;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import jakethurman.foundation.CleanupHandler;
import jakethurman.foundation.Disposable;
import jakethurman.components.ButtonFactory;
import jakethurman.components.SafeNode;
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
	
	private final BorderPane          parent;
	private final Text                score;
	private final Text                turn;
	
	public GameStatusBar(Messages msgs, CheckersTurnManager turnManager, PlayAgainHandler playAgain, ButtonFactory buttonFactory) {
		this.turnManager      = turnManager;
		this.playAgainHandler = playAgain;
		this.buttonFactory    = buttonFactory;
		this.msgs             = msgs;
		this.cleanup          = new CleanupHandler(turnManager, playAgain, buttonFactory, msgs);
		
		this.score            = new Text();
		this.turn             = new Text();
		this.parent           = new BorderPane();
		
		init();
	}
	
	//Holds basic initialization logic
	public void init() {
		updateText(turnManager.getCurrentScore());		
		turnManager.addOnChangeHandler((s) -> updateText(s));
		
		turn.setTextAlignment(TextAlignment.CENTER);
		turn.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
		score.setTextAlignment(TextAlignment.LEFT);
		score.setFont(Font.font("Tahoma", 20));
		
		parent.setPadding(new Insets(4));
		
		parent.setCenter(this.turn);
		parent.setBottom(this.score);
	}
	
	private void updateText(ScoreInfo score) {
		// Check for a win first.
		if (score.player1CheckersRemaining == 0 || score.player2CheckersRemaining == 0) {
			handleWin(score.player2CheckersRemaining == 0);
			return;
		}

		String currScore  = msgs.getScoreStatus(score.player1CheckersRemaining, score.player2CheckersRemaining);
		String currPlayer = msgs.getTurnStatus(score.currentPlayerIsPlayer1);
		
		this.score.setText(currScore);
		this.turn.setText(currPlayer);
	}
	
	private void handleWin(boolean isPlayer1) {
		String msg = msgs.getWinnerMessage(isPlayer1);
		this.score.setText("");
		this.turn.setText(msg);
		
		Node playAgain = buttonFactory.create(msgs.getPlayAgain(), playAgainHandler);		
		
		parent.setPadding(new Insets(10));
		parent.setRight(playAgain);
	}
	
	public SafeNode getNode() {
		return new SafeNode(parent);
	}
	
	public void dispose() {
		cleanup.dispose();
	}
}
