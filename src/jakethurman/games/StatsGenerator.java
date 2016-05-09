package jakethurman.games;

import jakethurman.components.SafeNode;
import jakethurman.foundation.Disposable;

// Interface for statistic generation
public interface StatsGenerator extends Disposable {
	public SafeNode getChart(StatChartType type);
	public String   getStatsText(int finalScore);
}
