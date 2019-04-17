package com.michal.scoreflash;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import footballdata.model.match.Match;

public class MatchesAdapter extends ArrayAdapter<Match> implements View.OnClickListener
{

    private ArrayList<Match> myList;

    private Context context;

    @Override
    public void onClick(View v) {

    }

    static class MyViewHolder {
        TextView txtHomeName;
        TextView txtAwayName;
        TextView txtHomeScore;
        TextView txtAwayScore;
        TextView txtTime;
    }

    @Override
    public int getCount()
    {
        return myList.size();
    }

    MatchesAdapter(ArrayList<Match> myList, Context context) {
        super(context, R.layout.match_item, myList);
        this.context = context;
        this.myList = myList;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent)
    {
        MyViewHolder viewHolder;

        if(v==null)
        {
            LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.match_item, parent, false);
            viewHolder = new MyViewHolder();
            viewHolder.txtHomeName = v.findViewById(R.id.textView_homeTeamName);
            viewHolder.txtAwayName =v.findViewById(R.id.textView_awayTeamName);
            viewHolder.txtHomeScore=v.findViewById(R.id.textView_homeTeamScore);
            viewHolder.txtAwayScore=v.findViewById(R.id.textView_awayTeamScore);
            viewHolder.txtTime=v.findViewById(R.id.textView_time);
            v.setTag(viewHolder);
        }
        else
        {
            viewHolder = (MyViewHolder) v.getTag();
        }

        Match match;
        match=myList.get(position);
        if(match!=null)
        {
            viewHolder.txtHomeName.setText(match.getHomeTeam().getName());
            viewHolder.txtAwayName.setText(match.getAwayTeam().getName());
            String homeScore = match.getScore().getFullTime().getHomeTeam();
            String awayScore = match.getScore().getFullTime().getAwayTeam();
            if(homeScore == null) homeScore="--";
            if(awayScore == null) awayScore="--";
            viewHolder.txtHomeScore.setText(homeScore);
            viewHolder.txtAwayScore.setText(awayScore);
            viewHolder.txtTime.setText(match.getUtcDate().substring(11,16));
        }
        return v;
    }
}