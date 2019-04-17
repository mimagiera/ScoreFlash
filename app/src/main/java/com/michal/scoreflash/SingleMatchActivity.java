package com.michal.scoreflash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import footballdata.DataSource;
import footballdata.model.match.Match;
import footballdata.model.player.Player;
import footballdata.model.team.Team;

import static com.michal.scoreflash.MatchesActivity.MESSAGE_COMPETITION_NAME;
import static com.michal.scoreflash.MatchesActivity.MESSAGE_MATCH;

public class SingleMatchActivity extends AppCompatActivity {

    Match match;
    String date;
    String competitionName;
    List<Player> homePlayers = new ArrayList<>();
    List<Player> awayPlayers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_match);

        Intent intent = getIntent();
        match=(Match) intent.getSerializableExtra(MESSAGE_MATCH);
        competitionName=intent.getStringExtra(MESSAGE_COMPETITION_NAME);
        date = match.getUtcDate();

        TextView txtCompetition = findViewById(R.id.textView_singleMatch_Competition);
        TextView txtDate = findViewById(R.id.textView_singleMatch_date);
        TextView txtHomeTeam = findViewById(R.id.singleMatch_homeTeamName);
        TextView txtAwayTeam = findViewById(R.id.singleMatch_awayTeamName);
        TextView txtHomeScore = findViewById(R.id.singleMatch_homeTeamScore);
        TextView txtAwayScore = findViewById(R.id.singleMatch_awayTeamScore);

        txtCompetition.setText(competitionName);

        String dateText=date.substring(0,10)+" "+date.substring(11,16);
        txtDate.setText(dateText);

        txtHomeTeam.setText(match.getHomeTeam().getName());
        txtAwayTeam.setText(match.getAwayTeam().getName());
        txtHomeScore.setText(match.getScore().getFullTime().getHomeTeam());
        txtAwayScore.setText(match.getScore().getFullTime().getAwayTeam());

        ListView listViewHomePlayers = findViewById(R.id.singleMatch_listView_homePlayers);
        ListView listViewAwayPlayers = findViewById(R.id.singleMatch_listView_awayPlayers);

        setHomePlayers();
        setAwayPlayers();

        List<String> homePlayersString;
        List<String> awayPlayersString;

        homePlayersString=setNamesFromPlayerList("home");
        awayPlayersString=setNamesFromPlayerList("away");

        listViewHomePlayers.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,homePlayersString));
        listViewAwayPlayers.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,awayPlayersString));

    }

    private List<String> setNamesFromPlayerList(String team) {
        List<Player> playerArrayList;
        switch (team) {
            case "home":
                playerArrayList = homePlayers;
                break;
            case "away":
                playerArrayList = awayPlayers;
                break;
            default:
                throw new IllegalArgumentException("Team side: " + team + " not found");
        }
        List<String> names = new ArrayList<>();
        String oneLine;
        if(playerArrayList!=null)
        for(Player player : playerArrayList)
        {
            oneLine=player.getName();
            names.add(oneLine);
        }

        return names;
    }


    private void setHomePlayers() {
        String homeId = match.getHomeTeam().getId();
        homePlayers=getListOfPlayersFromTeam(homeId);
    }

    private void setAwayPlayers() {
        String awayId=match.getAwayTeam().getId();
        awayPlayers=getListOfPlayersFromTeam(awayId);
    }

    private List<Player> getListOfPlayersFromTeam(String teamId)
    {
        String url = "http://api.football-data.org/v2/teams/"+teamId;
        Team team = new Team();
        DataSource dataSource = new DataSource<>();

        try {
            team =(Team) dataSource.getObjectFromJson(url,Team.class);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return team.getSquad();
    }

}
