package com.williamlian.instakilogram;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.williamlian.instakilogram.helper.CircleTransform;
import com.williamlian.instakilogram.helper.CommentHelper;
import com.williamlian.instakilogram.helper.CommentSpanTextFactory;
import com.williamlian.instakilogram.model.Post;

import java.util.ArrayList;


public class PostAdaptor extends ArrayAdapter<Post> {

    private final static int COMMENTS_TO_SHOW = 2;
    public PostAdaptor(Context context, ArrayList<Post> posts) {
        super(context, 0, posts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Post post = getItem(position);
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.content_post, parent, false);
        TextView tv_title = (TextView) convertView.findViewById(R.id.tv_title);
        TextView tv_user = (TextView) convertView.findViewById(R.id.tv_userName);
        TextView tv_likes = (TextView) convertView.findViewById(R.id.tv_likes);
        TextView tv_age = (TextView) convertView.findViewById(R.id.tv_age);
        TextView tv_location = (TextView) convertView.findViewById(R.id.tv_location);
        ImageView iv_main = (ImageView) convertView.findViewById(R.id.iv_main);
        ImageView iv_user = (ImageView) convertView.findViewById(R.id.iv_profilePhoto);
        ImageView iv_videoIcon = (ImageView) convertView.findViewById(R.id.iv_video_icon);

        tv_title.setText(CommentSpanTextFactory.getSpannedString(getContext(), post.author.userName, post.title));
        tv_user.setText(post.author.userName);
        String likeTemplate = getContext().getString(post.likes > 1 ? R.string.likes : R.string.likes);
        tv_likes.setText(String.format(likeTemplate, post.likes));
        tv_age.setText(post.getAge());
        if (post.location != null) {
            tv_location.setText(post.location);
            tv_location.setVisibility(View.VISIBLE);
        } else {
            tv_location.setVisibility(View.GONE);
        }

        //View all link

        TextView tv_viewAllComments = (TextView) convertView.findViewById(R.id.tv_view_all_comments);
        if (post.comments.getLength() > 2) {
            String template = getContext().getString(R.string.view_all_comments);
            tv_viewAllComments.setText(String.format(template, post.comments.totalComments));
            tv_viewAllComments.setVisibility(View.VISIBLE);

            tv_viewAllComments.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity activity = (MainActivity) getContext();
                    FragmentManager fm = activity.getSupportFragmentManager();
                    CommentDialog commentDialog = CommentDialog.newInstance(post.mediaId);
                    commentDialog.show(fm, "comment_show_dialog");
                }
            });

        } else {
            tv_viewAllComments.setVisibility(View.GONE);
        }

        //comments
        LinearLayout ll_comment = (LinearLayout) convertView.findViewById(R.id.ll_comments);
        for (int i = 0; i < COMMENTS_TO_SHOW; i++) {
            int commentLength = post.comments.getLength();
            if (commentLength > i) {
                //reuse tv_comment if there is one
                if (ll_comment.getChildCount() > i) {
                    TextView tv_comment = (TextView) ll_comment.getChildAt(i);
                    tv_comment.setText(CommentSpanTextFactory.getSpannedString(getContext(), post.comments.getAt(commentLength - 1 - i)));
                } else {
                    TextView tv_comment = CommentHelper.createdCommentTextView(getContext(), post.comments.getAt(commentLength - 1 - i));
                    ll_comment.addView(tv_comment, i);
                }
            }
        }

        //video
        if(post.videoUrl != null) {
            iv_videoIcon.setVisibility(View.VISIBLE);
            iv_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent videoPlayIntent = new Intent(getContext(), VideoViewActivity.class);
                    videoPlayIntent.putExtra("url", post.videoUrl);
                    getContext().startActivity(videoPlayIntent);
                }
            });

        } else {
            iv_videoIcon.setVisibility(View.INVISIBLE);
        }

        Picasso.with(getContext()).
                load(post.imageUrl).
                placeholder(R.drawable.placeholder).
                into(iv_main);

        Picasso.with(getContext()).
                load(post.author.profilePhoto).
                placeholder(R.drawable.placeholder).
                transform(new CircleTransform()).
                into(iv_user);

        convertView.setClickable(false);
        convertView.setOnClickListener(null);
        return convertView;
    }
}
