package com.michal.scoreflash;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import footballdata.DataSource;
import footballdata.model.match.Match;
import footballdata.model.match.MatchList;

public class MatchesActivity extends AppCompatActivity {

    ListView listView;
    String url = "http://api.football-data.org/v2/competitions/";
    static final String MESSAGE_MATCH="match";
    static final String MESSAGE_COMPETITION_NAME="competition name";
    ArrayList<String> arrayList = new ArrayList<>();

    MatchList allMatchesList;
    ArrayList<Match> matchesFromOneDay = new ArrayList<>();

    String competitionId;
    String competitionName;

    TextView txtDate;

    Calendar calendar;
    MatchesAdapter arrayAdapter;

    SwipeRefreshLayout pullToRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        txtDate = findViewById(R.id.matchesDate);

        setCurrentDate();

        Intent intent = getIntent();
        Log.d("AAAAA",intent.toString());
        competitionId = intent.getStringExtra(MainActivity.MESSAGE_COMPETITION_ID);
        competitionName = intent.getStringExtra(MainActivity.MESSAGE_COMPETITION_NAME);

        TextView textView = findViewById(R.id.textView2);
        textView.setText(competitionName);

        url+=competitionId+"/matches";

        arrayAdapter = new MatchesAdapter(matchesFromOneDay,getApplicationContext());
        listView = findViewById(R.id.listView2);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MatchesActivity.this,SingleMatchActivity.class);
                intent.putExtra(MESSAGE_MATCH,matchesFromOneDay.get(position));
                intent.putExtra(MESSAGE_COMPETITION_NAME,competitionName);
                startActivity(intent);
            }
        });

        setData();
        arrayAdapter.notifyDataSetChanged();


        pullToRefresh = findViewById(R.id.pullToRefreshMatches);

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                if(allMatchesList!=null)
                {
                    setMatches();
                }
                else
                {
                    setData();
                }

                arrayAdapter.notifyDataSetChanged();
                pullToRefresh.setRefreshing(false);
            }
        });

    }


    private void setData()
    {
        DataSource<MatchList> dataSource = new DataSource<>();
        txtDate.setText(getDate());

        try {
            allMatchesList = dataSource.getObjectFromJson(url,MatchList.class);
            setMatches();
        } catch (ExecutionException | InterruptedException e) {
            new AlertDialog.Builder(this)
                    .setTitle("Something gone wrong!")
                    .setMessage(e.getMessage() + "\nCheck your internet connection")
                    .setPositiveButton("Ok",null)
                    .show();
        }
    }

    private void setMatches()
    {
        arrayList.clear();
        setMatchesFromGivenDate();
    }

    private void setCurrentDate()
    {
        calendar = Calendar.getInstance();
    }

    private String getDate()
    {
        Date date=calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
        return dateFormat.format(date);
    }
    private void setMatchesFromGivenDate()
    {
        matchesFromOneDay.clear();

        if(allMatchesList!=null)
        for (Match match : allMatchesList.getMatches())
        {
            if(match.getUtcDate().substring(0,10).equals(getDate()))
            {
                matchesFromOneDay.add(match);
            }
        }
    }

    public void nextDayClicked(View view)
    {
        changeDate(1);
    }

    public void prevDayClicked(View view)
    {
        changeDate(-1);
    }

    private void changeDate(int dateDirection)
    {
        calendar.add(Calendar.DAY_OF_MONTH,dateDirection);
        setMatches();
        txtDate.setText(getDate());
        arrayAdapter.notifyDataSetChanged();
    }

}
