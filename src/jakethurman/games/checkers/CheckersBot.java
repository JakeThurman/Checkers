package jakethurman.games.checkers;

import java.util.HashMap;
import edu.frederick.cis.datastructures.AVLTree;
import jakethurman.games.checkers.components.Checker;
import jakethurman.games.checkers.components.Checkerboard;

public class CheckersBot {
	private final Checkerboard board;
	private final CheckersTurnManager ctm;
	
	public CheckersBot(Checkerboard board, CheckersTurnManager ctm) {
		this.board      = board;
		this.ctm        = ctm;
	}
	
	public void init(boolean forPlayer1) {
    	ctm.addOnTurnStartHandler(isPlayer1 -> {
    		// If it's the other players 
    		// turn, we don't want to touch
    		// anything! Get out now!!!
    		// Otherwise take the turn!
    		if (forPlayer1 == isPlayer1)
    			takeTurn(forPlayer1);
    	});
    }
	
	private void takeTurn(boolean forPlayer1) {
		HashMap<CellSearchResult, Checker> pieceMap    = new HashMap<>();
		AVLTree<CellSearchResult>          viableMoves = new AVLTree<>();
		
		// Loop through all of this player's checkers on the board
		for (Checker checker : board.getAllCheckers(forPlayer1)) {
			// Get all move options available for each checker
			for (CellSearchResult viableMove : board.getAvailableSpaces(checker)) {
				// Record it as a viable move
				viableMoves.insert(viableMove);
				
				// And store it in the map so we can get back to it's checker later
				pieceMap.put(viableMove, checker);
			}
		}
		
		// Take the best available option to us to go to.
		CellSearchResult bestMove = getBestMove(viableMoves);
		
		if (bestMove != null)
			board.makeMove(pieceMap.get(bestMove), bestMove);
	}

	private static CellSearchResult getBestMove(AVLTree<CellSearchResult> viableMoves) {
		// This should be optimized and tuned later on.
		// But for now take the calculated middle move.
		// Why you may ask... because this was due!! ;)
		return viableMoves.getRoot();
	}
}
