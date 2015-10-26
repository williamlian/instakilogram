package com.williamlian.instakilogram.model;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    public String profilePhoto;
    public String userName;

    public User(String profilePhoto, String userName) {
        this.profilePhoto = profilePhoto;
        this.userName = userName;
    }

    public User(JSONObject userNode) {
        try {
            userName = userNode.getString("username");
            profilePhoto = userNode.getString("profile_picture");
        } catch (JSONException e) {
            e.printStackTrace();
            userName = "ERROR";
        }

    }
}
