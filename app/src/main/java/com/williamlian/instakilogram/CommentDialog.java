package com.williamlian.instakilogram;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.owlike.genson.Genson;
import com.williamlian.instakilogram.helper.CommentHelper;
import com.williamlian.instakilogram.helper.CommentSpanTextFactory;
import com.williamlian.instakilogram.model.Comment;
import com.williamlian.instakilogram.model.CommentList;

import org.json.JSONException;


public class CommentDialog extends DialogFragment {
    public CommentDialog() {

    }

    public static CommentDialog newInstance(String mediaId) {
        CommentDialog dialog = new CommentDialog();
        Bundle args = new Bundle();
        args.putString("media_id", mediaId);
        dialog.setArguments(args);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_comments, container);
    }

    @Override
    public void onResume() {
        int width = (int)(getResources().getDisplayMetrics().widthPixels * 0.9);
        int height = getResources().getDisplayMetrics().heightPixels / 2;
        getDialog().getWindow().setLayout(width, height);
        super.onResume();
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().setTitle(R.string.comment_dialog_title);

        String mediaId = getArguments().getString("media_id", null);
        if(mediaId != null) {
            try {
                CommentList.GetComments(mediaId, new CommentList.GetCommentsCallback() {
                    @Override
                    public void onLoad(CommentList commentList) {
                        showComments(commentList);
                    }

                    @Override
                    public void onFail(String error) {
                        Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
                    }
                });
            } catch (JSONException e) {
                Toast.makeText(getContext(),e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void showComments(CommentList comments) {
        LinearLayout commentsLayout = (LinearLayout)getView().findViewById(R.id.ll_comment_dialog);
        commentsLayout.removeAllViews();
        for (int i = 0; i < comments.getLength(); i++) {
            Comment comment = comments.getAt(i);
            TextView tv_comment = CommentHelper.createdCommentTextView(getContext(), comment, 5);
            commentsLayout.addView(tv_comment);
        }
    }

}
