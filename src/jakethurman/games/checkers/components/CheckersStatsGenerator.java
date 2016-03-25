package jakethurman.games.checkers.components;

import java.util.LinkedList;

import jakethurman.components.CellIndex;
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
				ChartFactory.ChartDataSeries redData   = chartFactory.createDataSeries("Red", new CellIndex(0, 0), new CellIndex(1, 5), new CellIndex(2, 10));
				ChartFactory.ChartDataSeries blackData = chartFactory.createDataSeries("Black", new CellIndex(15, 0), new CellIndex(7, 10));
				//TODO: Factor strings out to Messages instance
				return chartFactory.createLineChart("Title", "Time (Seconds)", "Pieces", redData, blackData);
			default:
				return SafeNode.NONE;
		}
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
