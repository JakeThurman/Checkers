package jakethurman.games;

import org.json.JSONObject;

public class SimpleScoreData {
	public final long gameEndMs;
	public final int score;
	public final String name;
	
	public SimpleScoreData(long gameEndMs, int score, String playerName) {
		this.gameEndMs = gameEndMs;
		this.score     = score;
		this.name      = playerName;
	}
	
	public String serialize() {
		return "{ \"gameEndMs\": " + gameEndMs + ", \"score\": " + score + ", \"name\": \"" + name + "\" }";
	}
	
	public static SimpleScoreData deserialize(String json) {
		JSONObject obj = new JSONObject(json);
		
		return new SimpleScoreData(obj.getLong("gameEndMs"), obj.getInt("score"), obj.getString("name"));
	}
}
