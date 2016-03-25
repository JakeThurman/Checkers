package jakethurman.games;

import jakethurman.components.SafeNode;
import jakethurman.foundation.Disposable;

public interface StatsGenerator extends Disposable {
	public SafeNode getChart(StatChartType type);
	public String   getStatusText();
}