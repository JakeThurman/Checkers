package jakethurman.games.checkers;

import java.util.LinkedList;
import java.util.function.Consumer;
import jakethurman.foundation.Disposable;

public class CheckersTurnManager implements Disposable {
	private boolean                         isPlayer1sTurn   = true;
	private LinkedList<Consumer<ScoreInfo>> onChangeHandlers = null;
	
	private int player1CheckersRemaining = Settings.NUM_PIECES;
	private int player2CheckersRemaining = Settings.NUM_PIECES;
	
	public CheckersTurnManager() {
		this.onChangeHandlers = new LinkedList<Consumer<ScoreInfo>>();
	}
		
	public boolean isPlayer1sTurn() {
		return this.isPlayer1sTurn;
	}
	
	public void endTurn() {
		this.isPlayer1sTurn = !this.isPlayer1sTurn;
		triggerOnChangeHandlers();
	}
	
	public ScoreInfo getCurrentScore() {
		return new ScoreInfo(this.isPlayer1sTurn, player1CheckersRemaining, player2CheckersRemaining);
	}
	
	public void triggerOnChangeHandlers() {
		ScoreInfo score = this.getCurrentScore();
		
		for (Consumer<ScoreInfo> handler : this.onChangeHandlers)
			handler.accept(score);
	}
	
	public void addOnChangeHandler(Consumer<ScoreInfo> handler) {
		this.onChangeHandlers.add(handler);
	}

	public void recordDeadChecker(boolean isPlayer1) {
		if (isPlayer1)
			player1CheckersRemaining--;
		else 
			player2CheckersRemaining--;
		
		triggerOnChangeHandlers();
	}
	
	public void dispose() {
		// Clear out stored handlers
		onChangeHandlers = null;
	}
}