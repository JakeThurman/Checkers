package jakethurman.games.checkers.components;

import jakethurman.components.SafeNode;
import jakethurman.foundation.CleanupHandler;
import jakethurman.games.StatsGenerator;
import jakethurman.games.checkers.CheckersTurnManager;
import jakethurman.games.checkers.Messages;

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
	public SafeNode getChart() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getStatusText() {
		// TODO Auto-generated method stub

		return "Stuff happened";
	}
	
	@Override
	public void dispose() {
		cleanup.dispose();
	}
}
