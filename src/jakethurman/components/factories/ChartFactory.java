package jakethurman.components.factories;

import jakethurman.components.SafeNode;
import jakethurman.foundation.Disposable;

public class ChartFactory implements Disposable {
	public SafeNode createLineChart() {
		return SafeNode.NONE;
	}
	
	@Override
	public void dispose() {
		//Nothing to dispose
	}
}
