package jakethurman.games.checkers.components;

import java.util.LinkedList;

import jakethurman.components.factories.ShapeFactory;
import jakethurman.components.SafeNode;
import jakethurman.components.SafePaint;
import jakethurman.foundation.CleanupHandler;
import jakethurman.foundation.Disposable;
import jakethurman.foundation.Point;
import jakethurman.components.helpers.GridHelpers;
import jakethurman.games.checkers.CellSearchResult;
import jakethurman.games.checkers.CheckerInteractionManager;
import jakethurman.games.checkers.Settings;

public class CheckersInitialization implements Disposable {    
	private final ShapeFactory              shapeFactory;
	private final CheckerInteractionManager interactions;
	private final CleanupHandler            cleanup;
	private final Settings                  settings;
	private final GridHelpers               gridHelpers;
	
	public CheckersInitialization(ShapeFactory shapeFactory, GridHelpers gridHelpers, CheckerInteractionManager interactions, Settings settings) {
		this.shapeFactory  = shapeFactory;
		this.interactions  = interactions;
		this.settings      = settings;
		this.gridHelpers   = gridHelpers;
		this.cleanup = new CleanupHandler(shapeFactory, interactions, settings, gridHelpers);
	}
	
    public void initialize(Checkerboard data) {
    	showAvailableMovesOnClick(data);
        gridHelpers.fillGridWithSquares(data.getNode());
        addPiecesToBoard(data);
    }
        
    private void showAvailableMovesOnClick(Checkerboard data) {
    	LinkedList<SafeNode> choiceNodes = new LinkedList<>();
    	
    	interactions.setAfterSelect((checker) -> {    		
    		for (CellSearchResult searchData : data.getAvailableSpaces(checker)) {
    			SafeNode circle = shapeFactory.createOpaqueCircle(SafePaint.LIGHTBLUE);
    			Point    pos    = searchData.getPoint();
    			interactions.initializeMoveOption(circle, () -> {
    				data.movePieceToCell(checker, pos);
    				
    				// Set any jumped pieces as such
    				for (Point i : searchData.getJumpedCells())
    					data.setJumped(i);
    			});
    			data.getNode().add(circle, pos);
    			choiceNodes.add(circle);
    		}
    	});
    	
    	interactions.setAfterUnselect(() -> {
    		for (SafeNode node : choiceNodes)
    			data.getNode().remove(node);
    		
    		choiceNodes.clear();
    	});
    }
    
    private void addPiecesToBoard(Checkerboard data) {
    	int numPieces = settings.getNumPieces();
    	int boardSize = settings.getBoardSize();
    	
        for (int i=0; i < numPieces; i++) {  
        	// Give player 1 a piece   
        	Checker p1Checker = new Checker(
        		true, // isPlayer1
        		SafePaint.DEEPPINK, // King Fill
        		shapeFactory.createCircle(SafePaint.RED), 
        		getPlayer1Cell(i, boardSize));

        	interactions.initalizeChecker(p1Checker);
        	data.pieceIsInCell(p1Checker);

        	// Give player 2 a piece
        	Checker p2Checker = new Checker(
	    		false, // isPlayer1
	    		SafePaint.DARKSLATEGRAY, // King Fill
	    		shapeFactory.createCircle(SafePaint.BLACK),
	    		getPlayer2Cell(i, boardSize));
        	
            interactions.initalizeChecker(p2Checker);
        	data.pieceIsInCell(p2Checker);
        }
    }
    
    private static Point getPlayer2Cell(int currPiece, int boardSize) {
        return new Point(
        	currPiece%(boardSize/2) * 2 + (1 + 2*currPiece/boardSize)%2, // X
        	(currPiece*2)/boardSize);                                     // Y
    }
    
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