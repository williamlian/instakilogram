package com.williamlian.instakilogram.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CommentList {
    public ArrayList<Comment> comments;
    public int totalComments;

    public CommentList(JSONObject commentsNode) {
        comments = new ArrayList<>();
        try {
            totalComments = commentsNode.getInt("count");
            JSONArray data = commentsNode.getJSONArray("data");
            for(int i = 0; i < data.length(); i++) {
                comments.add(new Comment(data.optJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            totalComments = 0;
            comments = new ArrayList<>();
        }
    }

    public Comment lastComment() {
        return comments.get(comments.size()-1);
    }
}
