package footballdata.model.competition;

import java.util.ArrayList;
import java.util.List;

import footballdata.model.competition.Competition;

/**
 * Class model for Competition list
 * @author remimarion
 */
public class CompetitionList{
	private static String[] freeTierCompetitions = new String[]{"2001","2017","2021","2003","2002","2015","2019","2014","2016","2013","2000","2018"};
	
	//ATTRIBUT
	private String count;
	private List<Competition> competitions;

	public List<Competition> getCompetitions() {
		return competitions;
	}

	public List<Competition> getAvailableCompetitions()
	{
		List<Competition> competitionList = new ArrayList<>();
		for(Competition competition : competitions)
		{
			if(arrayContainsValue(freeTierCompetitions,competition.getId()))
			{
				competitionList.add(competition);
			}
		}
		return competitionList;
	}
	private static boolean arrayContainsValue(String[] arr, String targetValue) {
		for(String s: arr){
			if(s.equals(targetValue))
				return true;
		}
		return false;
	}
}
