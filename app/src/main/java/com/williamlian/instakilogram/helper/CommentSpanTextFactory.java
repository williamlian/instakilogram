package com.williamlian.instakilogram.helper;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import com.williamlian.instakilogram.R;

public class CommentSpanTextFactory {
    public static SpannableStringBuilder getSpannedString(Context context, String userName, String comment) {
        int userColor = context.getResources().getColor(R.color.colorDarkText);
        int commentColor = context.getResources().getColor(R.color.colorHighlightText);
        ForegroundColorSpan userSpan = new ForegroundColorSpan(userColor);
        ForegroundColorSpan commentSpan = new ForegroundColorSpan(commentColor);
        SpannableStringBuilder spannedString = new SpannableStringBuilder();

        spannedString.append(userName);
        spannedString.setSpan(userSpan, 0, userName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannedString.append(" ");
        spannedString.append(comment);
        spannedString.setSpan(commentColor, userName.length(), comment.length()+userName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannedString;
    }

}
