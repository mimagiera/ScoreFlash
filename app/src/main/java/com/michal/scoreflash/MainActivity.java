package com.michal.scoreflash;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import footballdata.DataSource;
import footballdata.model.competition.Competition;
import footballdata.model.competition.CompetitionList;


public class MainActivity extends AppCompatActivity {

    ListView listView;
    String url = "http://api.football-data.org/v2/competitions";
    List<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    SwipeRefreshLayout pullToRefresh;

    public static final String MESSAGE_COMPETITION_ID="competition id";
    public static final String MESSAGE_COMPETITION_NAME="competition name";

    Map<String, String> mapOfCompetitions = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,arrayList);
        listView = findViewById(R.id.listView1);
        listView.setAdapter(arrayAdapter);

        setData();
        arrayAdapter.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, MatchesActivity.class);
                String competitionName = arrayList.get(position);
                String competitionId = mapOfCompetitions.get(competitionName);
                intent.putExtra(MESSAGE_COMPETITION_ID,competitionId);
                intent.putExtra(MESSAGE_COMPETITION_NAME,competitionName);
                startActivity(intent);
            }
        });

        pullToRefresh = findViewById(R.id.pullToRefreshMain);

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setData();
                arrayAdapter.notifyDataSetChanged();
                pullToRefresh.setRefreshing(false);
            }
        });

    }
    private void setData() {
        DataSource<CompetitionList> dataSource = new DataSource<>();
        CompetitionList competitionList;

            try {

                arrayList.clear();
                competitionList = dataSource.getObjectFromJson(url, CompetitionList.class);
                for (Competition competition : competitionList.getAvailableCompetitions()) {
                    mapOfCompetitions.put(competition.getName(), competition.getId());
                    arrayList.add(competition.getName());
                }
            } catch (ExecutionException | InterruptedException e) {

                new AlertDialog.Builder(this)
                        .setTitle("Something gone wrong!")
                        .setMessage(e.getMessage() + "\nCheck your internet connection")
                        .setPositiveButton("Ok",null)
                        .show();
            }
    }
}