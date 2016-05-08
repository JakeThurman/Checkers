package jakethurman.games.checkers;

import java.util.LinkedList;
import java.util.function.Consumer;
import jakethurman.foundation.Disposable;
import jakethurman.games.Difficulty;

/*
 * Manages turns for a game of checkers 
 */
public class CheckersTurnManager implements Disposable {
	private boolean isPlayer1sTurn;
	
	private final PlayerInfo player1;
	private final PlayerInfo player2;
	private final LinkedList<Consumer<ScoreInfo>> onChangeHandlers;	
	private final LinkedList<BooleanConsumer> onTurnStartHandlers;	
	private final LinkedList<TurnInfo> turns;
	
	private final long startTimeMS;
	private       long endTimeMS;
	
	// Constructor
	public CheckersTurnManager(Settings settings) {		
		this.player1 = new PlayerInfo(settings.getNumPieces());		
		this.player2 = new PlayerInfo(settings.getNumPieces());
		
		// Initialize our lists
		this.onChangeHandlers    = new LinkedList<>();
		this.onTurnStartHandlers = new LinkedList<>();
		this.turns               = new LinkedList<>();
		
		// Record the start time millisecond for the game as right now.
		this.startTimeMS = System.currentTimeMillis();
		
		// Randomly decide if player 1 should go first
		// Unless this is an AI game, in which case,
		// always make player 1 go first.
		this.isPlayer1sTurn = settings.getDifficulty() == Difficulty.HUMAN ? Math.random() < 0.5 : true;
		
		// Start the first turn
		turns.add(new TurnInfo(getCurrentScore()));
	}
		
	// Simple get method
	public boolean isPlayer1sTurn() {
		return this.isPlayer1sTurn;
	}
	
	// Ends the current turn
	public void endTurn() {
		// Set the score of the current turn to the current score.
		turns.getLast().setEnd(getCurrentScore());
		
		// Toggle the current player whom the current turn is for
		this.isPlayer1sTurn = !this.isPlayer1sTurn;
				
		// Trigger change handlers now that we ended the old turn
		triggerOnChangeHandlers();
		
		// Add the new turn into the stack
		turns.add(new TurnInfo(getCurrentScore()));
		
		// Now, fire all of our turn-start handlers.
		for (BooleanConsumer handler : this.onTurnStartHandlers)
			handler.accept(this.isPlayer1sTurn);
	}
	
	// Gets a ScoreInfo object of the current turn
	public ScoreInfo getCurrentScore() {
		return new ScoreInfo(this.isPlayer1sTurn, this.player1.clone(), this.player2.clone());
	}
	
	// Fires all added change handlers
	private void triggerOnChangeHandlers() {
		ScoreInfo score = this.getCurrentScore();
		
		for (Consumer<ScoreInfo> handler : this.onChangeHandlers)
			handler.accept(score);
	}
	
	// Adds a new on-change handler to be 
	// fired any time the score changes
	// The handler will be given the current
	// score at the time it is called.
	public void addOnChangeHandler(Consumer<ScoreInfo> handler) {
		this.onChangeHandlers.add(handler);
	}
	
	// Adds a new turn-start handler to be 
	// fired any time a new turn has begun.
	// The handler will be given the boolean 
	// value returned by .isPlayer1sTurn()
	public void addOnTurnStartHandler(BooleanConsumer handler) {
		this.onTurnStartHandlers.add(handler);
	}

	// Records the fact that a player has lost a piece
	// @param isPlayer1: Which player lost a piece?
	// @param wasKing: Was the checker that died a king?
	public void recordDeadChecker(boolean isPlayer1, boolean wasKing) {
		// Record the dead checker in the neccesary player's object
		if (isPlayer1)
			player1.playerLostPiece(wasKing);
		else 
			player2.playerLostPiece(wasKing);
		
		// Fire change listeners
		triggerOnChangeHandlers();
	}
	
	// Records that a player has changed one 
	// of there non-king pieces to a king.
	public void playerHasKing(boolean isPlayer1) {
		// Record the new king to the appropriate player object
		if (isPlayer1)
			player1.playerHasKing();
		else 
			player2.playerHasKing();
		
		// Fire change listeners
		triggerOnChangeHandlers();
	}
	
	// Gets the whole turn stack.
	// TODO: This returns the whole object, it should just return an iterator
	public LinkedList<TurnInfo> getTurnData() {
		return this.turns;
	}

	@Override
	public void dispose() {
		// Clear out stored handlers
		// TODO: This is really unneeded, the garbage 
		// 		collector should do this for us
		onChangeHandlers.clear();
	}

	// Records that the game has ended.
	public void gameDidEnd() {
		this.endTimeMS = System.currentTimeMillis();
	}

	// Gets the millisecond time stamp of when exactly the game ended
	public long getGameEndMS() {
		return this.endTimeMS;
	}

	// Gets the millisecond time stamp of when the game started	
	public long getGameStartMS() {
		return this.startTimeMS;
	}
	
	// Helper interface for onTurnStartHandlers
	public interface BooleanConsumer {
		public void accept(boolean val);
	}
}
