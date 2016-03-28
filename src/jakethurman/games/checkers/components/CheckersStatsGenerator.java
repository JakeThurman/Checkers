package jakethurman.games.checkers.components;

import java.util.LinkedList;
import java.util.HashSet;
import jakethurman.components.Point;
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

public class CheckersStatsGenerator implements StatsGenerator {
	private final CheckersTurnManager ctm;	
	private final CleanupHandler      cleanup;
	private final Messages            msgs;
	private final ChartFactory        chartFactory;
	
	public CheckersStatsGenerator(CheckersTurnManager ctm, Messages msgs, ChartFactory chartFactory) {
		this.ctm          = ctm;
		this.msgs         = msgs;
		this.chartFactory = chartFactory;
		this.cleanup      = new CleanupHandler(ctm, msgs, chartFactory);
	}

	@Override
	public SafeNode getChart(StatChartType type) {
		switch(type) {
			case PIECES_OVER_TIME:
				return getPiecesOverTime();
			default:
				return SafeNode.NONE;
		}
	}
	
	public SafeNode getPiecesOverTime() {
		HashSet<Point> player1CheckerChanges = new HashSet<>();
		HashSet<Point> player2CheckerChanges = new HashSet<>();
		HashSet<Point> player1KingChanges = new HashSet<>();
		HashSet<Point> player2KingsChange = new HashSet<>();
		
		for (TurnInfo turn : ctm.getTurnData()) {
			ScoreInfo score = turn.getScoreAtStart();
			
			double secAfterStart = (turn.getStart() - ctm.getGameStartMS()) / 1000;
			
			(score.currentPlayerIsPlayer1 ? player1CheckerChanges : player2CheckerChanges)
				.add(new Point((int)secAfterStart, score.getCurrentPlayer().getPiecesRemaining()));
			
			(score.currentPlayerIsPlayer1 ? player1KingChanges : player2KingsChange)
				.add(new Point((int)secAfterStart, score.getCurrentPlayer().getKingCount()));
		}
		
		//TODO: Factor strings out to Messages instance
		ChartFactory.ChartDataSeries player1      = chartFactory.createDataSeries("Red", player1CheckerChanges);
		ChartFactory.ChartDataSeries player2      = chartFactory.createDataSeries("Black", player2CheckerChanges);
		ChartFactory.ChartDataSeries player1Kings = chartFactory.createDataSeries("Red Kings", player1KingChanges);
		ChartFactory.ChartDataSeries player2Kings = chartFactory.createDataSeries("Black Kings", player2KingsChange);
		
		return chartFactory.createLineChart("Pieces Over Time", "Time (Seconds)", "Pieces", player1, player2, player1Kings, player2Kings);
	}
	
	@Override
	public String getStatusText() {
		LinkedList<TurnInfo> data = ctm.getTurnData();
		
		TurnInfo   lastTurn = data.getLast().getScoreAtEnd() == null ? data.get(data.size() - 2) : data.getLast();
		ScoreInfo  endScore = lastTurn.getScoreAtEnd();
		PlayerInfo player   = endScore.getCurrentPlayer();
		
		return msgs.getGameStatsMessage(endScore.currentPlayerIsPlayer1, player.getKingCount(), player.getPiecesRemaining(), lastTurn.getLengthMS());
	}
	
	@Override
	public void dispose() {
		cleanup.dispose();
	}
}
