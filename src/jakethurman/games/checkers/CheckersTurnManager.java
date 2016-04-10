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
	
	private final long startTimeMS;
	private       long endTimeMS;
	
	public CheckersTurnManager(Settings settings) {
		this.player1 = new PlayerInfo(settings.getNumPieces());		
		this.player2 = new PlayerInfo(settings.getNumPieces());
		
		this.onChangeHandlers = new LinkedList<>();
		this.turns            = new LinkedList<>();
		
		this.startTimeMS = System.currentTimeMillis();
		
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
		return new ScoreInfo(this.isPlayer1sTurn, this.player1.clone(), this.player2.clone());
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
	
	public LinkedList<TurnInfo> getTurnData() {
		return this.turns;
	}

	@Override
	public void dispose() {
		// Clear out stored handlers
		onChangeHandlers.clear();
	}

	public void gameDidEnd() {
		this.endTimeMS = System.currentTimeMillis();
	}

	public long getGameEndMS() {
		return this.endTimeMS;
	}
	
	public long getGameStartMS() {
		return this.startTimeMS;
	}
	
	//DANGEROUS DEBUG FUNCTION
	public void hackPlayer2ToZeroPieces() {
		while(this.player2.getPiecesRemaining() > 0) {
			this.player2.playerLostPiece(false);
		}
	}
}
