package jakethurman.games.checkers;

import java.util.HashMap;
import edu.frederick.cis.datastructures.AVLTree;
import jakethurman.games.Difficulty;
import jakethurman.games.checkers.components.Checker;
import jakethurman.games.checkers.components.Checkerboard;
import javafx.application.Platform;

/*
 * An AI robot player for a checkers game
 */
public class CheckersBot {
	// Data
	private final Checkerboard board;
	private final CheckersTurnManager ctm;
	private final Settings settings;
	
	// C'tor
	public CheckersBot(Checkerboard board, CheckersTurnManager ctm, Settings settings) {
		this.board    = board;
		this.ctm      = ctm;
		this.settings = settings;
	}
	
	// Initializes the bot to play for a given player
	public void init(boolean forPlayer1) {
		// Add a listener for turn changes which is where we will take a turn.
    	ctm.addOnTurnStartHandler(isPlayer1 -> {
    		new Thread(() -> {
    			try {
					Thread.sleep(settings.getBotSleepMS());
				} catch (Exception e) {
					// TODO: Auto-generated catch block
					e.printStackTrace();
				}
    			
	    		Platform.runLater(() -> {
		    		// If it's the other players 
		    		// turn, we don't want to touch
		    		// anything! Get out now!!!
		    		// Otherwise take the turn!
		    		if (forPlayer1 == isPlayer1)
		    			takeTurn(forPlayer1);
	    		});
    		}).start();
    	});
    }
	
	// Takes a turn as a given player (as Player 1 if @forPlayer1 otherwise Player 2)
	private void takeTurn(boolean forPlayer1) {
		// Stores a Checker so we can get back to it later from the CellSearchResult we choose.
		HashMap<CellSearchResult, Checker> pieceMap = new HashMap<>();
		
		// Create a tree for all of the potential moves we can make.
		AVLTree<CellSearchResult> viableMoves = new AVLTree<>();
		
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
		CellSearchResult bestMove = getBestMove(viableMoves, settings.getDifficulty());
		
		// Make the best move if there is one.
		if (bestMove != null)
			board.makeMove(pieceMap.get(bestMove), bestMove);
	}

	// Returns the best move based on the given difficulty setting
	// TODO: Improve computation algorithm to not be just min, mid, max.
	private static CellSearchResult getBestMove(AVLTree<CellSearchResult> viableMoves, Difficulty difficulty) {
		switch(difficulty) {
			case EASY:
				// Take the calculated "worst" move.
				return viableMoves.getMin();
			case MEDIUM:
				// Take the calculated "average" move.
				return viableMoves.getRoot();
			case HARD:
				// Take the calculated "best" move.
				return viableMoves.getMax();
			case HUMAN:
				throw new Error("Checker bot should only be run when playing as a computer player!"); //TODO: Pick a better exception class!
			default:
				throw new Error("Difficulty is not implemented."); //TODO: Pick a better exception class!
		}		
	}
}
