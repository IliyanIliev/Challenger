package com.mentormate.academy.challenger.adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mentormate.academy.challenger.R;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

public class CustomChallengesAdapter extends ParseQueryAdapter<ParseObject> {

    private int lastLevelUnlocked = 0;

    public CustomChallengesAdapter(Context context, final String storyId, String title) {
        super(context, new QueryFactory<ParseObject>() {
            public ParseQuery create() {
                ParseObject obj = ParseObject.createWithoutData("Story", storyId);
                ParseQuery query = new ParseQuery("Challange");
                query.whereEqualTo("story", obj);
                query.orderByAscending("number");
                query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
                return query;
            }
        });

        lastLevelUnlocked = 0;
        final String storyTitle = title.replaceAll("\\s+","");
        ParseUser currentUser = ParseUser.getCurrentUser();
        String username = currentUser.getString("username");

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Ranking");
        query.whereEqualTo("username", username);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject ranking, ParseException e) {
                if (ranking != null) {
                    lastLevelUnlocked = ranking.getInt(storyTitle);
                } else {
                    Log.d("ERR", "Could not get story last level!");
                }
            }
        });
    }

    @Override
    public boolean isEnabled(int position) {
        int lastLevelPos = lastLevelUnlocked-1;
        if(position>lastLevelPos){
            return false;
        }
        return true;
    }

    // Customize the layout by overriding getItemView
    @Override
    public View getItemView(ParseObject object, View v, ViewGroup parent) {
        if (v == null) {
            v = View.inflate(getContext(), R.layout.challange_item, null);
        }

        super.getItemView(object, v, parent);

        // Add the level number
        TextView levelNum = (TextView) v.findViewById(R.id.level_num);
        int levelNumber = object.getInt("number");
        levelNum.setText("Level" + levelNumber);

        ImageView locked = (ImageView) v.findViewById(R.id.lock_level);
        ImageView completed = (ImageView) v.findViewById(R.id.complete_level);
        if(levelNumber < lastLevelUnlocked){
            completed.setVisibility(View.VISIBLE);
            locked.setVisibility(View.INVISIBLE);
        }
        else if(levelNumber == lastLevelUnlocked){
            locked.setVisibility(View.INVISIBLE);
            completed.setVisibility(View.INVISIBLE);
        }
        else {
            locked.setVisibility(View.VISIBLE);
            completed.setVisibility(View.INVISIBLE);
        }
        // Add the title view
        TextView titleTextView = (TextView) v.findViewById(R.id.challenge_name);
        titleTextView.setText(object.getString("name"));

        return v;
    }
}
