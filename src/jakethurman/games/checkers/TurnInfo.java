package jakethurman.games.checkers;

/*
 * Stores information about a single turn.
 */
public class TurnInfo {
	/* Data */
	private final long      startTime;
	private final ScoreInfo startScore;
	private       long      endTime;
	private       ScoreInfo endScore;
	
	/* C'tor */
	public TurnInfo(ScoreInfo startScore) {
		this.startScore = startScore;
		
		 // Say that the turn began when the object is created
		this.startTime  = System.currentTimeMillis();
	}
	
	// Gets the length of the turn in milliseconds
	public long getLengthMS() {
		return endTime - startTime;
	}
	
	// Gets the start time of the turn in milliseconds
	public long getStart() {
		return startTime;
	}
	
	// Records that the turn has ended and the score at that time	
	public void setEnd(ScoreInfo endScore) {
		this.endScore = endScore;
		this.endTime  = System.currentTimeMillis(); // The game ended when this function is called
	}
	
	// Gets the end time in milliseconds
	public long getEnd() {
		return endTime;
	}
	
	// Gets the score at the start of the turn
	public ScoreInfo getScoreAtStart() {
		return startScore;
	}
	
	// Gets the scrore at the end of the turn
	public ScoreInfo getScoreAtEnd() {
		return endScore;
	}
	
	@Override
	public String toString() {
		return "{ \"statrtTime\": " + startTime + ", \"startScore\": " + startScore + 
				", \"endTime\": " + endTime + ", \"endScore\": " + endScore + " }";
	}
}
