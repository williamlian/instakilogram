package com.williamlian.instakilogram;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.williamlian.instakilogram.model.Post;

import java.util.ArrayList;

public class PostAdaptor extends ArrayAdapter<Post> {

    public PostAdaptor(Context context, ArrayList<Post> posts) {
        super(context, 0, posts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Post post = getItem(position);
        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.content_post, parent, false);
        TextView tv_title = (TextView)convertView.findViewById(R.id.tv_title);
        TextView tv_user = (TextView)convertView.findViewById(R.id.tv_userName);
        TextView tv_likes = (TextView)convertView.findViewById(R.id.tv_likes);
        ImageView iv_main = (ImageView)convertView.findViewById(R.id.iv_main);
        ImageView iv_user = (ImageView)convertView.findViewById(R.id.iv_profilePhoto);

        tv_title.setText(post.title);
        tv_user.setText(post.author.userName);
        tv_likes.setText(String.format("%d %s",post.likes,(post.likes > 1 ? "likes" : "like")));

        Picasso.with(getContext()).load(post.imageUrl).into(iv_main);
        Picasso.with(getContext()).load(post.author.profilePhoto).into(iv_user);

        return convertView;
    }


}
