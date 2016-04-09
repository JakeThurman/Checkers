package jakethurman.games;

import org.json.JSONObject;

public class SimpleScoreData {
	public final long gameEndMs;
	public final int score;
	
	public SimpleScoreData(long gameEndMs, int score) {
		this.gameEndMs = gameEndMs;
		this.score     = score;
	}
	
	public String serialize() {
		return "{ \"gameEndMs\": " + gameEndMs + ", \"score\": " + score + " }";
	}
	
	public static SimpleScoreData deserialize(String json) {
		JSONObject obj = new JSONObject(json);
		
		return new SimpleScoreData(obj.getLong("gameEndMs"), obj.getInt("score"));
	}
}
