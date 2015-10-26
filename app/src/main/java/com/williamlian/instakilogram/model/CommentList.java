package com.williamlian.instakilogram.model;

import com.williamlian.instakilogram.client.InstagramService;
import com.williamlian.instakilogram.client.InstagramServiceCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CommentList {
    public ArrayList<Comment> comments;
    public Integer totalComments;

    public CommentList() {

    }

    public CommentList(JSONObject commentsNode) {
        comments = new ArrayList<>();
        try {
            totalComments = commentsNode.optInt("count");
            JSONArray data = commentsNode.getJSONArray("data");
            for(int i = 0; i < data.length(); i++) {
                comments.add(new Comment(data.optJSONObject(i)));
            }
            if(totalComments == null) {
                totalComments = comments.size();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            totalComments = 0;
            comments = new ArrayList<>();
        }
    }

    public Comment getAt(int i) {
        return comments.get(i);
    }

    public int getLength() {
        return  comments.size();
    }

    public static void GetComments(String mediaId, GetCommentsCallback callback) throws JSONException {
        InstagramService instagramService = new InstagramService();
        instagramService.getComments(mediaId, new GetCommentsClientCallback(callback));
    }

    public static interface GetCommentsCallback {
        void onLoad(CommentList comments);
        void onFail(String error);
    }

    public static class GetCommentsClientCallback implements InstagramServiceCallback {
        GetCommentsCallback onLoadCallback;
        public GetCommentsClientCallback(GetCommentsCallback callback) {
            onLoadCallback = callback;
        }

        @Override
        public void onSuccess(int type, JSONArray data) {
            onLoadCallback.onFail("Unexpected data: " + data.toString());
        }

        @Override
        public void onSuccess(int type, JSONObject data) {
            CommentList list = new CommentList(data);
            onLoadCallback.onLoad(list);
        }

        @Override
        public void onFailure(int type, int statusCode, String message) {
            onLoadCallback.onFail(String.format("Failed to load Instagram. Error code %d: %s",statusCode,message));
        }
    }
}
