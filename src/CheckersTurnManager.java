import java.util.function.ObjIntConsumer;

public class CheckersTurnManager {
	private boolean                 isPlayer1sTurn = true;
	private ObjIntConsumer<Boolean> onChange       = null;
	
	private int player1CheckersRemaining = Settings.NUM_PIECES;
	private int player2CheckersRemaining = Settings.NUM_PIECES;
		
	public boolean isPlayer1sTurn() {
		return this.isPlayer1sTurn;
	}
	
	public void endTurn() {
		this.isPlayer1sTurn = !this.isPlayer1sTurn;
		triggerOnChangeHandlers();
	}
	
	public void triggerOnChangeHandlers() {
		if (this.onChange != null)
			onChange.accept(this.isPlayer1sTurn, this.isPlayer1sTurn ? player1CheckersRemaining : player2CheckersRemaining);
	}
	
	public void setOnChange(ObjIntConsumer<Boolean> onTurnStart) {
		this.onChange = onTurnStart;
	}

	public void recordDeadChecker(boolean isPlayer1) {
		if (isPlayer1)
			player1CheckersRemaining--;
		else 
			player2CheckersRemaining--;
		
		triggerOnChangeHandlers();
	}
}
