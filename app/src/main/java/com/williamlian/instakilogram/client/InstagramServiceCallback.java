package com.williamlian.instakilogram.client;

import org.json.JSONArray;
import org.json.JSONObject;

public interface InstagramServiceCallback {

    void onSuccess(int type, JSONArray data);

    void onSuccess(int type, JSONObject data);

    void onFailure(int type, int statusCode, String message);
}
