package jakethurman.games.checkers;

public class TurnInfo {
	private final long      startTime;
	private final ScoreInfo startScore;
	private       long      endTime;
	private       ScoreInfo endScore;
	
	public TurnInfo(ScoreInfo startScore) {
		this.startScore = startScore;
		this.startTime  = System.currentTimeMillis();
	}
	
	public long getLengthMS() {
		return endTime - startTime;
	}
	
	public long getStart() {
		return startTime;
	}
	
	public void setEnd(ScoreInfo endScore) {
		this.endScore = endScore;
		this.endTime  = System.currentTimeMillis();
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
	
	@Override
	public String toString() {
		return "{ \"statrtTime\": " + startTime + ", \"startScore\": " + startScore + 
				", \"endTime\": " + endTime + ", \"endScore\": " + endScore + " }";
	}
}
