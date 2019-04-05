package footballdata.model.competition;

import java.io.Serializable;
import java.util.List;

import footballdata.model.area.Area;

public class Competition implements Serializable {
	
	//ATTRIBUT
	private String id;
	private Area area;
	private String name;
	private String code;
	private String plan;
	private CompetitionSeason currentSeason;
	private List<CompetitionSeason> seasons;
	private String lastUpdated;

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Area getArea() {
		return area;
	}
}
