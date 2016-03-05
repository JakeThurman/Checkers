import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class GameStatusBar {
	private final CheckersTurnManager turnManager;
	private final Messages            msgs;
	private final BorderPane          parent;
	private final Text                score;
	private final Text                turn;
	
	public GameStatusBar(Messages msgs, CheckersTurnManager turnManager) {
		this.turnManager = turnManager;
		this.msgs        = msgs;
		this.score       = new Text();
		this.turn        = new Text();
		this.parent      = new BorderPane();
		init();
	}
	
	//Holds basic initialization logic
	public void init() {
		updateText(turnManager.getCurrentScore());		
		turnManager.addOnChangeHandler((s) -> updateText(s));
		
		turn.setTextAlignment(TextAlignment.CENTER);
		turn.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
		score.setTextAlignment(TextAlignment.LEFT);
		score.setFont(Font.font("Tahoma", 20));
		
		parent.setCenter(this.turn);
		parent.setBottom(this.score);
	}
	
	private void updateText(ScoreInfo score) {
		String currScore = msgs.getScoreStatus(score.player1CheckersRemaining, score.player2CheckersRemaining);
		String currPlayer = msgs.getTurnStatus(score.currentPlayerIsPlayer1);

		this.score.setText(currScore);
		this.turn.setText(currPlayer);
	}
	
	public Node getNode() {
		return parent;
	}
}
