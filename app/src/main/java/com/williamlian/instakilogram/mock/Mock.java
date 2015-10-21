package com.williamlian.instakilogram.mock;

import com.williamlian.instakilogram.model.Post;
import com.williamlian.instakilogram.model.User;

import java.util.ArrayList;

public class Mock {

    public static ArrayList<Post> getPopularPhotos() {
        ArrayList<Post> posts = new ArrayList<>();
        User user = new User("", "William Lian");
        posts.add(new Post("", "Title 1", 1, user));
        posts.add(new Post("", "Title 2", 5, user));
        return posts;
    }

}
