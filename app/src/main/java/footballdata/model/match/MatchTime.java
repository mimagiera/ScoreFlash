package footballdata.model.match;

import java.io.Serializable;

public class MatchTime implements Serializable {

	//ATTRIBUT
	private String homeTeam;
	private String awayTeam;

	public String getHomeTeam() {
		return homeTeam;
	}

	public String getAwayTeam() {
		return awayTeam;
	}
}
