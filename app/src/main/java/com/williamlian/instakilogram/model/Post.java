package com.williamlian.instakilogram.model;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.williamlian.instakilogram.client.InstagramService;
import com.williamlian.instakilogram.client.InstagramServiceCallback;
import com.williamlian.instakilogram.helper.DateHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Post {
    public String imageUrl;
    public String title;
    public int likes;
    public User author;
    public Long createdAt;
    public String location;

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

    public String getAge() {
        return DateHelper.getRalativeTime(this.createdAt);
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
                    JSONObject captionNode = !postData.isNull("caption") ? postData.getJSONObject("caption") : null;
                    JSONObject userNode = postData.getJSONObject("user");
                    JSONObject locationNode = postData.optJSONObject("location");

                    String image = imageNode.getJSONObject("standard_resolution").getString("url");
                    String caption = captionNode == null ? "" : captionNode.getString("text");
                    int likes = likesNode.getInt("count");
                    Long createdTime = null;


                    String userName = userNode.getString("username");
                    String profileImage = userNode.getString("profile_picture");
                    User user = new User(profileImage, userName);

                    Post post = new Post(image, caption, likes, user);
                    if(!postData.isNull("created_time")) {
                        createdTime = Long.parseLong(postData.optString("created_time"));
                        post.createdAt = createdTime;
                    }
                    if(locationNode != null) {
                        post.location = locationNode.getString("name");
                        Log.i("post", "location obatained " + post.location);
                    }
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
