package footballdata.model.match;

import java.io.Serializable;

public class MatchScore implements Serializable {
	
	//ATTRIBUT
	private String winner;
	private String duration;
	private MatchTime fullTime;
	private MatchTime halfTime;
	private MatchTime extraTime;
	private MatchTime penalties;

	public MatchTime getFullTime() {
		return fullTime;
	}
}
