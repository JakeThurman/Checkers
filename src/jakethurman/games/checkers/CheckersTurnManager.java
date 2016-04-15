package jakethurman.games.checkers;

import java.util.LinkedList;
import java.util.function.Consumer;
import jakethurman.foundation.Disposable;

public class CheckersTurnManager implements Disposable {
	private boolean isPlayer1sTurn;
	
	private final PlayerInfo player1;
	private final PlayerInfo player2;
	private final LinkedList<Consumer<ScoreInfo>> onChangeHandlers;	
	private final LinkedList<BooleanConsumer> onTurnStartHandlers;	
	private final LinkedList<TurnInfo> turns;
	
	private final long startTimeMS;
	private       long endTimeMS;
	
	public CheckersTurnManager(Settings settings, boolean isVsAI) {		
		this.player1 = new PlayerInfo(settings.getNumPieces());		
		this.player2 = new PlayerInfo(settings.getNumPieces());
		
		this.onChangeHandlers    = new LinkedList<>();
		this.onTurnStartHandlers = new LinkedList<>();
		this.turns               = new LinkedList<>();
		
		this.startTimeMS = System.currentTimeMillis();
		
		// Randomly decide if player 1 should go first
		// Unless this is an AI game, in which case,
		// always make player 1 go first.
		this.isPlayer1sTurn = isVsAI ? true : Math.random() < 0.5;
		
		// Start the first turn
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
		
		for (BooleanConsumer handler : this.onTurnStartHandlers)
			handler.accept(this.isPlayer1sTurn);
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
	

	public void addOnTurnStartHandler(BooleanConsumer handler) {
		this.onTurnStartHandlers.add(handler);
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
	
	public interface BooleanConsumer {
		public void accept(boolean val);
	}
}
