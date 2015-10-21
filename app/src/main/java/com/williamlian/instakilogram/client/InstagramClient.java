package com.williamlian.instakilogram.client;

import com.loopj.android.http.*;

public class InstagramClient {
    private static final String BASE_URL = "https://api.instagram.com";
    private static final String CLIENT_ID = "65cb927d41e846f7be0ea079196ee97f";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        if(params == null)
            params = new RequestParams();
        params.add("client_id", CLIENT_ID);
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
