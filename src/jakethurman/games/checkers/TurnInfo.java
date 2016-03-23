package jakethurman.games.checkers;

public class TurnInfo {
	private final long      startTime;
	private final ScoreInfo startScore;
	private       long      endTime;
	private       ScoreInfo endScore;
	
	public TurnInfo(ScoreInfo startScore) {
		this.startScore = startScore;
		this.startTime  = System.nanoTime();
	}
	
	public long getLength() {
		return endTime - startTime; //divide by 1000000 to get milliseconds.
	}
	
	public long getStart() {
		return startTime;
	}
	
	public void setEnd(ScoreInfo endScore) {
		this.endScore = endScore;
		this.endTime  = System.nanoTime();
	}
	
	public long getEnd() {
		return endTime;
	}
	
	public ScoreInfo getScoreAtStart() {
		return startScore;
	}
	
	public ScoreInfo getScoreAtEnd() {
		return endScore;
	}
}
