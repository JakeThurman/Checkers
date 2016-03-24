package jakethurman.games.checkers.components;

import java.util.LinkedList;

import jakethurman.components.SafeNode;
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
	
	public CheckersStatsGenerator(CheckersTurnManager ctm, Messages msgs) {
		this.ctm     = ctm;
		this.msgs    = msgs;
		this.cleanup = new CleanupHandler(ctm, msgs);
	}

	@Override
	public SafeNode getChart(StatChartType type) {
		if (type != StatChartType.PIECES_OVER_TIME)
			return null;
		
		return null;
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
