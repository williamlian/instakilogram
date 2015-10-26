package com.williamlian.instakilogram.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Comment {
    public User commenter;
    public String comment;
    public Long commentedAt;

    public Comment(JSONObject commentNode) {
        try {
            comment = commentNode.optString("text");
            commentedAt = commentNode.getLong("created_time");
            commenter = new User(commentNode.getJSONObject("from"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
