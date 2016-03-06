package jakethurman.games.checkers.components;

import java.util.LinkedList;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import jakethurman.components.CellIndex;
import jakethurman.components.factories.CircleFactory;
import jakethurman.components.SafeGridPane;
import jakethurman.components.SafeNode;
import jakethurman.components.SafePaint;
import jakethurman.foundation.CleanupHandler;
import jakethurman.foundation.Disposable;
import jakethurman.games.checkers.CellSearchResult;
import jakethurman.games.checkers.CheckerInteractionManager;
import jakethurman.games.checkers.Settings;

public class CheckerboardInitialization implements Disposable {    
	private final CircleFactory circleFactory;
	private final CheckerInteractionManager interactions;
	private final CleanupHandler cleanup;
	
	public CheckerboardInitialization(CircleFactory circleFactory, CheckerInteractionManager interactions) {
		this.circleFactory = circleFactory;
		this.interactions  = interactions;
		this.cleanup = new CleanupHandler(circleFactory, interactions);
	}
	
    public void initialize(Checkerboard data) {
    	showAvailableMovesOnClick(data);
        configureBoardLayout(data.visual);
        addSquaresToBoard(data.visual);
        addPiecesToBoard(data);
    }
        
    private void showAvailableMovesOnClick(Checkerboard data) {
    	LinkedList<SafeNode> choiceNodes = new LinkedList<SafeNode>();
    	
    	interactions.setAfterSelect((checker) -> {    		
    		for (CellSearchResult searchData : data.getAvailableSpaces(checker)) {
    			SafeNode  circle = circleFactory.createOpaque(SafePaint.LIGHTBLUE);
    			CellIndex pos    = searchData.getCellIndex();
    			interactions.initializeMoveOption(circle, () -> {
    				data.movePieceToCell(checker, pos);
    				
    				if (searchData.getIsJump()) {
    					data.setJumped(searchData.getJumpedCellIndex());
    				}
    			});
    			data.visual.add(circle, pos);
    			choiceNodes.add(circle);
    		}
    	});
    	
    	interactions.setAfterUnselect(() -> {
    		for (SafeNode node : choiceNodes)
    			data.visual.remove(node);
    		
    		choiceNodes.clear();
    	});
    }
        
    private void configureBoardLayout(SafeGridPane visual) {
        for (int i=0; i < Settings.BOARD_SIZE; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setMinHeight(Settings.SQUARE_SIZE);
            rowConstraints.setPrefHeight(Settings.SQUARE_SIZE);
            rowConstraints.setMaxHeight(Settings.SQUARE_SIZE);
            rowConstraints.setValignment(VPos.CENTER);
            visual.addRowConstraint(rowConstraints);

            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setMinWidth(Settings.SQUARE_SIZE);
            colConstraints.setMaxWidth(Settings.SQUARE_SIZE);
            colConstraints.setPrefWidth(Settings.SQUARE_SIZE);
            colConstraints.setHalignment(HPos.CENTER);
            visual.addColumnConstraint(colConstraints);
        }
    }
    
    private void addSquaresToBoard(SafeGridPane visual) {
    	SafePaint[] squareColors = new SafePaint[] {SafePaint.WHITE, SafePaint.BLACK};
        for (int row = 0; row < Settings.BOARD_SIZE; row++) {
            for (int col = 0; col < Settings.BOARD_SIZE; col++) {
                visual.add(new SafeNode(new Rectangle(Settings.SQUARE_SIZE, Settings.SQUARE_SIZE, squareColors[(row+col)%2].getUnsafe())), new CellIndex(col, row));
            }
        }
    }
    
    private void addPiecesToBoard(Checkerboard data) {
        for (int i=0; i < Settings.NUM_PIECES; i++) {  
        	// Give player 1 a piece   
        	Checker p1Checker = new Checker(
        		true, // isPlayer1
        		SafePaint.DEEPPINK, // King Fill
        		circleFactory.create(SafePaint.RED), 
        		getPlayer1Cell(i));

        	interactions.initalizeChecker(p1Checker);
        	data.pieceIsInCell(p1Checker);

        	// Give player 2 a piece
        	Checker p2Checker = new Checker(
	    		false, // isPlayer1
	    		SafePaint.DARKSLATEGRAY, // King Fill
	    		circleFactory.create(SafePaint.BLACK),
	    		getPlayer2Cell(i));
        	
            interactions.initalizeChecker(p2Checker);
        	data.pieceIsInCell(p2Checker);
        }
    }
    
    private static CellIndex getPlayer2Cell(int currPiece) {
        return new CellIndex(
        	currPiece%(Settings.BOARD_SIZE/2) * 2 + (1 + 2*currPiece/Settings.BOARD_SIZE)%2, // X
        	(currPiece*2)/Settings.BOARD_SIZE);                                     // Y
    }
    
    private static CellIndex getPlayer1Cell(int currPiece) {
    	return new CellIndex(
    			currPiece%(Settings.BOARD_SIZE/2) * 2 + (2*currPiece/Settings.BOARD_SIZE)%2, // X
    			Settings.BOARD_SIZE - 1 - (currPiece*2)/Settings.BOARD_SIZE);                // Y
    }

	public void dispose() {
		cleanup.dispose();
	}
}