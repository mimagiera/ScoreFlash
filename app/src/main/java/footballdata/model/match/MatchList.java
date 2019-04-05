package footballdata.model.match;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import footballdata.model.competition.Competition;


public class MatchList {

	//ATTRIBUT
	private String count;
	private Competition competition;
	private List<Match> matches;

	public List<Match> getMatches() {
		return matches;
	}

	public Competition getCompetition() {
		return competition;
	}

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
		this.count = count;
	}

	public void setCompetition(Competition competition) {
		this.competition = competition;
	}

	public void setMatches(List<Match> matches) {
		this.matches = matches;
	}
}
