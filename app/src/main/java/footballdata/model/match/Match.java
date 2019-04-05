package footballdata.model.match;

import java.io.Serializable;
import java.util.List;
import footballdata.model.competition.*;
import footballdata.model.team.Team;

public class Match implements Serializable {

	//ATTRIBUT
	private String id;
	private Competition competition;
	private CompetitionSeason season;
	private String utcDate;
	private String status;
	private String venue;
	private String matchday;
	private String stage;
	private String group;
	private String lastUpdated;
	private Team homeTeam;
	private Team awayTeam;
	private MatchScore score;
	private List<MatchReferees> referees;



	public Competition getCompetition() {
		return competition;
	}

	public Team getHomeTeam() {
        return homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public MatchScore getScore() {
        return score;
    }

    public String getUtcDate() {
        return utcDate;
    }
}
