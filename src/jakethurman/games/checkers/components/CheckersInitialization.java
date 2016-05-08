package jakethurman.games.checkers.components;

import java.util.LinkedList;

import jakethurman.components.factories.ShapeFactory;
import jakethurman.components.SafeGridPane;
import jakethurman.components.SafeNode;
import jakethurman.components.SafePaint;
import jakethurman.foundation.CleanupHandler;
import jakethurman.foundation.Disposable;
import jakethurman.foundation.Point;
import jakethurman.components.helpers.GridHelpers;
import jakethurman.games.checkers.CellSearchResult;
import jakethurman.games.checkers.CheckerInteractionManager;
import jakethurman.games.checkers.Settings;

/* 
 * Initializes a checker board.
 */
public class CheckersInitialization implements Disposable {    
	// Dependencies
	private final ShapeFactory              shapeFactory;
	private final CheckerInteractionManager interactions;
	private final CleanupHandler            cleanup;
	private final Settings                  settings;
	private final GridHelpers               gridHelpers;
	
	// C'tor
	public CheckersInitialization(ShapeFactory shapeFactory, GridHelpers gridHelpers, CheckerInteractionManager interactions, Settings settings) {
		this.shapeFactory  = shapeFactory;
		this.interactions  = interactions;
		this.settings      = settings;
		this.gridHelpers   = gridHelpers;
		this.cleanup = new CleanupHandler(shapeFactory, interactions, settings, gridHelpers);
	}
	
	// Initialize a checker board
    public void initialize(Checkerboard data) {
    	showAvailableMovesOnClick(data); // We want to show the player there available moves on click
    	addRowsAndColumns(data.getNode()); // We want to add all of the needed rows/columns
        gridHelpers.fillGridWithSquares(data.getNode()); // We want to fill the cells of the board with white/black squares
        addPiecesToBoard(data); //Add pieces to every cell where they belong initially.
    }
    
    // Adds all of the needed row/columns
    private void addRowsAndColumns(SafeGridPane node) {
		// Get the setting values for this
    	int boardSize = settings.getBoardSize();
		int squareSize = settings.getSquareSize();
		
    	//Add {Settings.BOARD_SIZE} rows and columns
        for (int i=0; i < boardSize; i++) {
        	node.addRow(squareSize);
        	node.addColumn(squareSize);
        }
    }
        
    // Sets up the needed events to provide move options on checker click.
    private void showAvailableMovesOnClick(Checkerboard data) {
    	// We need a cache to store all of the move options currently displayed
    	// so we can remove them when the checker gets unselected.
    	LinkedList<SafeNode> choiceNodes = new LinkedList<>();
    	
    	// On select we want to add move options for the user
    	interactions.setAfterSelect((checker) -> {
    		// Loop through all of the available spaces
    		for (CellSearchResult searchData : data.getAvailableSpaces(checker)) {
    			// Create an opaque circle to represent a place where the player can move
    			SafeNode circle = shapeFactory.createOpaqueCircle(SafePaint.LIGHTBLUE);
    			data.getNode().add(circle, searchData.getPoint()); // Add it to the board
    			choiceNodes.add(circle); //Add it to the cache, so we can remove it later
    			
    			// Initialize the move option so that it gets a hover cursor 
    			// and so that when we click on it, the actual piece will move there.
    			interactions.initializeMoveOption(circle, () -> data.makeMove(checker, searchData));
    		}
    	});
    	
    	// On unselect we want to destroy the move options
    	interactions.setAfterUnselect(() -> {
    		// Remove any move option nodes
    		for (SafeNode node : choiceNodes)
    			data.getNode().remove(node);
    		
    		// and clear them so we don't have to again.
    		choiceNodes.clear();
    	});
    }
    
    // Adds all of the initial pieces to the checker board.
    private void addPiecesToBoard(Checkerboard data) {
    	// Get the settings upfront
    	int numPieces = settings.getNumPieces();
    	int boardSize = settings.getBoardSize();
    	
    	// Loop through, and create {numPieces} number of pieces for each player
        for (int i=0; i < numPieces; i++) {  
        	// Give player 1 a piece   
        	Checker p1Checker = new Checker(
        		true, // isPlayer1
        		SafePaint.DEEPPINK, // King Fill
        		shapeFactory.createCircle(SafePaint.RED), 
        		getPlayer1Cell(i, boardSize));

        	// Initialize the checker with the interactions manager
        	interactions.initalizeChecker(p1Checker);
        	
        	// Add the piece to the board
        	data.add(p1Checker);

        	// Give player 2 a piece
        	Checker p2Checker = new Checker(
	    		false, // isPlayer1
	    		SafePaint.DARKSLATEGRAY, // King Fill
	    		shapeFactory.createCircle(SafePaint.BLACK),
	    		getPlayer2Cell(i, boardSize));
        	
        	// Initialize the checker with the interactions manager
            interactions.initalizeChecker(p2Checker);
            
            // Add the piece to the board
        	data.add(p2Checker);
        }
    }
    
    // Gets a point for player 2 where their piece should go.
    private static Point getPlayer2Cell(int currPiece, int boardSize) {
        return new Point(
        	currPiece%(boardSize/2) * 2 + (1 + 2*currPiece/boardSize)%2, // X
        	(currPiece*2)/boardSize);                                    // Y
    }
    
    // Gets a point for player 1 where their piece should go.
    private static Point getPlayer1Cell(int currPiece, int boardSize) {
    	return new Point(
    			currPiece%(boardSize/2) * 2 + (2*currPiece/boardSize)%2, // X
    			boardSize - 1 - (currPiece*2)/boardSize);                // Y
    }

	@Override
	public void dispose() {
		cleanup.dispose();
	}
}