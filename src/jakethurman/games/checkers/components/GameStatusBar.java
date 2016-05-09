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
import jakethurman.games.checkers.CheckersTurnManager;
import jakethurman.games.checkers.Messages;
import jakethurman.games.checkers.PlayerInfo;
import jakethurman.games.checkers.ScoreInfo;
import jakethurman.games.checkers.Settings;

/* Handles the rendering of the checkers game status bar */
public class GameStatusBar implements Disposable {
	// Dependencies
	private final CheckersTurnManager turnManager;
	private final EndGameHandler      endGameHandler;
	private final ButtonFactory       buttonFactory;
	private final Messages            msgs;
	private final CleanupHandler      cleanup;
	private final Settings            settings;
	
	// Content
	private final SafeBorderPane parent;
	private final SafeText       score;
	private final SafeText       turn;
	
	// Data
	private boolean inWinMode = false;
	
	// C'tor
	public GameStatusBar(Messages msgs, Settings settings, CheckersTurnManager turnManager, EndGameHandler endGameHandler, ButtonFactory buttonFactory, TextFactory textFactory) {
		// Store dependencies
		this.turnManager    = turnManager;
		this.endGameHandler = endGameHandler;
		this.buttonFactory  = buttonFactory;
		this.settings       = settings;
		this.msgs           = msgs;
		this.cleanup        = new CleanupHandler(turnManager, endGameHandler, buttonFactory, msgs, textFactory);

		// Create content nodes
		this.score          = textFactory.createLeftAlign();
		this.turn           = textFactory.createCenteredBold();
		this.parent         = new SafeBorderPane();
		
		// INITIALIZE!
		init();
	}
	
	//Holds basic initialization logic
	public void init() {
		// Set the initial text to the current score
		updateText(turnManager.getCurrentScore());
		// When the score changes, update the text to that score.
		turnManager.addOnChangeHandler((s) -> updateText(s));
		
		// Set some nice spacing :)
		parent.setPadding(4);
		
		// Add to the parent pane.
		parent.setChildren(new PositionedNodes()
			.setCenter(this.turn)
			.setBottom(this.score));
	}
	
	// Updates the text in the status bar to represent the score
	private void updateText(ScoreInfo currScore) {
		// Don't do anything if we're already in win mode.
		if (this.inWinMode) 
			return;
		
		// Check for a win first.
		if (currScore.player1.getPiecesRemaining() == 0 || currScore.player2.getPiecesRemaining() == 0) {
			// Tell the turn manager that the game ended
			turnManager.gameDidEnd();
			// Remember that we're in mode so we don't have to bother with refreshing anymore.
			this.inWinMode = true;
			// Handle the win by completely changing the status bar
			handleWin(currScore.player2.getPiecesRemaining() == 0, currScore);
			// Don't do anything else here
			return;
		}

		// Get the text to display.
		String scoreText  = msgs.getScoreStatus(
				currScore.player1.getPiecesRemaining(), currScore.player2.getPiecesRemaining(), 
				currScore.player1.getKingCount(), currScore.player2.getKingCount());
		
		String playerText = msgs.getTurnStatus(currScore.currentPlayerIsPlayer1);
		
		// Display the updated score text.
		this.score.setText(scoreText);
		this.turn.setText(playerText);
	}
	
	// Calculates the resulting score of a game of checkers.
	//TODO: Include time as a factor
	private static int getScoreNumber(boolean isPlayer1, ScoreInfo finalScore) {
		// Get the data about the winning player.
		PlayerInfo player = (isPlayer1 ? finalScore.player1 : finalScore.player2);
	
		// Each non-king is worth 1 point.
		// Each king are worth 10 points. (9 for being a king + 1 for being a piece.)
		return (player.getKingCount() * 9) + (player.getPiecesRemaining());
	}
	
	// Updates the status bar to display that a player has won
	private void handleWin(boolean isPlayer1, ScoreInfo finalScore) {
		int scoreNumber = getScoreNumber(isPlayer1, finalScore);
		
		// Write the score to the save file
		endGameHandler.writeScore(settings.getSaveFileLocation(), scoreNumber, msgs.getPlayerName(isPlayer1));
		
		// Create the winner message
		String msg = msgs.getWinnerMessage(isPlayer1);
		this.score.setText(""); // Clear out the score text
		this.turn.setText(msg); // Place the win message into the turn display
		
		// Create a button to let the user play again
		SafeNode playAgain = buttonFactory.create(msgs.getPlayAgain(), endGameHandler::playAgain);	
		
		// Create a button to let the player view statistics about the game
		SafeNode gameStats = buttonFactory.create(msgs.getViewGameStats(), () -> endGameHandler.viewStats(settings.getSaveFileLocation(), scoreNumber));
		
		// Create a pane to display the buttons in
		SafeBorderPane bottom = new SafeBorderPane();
		bottom.setChildren(new PositionedNodes()
			.setLeft(gameStats)
			.setRight(playAgain));
		
		// INCREASE THE PADDING!!!
		parent.setPadding(10);
		
		// Set the content once more
		parent.setChildren(new PositionedNodes()
			.setCenter(this.turn)
			.setBottom(bottom));
	}
	
	// Gets the visual node for the status bar
	public SafeNode getNode() {
		return parent;
	}

	@Override
	public void dispose() {
		cleanup.dispose();
	}
}
