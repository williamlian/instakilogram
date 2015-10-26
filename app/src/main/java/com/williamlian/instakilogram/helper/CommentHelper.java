package com.williamlian.instakilogram.helper;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.williamlian.instakilogram.model.Comment;

public class CommentHelper {
    public static TextView createdCommentTextView(Context context, Comment comment, int margin) {
        TextView tv_comment = new TextView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, margin, 0, margin);
        tv_comment.setLayoutParams(layoutParams);

        SpannableStringBuilder commentText = CommentSpanTextFactory.getSpannedString(context, comment);
        tv_comment.setText(commentText);

        return tv_comment;
    }

    public static TextView createdCommentTextView(Context context, Comment comment) {
        return createdCommentTextView(context, comment, 0);
    }

}
