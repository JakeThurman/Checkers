package jakethurman.games.checkers;

import java.util.LinkedList;
import java.util.function.Consumer;
import jakethurman.foundation.Disposable;

public class CheckersTurnManager implements Disposable {
	private boolean                         isPlayer1sTurn   = true;
	private LinkedList<Consumer<ScoreInfo>> onChangeHandlers = null;
	
	private int player1CheckersRemaining;
	private int player2CheckersRemaining;
	private int player1Kings = 0;
	private int player2Kings = 0;
	
	public CheckersTurnManager(Settings settings) {
		//Initialize scores
		this.player2CheckersRemaining = this.player1CheckersRemaining = settings.getNumPieces();
		this.player1Kings = this.player2Kings = 0;
		
		this.onChangeHandlers = new LinkedList<>();
	}
		
	public boolean isPlayer1sTurn() {
		return this.isPlayer1sTurn;
	}
	
	public void endTurn() {
		this.isPlayer1sTurn = !this.isPlayer1sTurn;
		triggerOnChangeHandlers();
	}
	
	public ScoreInfo getCurrentScore() {
		return new ScoreInfo(this.isPlayer1sTurn, player1CheckersRemaining, player2CheckersRemaining, player1Kings, player2Kings);
	}
	
	public void triggerOnChangeHandlers() {
		ScoreInfo score = this.getCurrentScore();
		
		for (Consumer<ScoreInfo> handler : this.onChangeHandlers)
			handler.accept(score);
	}
	
	public void addOnChangeHandler(Consumer<ScoreInfo> handler) {
		this.onChangeHandlers.add(handler);
	}

	public void recordDeadChecker(boolean isPlayer1, boolean wasKing) {
		if (isPlayer1)
			player1CheckersRemaining--;
		else 
			player2CheckersRemaining--;
		
		//If this was a king, kill that
		if (wasKing) {
			if (isPlayer1)
				player1Kings--;
			else 
				player2Kings--;
		}
		
		triggerOnChangeHandlers();
	}
	
	public void playerHasKing(boolean isPlayer1) {
		if (isPlayer1)
			this.player1Kings++;
		else 
			this.player2Kings++;

		triggerOnChangeHandlers();
	}

	@Override
	public void dispose() {
		// Clear out stored handlers
		onChangeHandlers = null;
	}
}
