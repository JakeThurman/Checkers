import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.GridPane;
import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import java.util.LinkedList;
import javafx.geometry.HPos;
import javafx.geometry.VPos;

public class CheckerboardInitialization {    
	private final CircleFactory circleFactory;
	private final CheckerInteractionManager interactions;
	
	public CheckerboardInitialization(CircleFactory circleFactory, CheckerInteractionManager interactions) {
		this.circleFactory = circleFactory;
		this.interactions  = interactions;
	}
	
    public void initialize(Checkerboard data) {
    	showAvailableMovesOnClick(data);
        configureBoardLayout(data.visual);
        addSquaresToBoard(data.visual);
        addPiecesToBoard(data);
    }
        
    private void showAvailableMovesOnClick(Checkerboard data) {
    	LinkedList<Node> choiceNodes = new LinkedList<Node>();
    	
    	interactions.setAfterSelect((checker) -> {    		
    		for (CellSearchResult searchData : data.getAvailableSpaces(checker)) {
    			Node      circle = circleFactory.createOpaque(Color.LIGHTBLUE);
    			CellIndex pos    = searchData.getCellIndex();
    			interactions.initializeMoveOption(circle, () -> {
    				data.movePieceToCell(checker, pos);
    				
    				if (searchData.getIsJump()) {
    					data.setJumped(searchData.getJumpedCellIndex());
    				}
    			});
    			data.visual.add(circle, pos.x, pos.y);
    			choiceNodes.add(circle);
    		}
    	});
    	
    	interactions.setAfterUnselect(() -> {
    		for (Node node : choiceNodes) {
    			data.visual.getChildren().remove(node);
    		}
    		
    		choiceNodes.clear();
    	});
    }
        
    private void configureBoardLayout(GridPane visual) {
        for (int i=0; i < Settings.BOARD_SIZE; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setMinHeight(Settings.SQUARE_SIZE);
            rowConstraints.setPrefHeight(Settings.SQUARE_SIZE);
            rowConstraints.setMaxHeight(Settings.SQUARE_SIZE);
            rowConstraints.setValignment(VPos.CENTER);
            visual.getRowConstraints().add(rowConstraints);

            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setMinWidth(Settings.SQUARE_SIZE);
            colConstraints.setMaxWidth(Settings.SQUARE_SIZE);
            colConstraints.setPrefWidth(Settings.SQUARE_SIZE);
            colConstraints.setHalignment(HPos.CENTER);
            visual.getColumnConstraints().add(colConstraints);
        }
    }
    
    private void addSquaresToBoard(GridPane visual) {
        Color[] squareColors = new Color[] {Color.WHITE, Color.BLACK};
        for (int row = 0; row < Settings.BOARD_SIZE; row++) {
            for (int col = 0; col < Settings.BOARD_SIZE; col++) {
                visual.add(new Rectangle(Settings.SQUARE_SIZE, Settings.SQUARE_SIZE, squareColors[(row+col)%2]), col, row);
            }
        }
    }
    
    private void addPiecesToBoard(Checkerboard data) {
        for (int i=0; i < Settings.NUM_PIECES; i++) {  
        	// Give player 1 a piece   
        	Checker p1Checker = new Checker(
        		true, // isPlayer1
        		Color.DEEPPINK, // King Fill
        		circleFactory.create(Color.RED), 
        		getPlayer1Cell(i));

        	interactions.initalizeChecker(p1Checker);
        	data.pieceIsInCell(p1Checker);

        	// Give player 2 a piece
        	Checker p2Checker = new Checker(
	    		false, // isPlayer1
	    		Color.DARKSLATEGRAY, // King Fill
	    		circleFactory.create(Color.BLACK),
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
}