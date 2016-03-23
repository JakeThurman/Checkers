package jakethurman.games.checkers;

import java.util.LinkedList;
import java.util.function.Consumer;
import jakethurman.foundation.Disposable;

public class CheckersTurnManager implements Disposable {
	private boolean isPlayer1sTurn;
	
	private final PlayerInfo player1;
	private final PlayerInfo player2;
	private final LinkedList<Consumer<ScoreInfo>> onChangeHandlers;	
	private final LinkedList<TurnInfo> turns;
	
	public CheckersTurnManager(Settings settings) {
		this.player1 = new PlayerInfo(settings.getNumPieces());		
		this.player2 = new PlayerInfo(settings.getNumPieces());
		
		this.onChangeHandlers = new LinkedList<>();
		this.turns            = new LinkedList<>();
		
		//Randomly decide if player 1 should go first
		this.isPlayer1sTurn = Math.random() < 0.5;
		
		//Start the first turn
		turns.add(new TurnInfo(getCurrentScore()));
	}
		
	public boolean isPlayer1sTurn() {
		return this.isPlayer1sTurn;
	}
	
	public void endTurn() {
		turns.getLast().setEnd(getCurrentScore());
		
		this.isPlayer1sTurn = !this.isPlayer1sTurn;
				
		triggerOnChangeHandlers();
		
		turns.add(new TurnInfo(getCurrentScore()));
	}
	
	public ScoreInfo getCurrentScore() {
		return new ScoreInfo(this.isPlayer1sTurn, this.player1, this.player2);
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
			player1.playerLostPiece(wasKing);
		else 
			player2.playerLostPiece(wasKing);
		
		triggerOnChangeHandlers();
	}
	
	public void playerHasKing(boolean isPlayer1) {
		if (isPlayer1)
			player1.playerHasKing();
		else 
			player2.playerHasKing();
		
		triggerOnChangeHandlers();
	}

	@Override
	public void dispose() {
		// Clear out stored handlers
		onChangeHandlers.clear();
	}
}
