package com.williamlian.instakilogram.client;

import org.json.JSONArray;
import org.json.JSONObject;

public interface InstagramServiceCallback {

    void onSuccess(JSONArray data);

    void onSuccess(JSONObject data);

    void onFailure(int statusCode, String message);
}
