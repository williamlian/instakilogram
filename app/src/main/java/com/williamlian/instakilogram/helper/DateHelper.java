package com.williamlian.instakilogram.helper;

import android.text.format.DateUtils;

import java.util.Date;

public class DateHelper {

    public static String getRalativeTime(Long ts) {
        if(ts == null)
            return "";
        Date now = new Date();
        now.getTime();
        CharSequence str = DateUtils.getRelativeTimeSpanString(ts * 1000, now.getTime(), DateUtils.HOUR_IN_MILLIS);
        return str.toString();
    }
}
