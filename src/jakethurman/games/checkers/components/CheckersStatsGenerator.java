package jakethurman.games.checkers.components;

import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import jakethurman.components.SafeNode;
import jakethurman.components.factories.ChartFactory;
import jakethurman.foundation.CleanupHandler;
import jakethurman.games.StatChartType;
import jakethurman.games.StatsGenerator;
import jakethurman.games.checkers.CheckersTurnManager;
import jakethurman.games.checkers.Messages;
import jakethurman.games.checkers.PlayerInfo;
import jakethurman.games.checkers.ScoreInfo;
import jakethurman.games.checkers.TurnInfo;

/*
 * Generates statistics about a checkers game.
 */
public class CheckersStatsGenerator implements StatsGenerator {
	// Dependencies
	private final CheckersTurnManager ctm;	
	private final CleanupHandler      cleanup;
	private final Messages            msgs;
	private final ChartFactory        chartFactory;
	
	// C'tor
	public CheckersStatsGenerator(CheckersTurnManager ctm, Messages msgs, ChartFactory chartFactory) {
		this.ctm          = ctm;
		this.msgs         = msgs;
		this.chartFactory = chartFactory;
		this.cleanup      = new CleanupHandler(ctm, msgs, chartFactory);
	}

	// Gets a rendered chart for a given type of chart 
	// (currently there is only one type)
	@Override
	public SafeNode getChart(StatChartType type) {
		switch(type) {
			case PIECES_OVER_TIME:
				return getPiecesOverTime();
			default:
				return SafeNode.NONE;
		}
	}
	
	// Creates a chart representing the number of pieces each player had over the period of a game.
	public SafeNode getPiecesOverTime() {
		// Create maps for each line on the chart.
		Map<Double, Double> player1CheckerChanges = new HashMap<>();
		Map<Double, Double> player2CheckerChanges = new HashMap<>();
		Map<Double, Double> player1KingChanges = new HashMap<>();
		Map<Double, Double> player2KingsChange = new HashMap<>();
		
		// Loop through all of the turns that happened over the course of the game
		for (TurnInfo turn : ctm.getTurnData()) {
			ScoreInfo score = turn.getScoreAtStart();
			
			// Get the number of seconds after the game started that this turn occurred.
			double secAfterStart = (turn.getStart() - ctm.getGameStartMS()) / 1000;
			
			// Add the number of checkers at this time to the map.
			(score.currentPlayerIsPlayer1 ? player1CheckerChanges : player2CheckerChanges)
				.put(new Double(secAfterStart), new Double(score.getCurrentPlayer().getPiecesRemaining()));
			
			// Add the number of kings at this time to the map.
			(score.currentPlayerIsPlayer1 ? player1KingChanges : player2KingsChange)
				.put(new Double(secAfterStart), new Double(score.getCurrentPlayer().getKingCount()));
		}
		
		// Create data series instances for each of the data sets
		//TODO: Factor strings out to Messages instance
		ChartFactory.ChartDataSeries player1      = chartFactory.createDataSeries("Red Pieces", player1CheckerChanges);
		ChartFactory.ChartDataSeries player2      = chartFactory.createDataSeries("Black Pieces", player2CheckerChanges);
		ChartFactory.ChartDataSeries player1Kings = chartFactory.createDataSeries("Red Kings", player1KingChanges);
		ChartFactory.ChartDataSeries player2Kings = chartFactory.createDataSeries("Black Kings", player2KingsChange);
		
		// Create the chart and return its node
		return chartFactory.createLineChart("Pieces Over Time", "Time (Seconds)", "Pieces", player1, player2, player1Kings, player2Kings);
	}
	
	// The the statistics of the game as a single sentence of information.
	@Override
	public String getStatsText(int finalScore) {
		// Get all of the turns that happened in the game
		LinkedList<TurnInfo> data = ctm.getTurnData();
		
		// Get the last turn with an end
		TurnInfo   lastTurn = data.getLast().getScoreAtEnd() == null ? data.get(data.size() - 2) : data.getLast();
		ScoreInfo  endScore = lastTurn.getScoreAtEnd(); // And get the score at that time
		PlayerInfo player   = endScore.getCurrentPlayer(); // Then get the information about the player who's turn it was on the last turn.
		
		// Return the complied message
		return msgs.getGameStatsMessage(endScore.currentPlayerIsPlayer1, player.getKingCount(), player.getPiecesRemaining(), lastTurn.getEnd() - data.getFirst().getStart(), finalScore);
	}
	
	@Override
	public void dispose() {
		cleanup.dispose();
	}
}
