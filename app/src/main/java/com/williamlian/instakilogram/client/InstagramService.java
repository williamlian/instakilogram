package com.williamlian.instakilogram.client;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class InstagramService {
    private final String POPULAR = "/v1/media/popular";

    public void getPopular(final InstagramServiceCallback callback) throws JSONException {
        InstagramClient.get(POPULAR, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                callback.onSuccess(response);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                callback.onSuccess(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                callback.onFailure(statusCode, responseString);
            }
        });
    }
}
