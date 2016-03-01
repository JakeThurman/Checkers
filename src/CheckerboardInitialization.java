import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.geometry.HPos;
import javafx.geometry.VPos;

public class CheckerboardInitialization {
    private final int NUM_PIECES,
                      BOARD_SIZE,
                      SQUARE_SIZE;

    public CheckerboardInitialization(int numPieces, int boardSize, int squareSize) {
        this.NUM_PIECES  = numPieces;
        this.BOARD_SIZE  = boardSize;
        this.SQUARE_SIZE = squareSize;
    }
    
    public void initialize(GridPane checkerBoard, CircleFactory circleFactory) {
        configureBoardLayout(checkerBoard);
        addSquaresToBoard(checkerBoard);
        addPiecesToBoard(checkerBoard, circleFactory);
    }
        
    private void configureBoardLayout(GridPane board) {
        for (int i=0; i<BOARD_SIZE; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setMinHeight(SQUARE_SIZE);
            rowConstraints.setPrefHeight(SQUARE_SIZE);
            rowConstraints.setMaxHeight(SQUARE_SIZE);
            rowConstraints.setValignment(VPos.CENTER);
            board.getRowConstraints().add(rowConstraints);

            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setMinWidth(SQUARE_SIZE);
            colConstraints.setMaxWidth(SQUARE_SIZE);
            colConstraints.setPrefWidth(SQUARE_SIZE);
            colConstraints.setHalignment(HPos.CENTER);
            board.getColumnConstraints().add(colConstraints);
        }
    }
    
    private void addSquaresToBoard(GridPane board) {
        Color[] squareColors = new Color[] {Color.WHITE, Color.BLACK};
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                board.add(new Rectangle(SQUARE_SIZE, SQUARE_SIZE, squareColors[(row+col)%2]), col, row);
            }
        }
    }

    private void addPiecesToBoard(GridPane checkerBoard, CircleFactory circleFactory) {
        for (int i=0; i<NUM_PIECES; i++) {
            // Give Player 1 a piece
            checkerBoard.add(
                 circleFactory.create(Color.WHITE, Color.BLACK),
                 getPlayer1Column(i),
                 getPlayer1Row(i));
            
            // Give Player 2 a piece
            checkerBoard.add(
                circleFactory.create(Color.BLACK, Color.WHITE),
                getPlayer2Column(i),
                getPlayer2Row(i));
        }
    }
    
    private int getPlayer2Row(int currPiece) {
        return (currPiece*2)/BOARD_SIZE;
    }
    
    private int getPlayer1Row(int currPiece) {
        return BOARD_SIZE - 1 - (currPiece*2)/BOARD_SIZE;
    }
    
    private int getPlayer2Column(int currPiece) {
        return currPiece%(BOARD_SIZE/2) * 2 + (1 + 2*currPiece/BOARD_SIZE)%2;
    }
    
    private int getPlayer1Column(int currPiece) {
        return currPiece%(BOARD_SIZE/2) * 2 + (2*currPiece/BOARD_SIZE)%2;
    }
}