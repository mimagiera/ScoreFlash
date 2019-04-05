package footballdata.model.team;

import java.io.Serializable;
import java.util.List;

import footballdata.model.area.Area;
import footballdata.model.competition.Competition;
import footballdata.model.player.Player;

public class Team implements Serializable {
	
	//ATTRIBUT
	private String id;
	private Area area;
	private List<Competition> activeCompetitions;
	private String name;
	private String shortName;
	private String tla;
	private String address;
	private String phone;
	private String website;
	private String email;
	private String founded;
	private String clubColors;
	private String venue;
	private List<Player> squad;
	private String lastUpdated;


	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}

	public List<Player> getSquad() {
		return squad;
	}
}
