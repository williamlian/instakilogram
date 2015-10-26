package com.williamlian.instakilogram.client;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class InstagramService {
    public static final int POPULAR = 1;
    private static final String URL_POPULAR = "/v1/media/popular";

    public static final int COMMENTS = 2;
    private final String URL_COMMENTS = "/v1/media/%s/comments";

    public void getPopular(InstagramServiceCallback callback) throws JSONException {
        InstagramClient.get(URL_POPULAR, null, new ResponseHandler(POPULAR, callback));
    }

    public void getComments(String mediaId, InstagramServiceCallback callback) throws JSONException {
        String url = String.format(URL_COMMENTS, mediaId);
        InstagramClient.get(url, null, new ResponseHandler(COMMENTS, callback));
    }


    public static class ResponseHandler extends JsonHttpResponseHandler {
        private int type;
        private InstagramServiceCallback callback;
        public ResponseHandler(int type, InstagramServiceCallback callback) {
            this.type = type;
            this.callback = callback;
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            callback.onSuccess(type, response);
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
            callback.onSuccess(type, response);
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            callback.onFailure(type, statusCode, responseString);
        }
    }
}
