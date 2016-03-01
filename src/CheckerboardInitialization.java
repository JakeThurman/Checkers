import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.geometry.HPos;
import javafx.geometry.VPos;

public class CheckerboardInitialization {    
    public static Checkerboard initialize(GridPane visual, CircleFactory circleFactory) {
    	Checkerboard data = new Checkerboard();
    	
        configureBoardLayout(visual);
        addSquaresToBoard(visual);
        addPiecesToBoard(visual, circleFactory, data);
        
        return data;
    }
        
    private static void configureBoardLayout(GridPane visual) {
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
    
    private static void addSquaresToBoard(GridPane visual) {
        Color[] squareColors = new Color[] {Color.WHITE, Color.BLACK};
        for (int row = 0; row < Settings.BOARD_SIZE; row++) {
            for (int col = 0; col < Settings.BOARD_SIZE; col++) {
                visual.add(new Rectangle(Settings.SQUARE_SIZE, Settings.SQUARE_SIZE, squareColors[(row+col)%2]), col, row);
            }
        }
    }

    private static void addPiecesToBoard(GridPane visual, CircleFactory circleFactory, Checkerboard data) {
        for (int i=0; i < Settings.NUM_PIECES; i++) {
        	CellIndex player1Cell = getPlayer1Cell(i);
        	CellIndex player2Cell = getPlayer2Cell(i);
        	
        	data.pieceIsInCell(/*isPlayer1*/ true, player1Cell);
        	data.pieceIsInCell(/*isPlayer1*/ false, player2Cell);
        	
            // Give Player 1 a piece
        	visual.add(
                 circleFactory.create(Color.RED, Color.WHITE),
                 player1Cell.x,
                 player1Cell.y);
            
            // Give Player 2 a piece
        	visual.add(
                circleFactory.create(Color.BLACK, Color.WHITE),
                player2Cell.x,
                player2Cell.y);
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