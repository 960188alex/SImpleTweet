package com.codepath.apps.restclienttemplate.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Tweet {
    public static final String TAG = "Model Tweet";
    public String body;
    public String createdAt;
    public User user;

    //for parceler
    public Tweet(){}

    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.body = jsonObject.getString("text");
        tweet.createdAt = jsonObject.getString("created_at");
        // since this is also a json object, we are goint to wrap it with a fromJson method
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        Log.i(TAG, "parse success" + tweet.createdAt);
        return tweet;
    }

    // actually. we want to return a list of tweet array
    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            Log.i(TAG, String.valueOf(jsonArray.length()));
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return tweets;
    }

}
