package jakethurman.games;

import org.json.JSONObject;

// Stores simple score data about a high score.
public class SimpleScoreData {
	public final long gameEndMs;
	public final int score;
	public final String name;
	
	// C'tor
	public SimpleScoreData(long gameEndMs, int score, String playerName) {
		this.gameEndMs = gameEndMs;
		this.score     = score;
		this.name      = playerName;
	}
	
	// TODO: this isn't real json generation
	public String serialize() {
		return "{ \"gameEndMs\": " + gameEndMs + ", \"score\": " + score + ", \"name\": \"" + name + "\" }";
	}
	
	// Create an object from a json string
	public static SimpleScoreData deserialize(String json) {
		JSONObject obj = new JSONObject(json);
		
		return new SimpleScoreData(obj.getLong("gameEndMs"), obj.getInt("score"), obj.getString("name"));
	}
}
