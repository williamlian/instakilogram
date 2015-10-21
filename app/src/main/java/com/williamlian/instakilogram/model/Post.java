package com.williamlian.instakilogram.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.williamlian.instakilogram.client.InstagramService;
import com.williamlian.instakilogram.client.InstagramServiceCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Post {
    public String imageUrl;
    public String title;
    public int likes;
    public User author;

    public Post(String imageUrl, String title, int likes, User author) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.likes = likes;
        this.author = author;
    }

    public static void getPopular(GetPopularCallback callback) throws JSONException{
        InstagramService instagramService = new InstagramService();
        instagramService.getPopular(new InstagramPopularServiceCallback(callback));
    }

    public static interface GetPopularCallback {
        void onLoad(ArrayList<Post> posts);
        void onFail(String error);
    }

    public static class InstagramPopularServiceCallback implements InstagramServiceCallback {
        private GetPopularCallback onLoadCallback;
        private ObjectMapper mapper = new ObjectMapper();
        private ArrayList<Post> posts = new ArrayList<>();

        public InstagramPopularServiceCallback(GetPopularCallback callback) {
            onLoadCallback = callback;
        }

        @Override
        public void onSuccess(JSONArray data) {
            onLoadCallback.onFail("Unexpected data: " + data.toString());
        }

        @Override
        public void onSuccess(JSONObject data) {
            try {
                JSONArray postsData = data.getJSONArray("data");
                for(int i = 0; i < postsData.length(); i++) {
                    JSONObject postData = postsData.getJSONObject(i);
                    JSONObject imageNode = postData.getJSONObject("images");
                    JSONObject likesNode = postData.getJSONObject("likes");
                    JSONObject captionNode = postData.has("caption") ? postData.getJSONObject("caption") : null;
                    JSONObject userNode = postData.getJSONObject("user");

                    String image = imageNode.getJSONObject("standard_resolution").getString("url");
                    String caption = captionNode == null ? "" : captionNode.getString("text");
                    int likes = likesNode.getInt("count");

                    String userName = userNode.getString("full_name");
                    String profileImage = userNode.getString("profile_picture");
                    User user = new User(profileImage, userName);

                    Post post = new Post(image, caption, likes, user);
                    posts.add(post);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            onLoadCallback.onLoad(posts);
        }

        @Override
        public void onFailure(int statusCode, String message) {
            onLoadCallback.onFail(String.format("Failed to load Instagram. Error code %d: %s",statusCode,message));
        }
    }
}
