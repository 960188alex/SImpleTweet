package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class TimelineActivity extends AppCompatActivity {

    public static final String TAG = "TimelineActivity";
    TwitterClient client;
    RecyclerView rvTweets;
    List<Tweet> tweets;
    TweetsAdapter adapter;
    SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);


        client = TwitterApp.getRestClient(this);
        // use these data to build timeline

        // get swipeContainer
        swipeContainer = findViewById(R.id.swipeContainer);
        // set color
//        swipeContainer.setColorSchemeColors(android.R.color.holo_blue_bright,
//                android.R.color.holo_green_light,
//                android.R.color.holo_orange_light,
//                android.R.color.holo_red_light
//        );
        // set listener
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i(TAG,"refresh success");
                populateHomeTimeline();
            }
        });


        // find recycler view
        rvTweets = findViewById(R.id.rvTweets);
        //  initiate tweets and adapter
        tweets = new ArrayList<>();
        adapter = new TweetsAdapter(this, tweets);
        //  set the  adapter for recycler view in layout
        rvTweets.setLayoutManager(new LinearLayoutManager(this));
        rvTweets.setAdapter(adapter);

        populateHomeTimeline();
        Log.i(TAG, "on create timelineA success");

    }

    private void populateHomeTimeline() {
        client.getHomeTimeline( new JsonHttpResponseHandler(){
        @Override
        public void onSuccess(int statusCode, Headers headers, JSON json) {
            Log.i(TAG, "on success" + json.toString());

            JSONArray jsonArray = json.jsonArray;
            try {
                Log.i(TAG, "on success JSON araray" );
                adapter.clear();
                adapter.addAll(Tweet.fromJsonArray(jsonArray));
                swipeContainer.setRefreshing(false); // turn off the loading icon
//                tweets.addAll(Tweet.fromJsonArray(jsonArray));
//                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
            Log.e(TAG, "on failure" + response, throwable);
        }
    });
    }


}