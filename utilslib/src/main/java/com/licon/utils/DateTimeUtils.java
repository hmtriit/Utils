package com.licon.utils;

/**
 * Created by FRAMGIA\khairul.alam.licon on 26/2/16.
 */

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import con.licon.data.AppData;

public class DateTimeUtils {

    private static final int CLOCK_UNIT_1000 = 1000;
    private static final int CLOCK_UNIT_365 = 365;
    private static final int CLOCK_UNIT_90 = 90;
    private static final int CLOCK_UNIT_60 = 60;
    private static final int CLOCK_UNIT_45 = 45;
    private static final int CLOCK_UNIT_48 = 48;
    private static final int CLOCK_UNIT_31 = 31;
    private static final int CLOCK_UNIT_30 = 30;
    private static final int CLOCK_UNIT_24 = 24;
    private static final int CLOCK_UNIT_12 = 12;
    private static final int CLOCK_UNIT_1 = 1;
    private static final int CLOCK_UNIT_0 = 0;

    public static ArrayList<SimpleDateFormat> getSimpleDateFormats () {
        ArrayList<SimpleDateFormat> allDateFormats = new ArrayList<SimpleDateFormat>();

        /** set-1 */
        allDateFormats.add(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)); // 0
        allDateFormats.add(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)); // 1
        allDateFormats.add(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US)); // 2
        allDateFormats.add(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US)); // 3

        /** set-2 */
        allDateFormats.add(new SimpleDateFormat("EEE, d MMM yy HH:mm:ss z", Locale.US)); // 4
        allDateFormats.add(new SimpleDateFormat("EEE, d MMM yy HH:mm z", Locale.US)); // 5
        allDateFormats.add(new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z", Locale.US)); // 6
        allDateFormats.add(new SimpleDateFormat("EEE, d MMM yyyy HH:mm z", Locale.US)); // 7

        /** set-3 */
        allDateFormats.add(new SimpleDateFormat("EEE d MMM yy HH:mm:ss z", Locale.US)); // 8
        allDateFormats.add(new SimpleDateFormat("EEE d MMM yy HH:mm z", Locale.US)); // 9
        allDateFormats.add(new SimpleDateFormat("EEE d MMM yyyy HH:mm:ss z", Locale.US)); // 10
        allDateFormats.add(new SimpleDateFormat("EEE d MMM yyyy HH:mm z", Locale.US)); // 11

        /** set-4 */
        allDateFormats.add(new SimpleDateFormat("d MMM yy HH:mm z", Locale.US)); // 12
        allDateFormats.add(new SimpleDateFormat("d MMM yy HH:mm:ss z", Locale.US)); // 13
        allDateFormats.add(new SimpleDateFormat("d MMM yyyy HH:mm z", Locale.US)); // 14
        allDateFormats.add(new SimpleDateFormat("d MMM yyyy HH:mm:ss z", Locale.US)); // 15

        /** set-5 */
        allDateFormats.add(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())); // 16
        allDateFormats.add(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())); // 17
        allDateFormats.add(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault())); // 18
        allDateFormats.add(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault())); // 19

        /** set-6 */
        allDateFormats.add(new SimpleDateFormat("EEE, d MMM yy HH:mm:ss z", Locale.getDefault())); // 20
        allDateFormats.add(new SimpleDateFormat("EEE, d MMM yy HH:mm z", Locale.getDefault())); // 21
        allDateFormats.add(new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z", Locale.getDefault())); // 22
        allDateFormats.add(new SimpleDateFormat("EEE, d MMM yyyy HH:mm z", Locale.getDefault())); // 23

        /** set-7 */
        allDateFormats.add(new SimpleDateFormat("EEE d MMM yy HH:mm:ss z", Locale.getDefault())); // 24
        allDateFormats.add(new SimpleDateFormat("EEE d MMM yy HH:mm z", Locale.getDefault())); // 25
        allDateFormats.add(new SimpleDateFormat("EEE d MMM yyyy HH:mm:ss z", Locale.getDefault())); // 26
        allDateFormats.add(new SimpleDateFormat("EEE d MMM yyyy HH:mm z", Locale.getDefault())); // 27

        /** set-8 */
        allDateFormats.add(new SimpleDateFormat("d MMM yy HH:mm z", Locale.getDefault())); // 28
        allDateFormats.add(new SimpleDateFormat("d MMM yy HH:mm:ss z", Locale.getDefault())); // 29
        allDateFormats.add(new SimpleDateFormat("d MMM yyyy HH:mm z", Locale.getDefault())); // 30
        allDateFormats.add(new SimpleDateFormat("d MMM yyyy HH:mm:ss z", Locale.getDefault())); // 31

        return allDateFormats;
    }

    public static Date parseDateFromString(String date, SimpleDateFormat simpleDateFormat) {
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone(AppData.APP_TIME_ZONE));
            try {
                SimpleDateFormat enUSFormat = new SimpleDateFormat(simpleDateFormat.toPattern(), Locale.US);
                return enUSFormat.parse(date);
            } catch (ParseException e) {
            }
        return null;
    }

    public static String parseDateToString(Date date, SimpleDateFormat simpleDateFormat) {
        if(date == null) {
            return null;
        }
        return simpleDateFormat.format(date);
    }

    public static Long parseDateToLong(Date date) {
        if (date != null) {
            return date.getTime();
        }
        return null;
    }

    public static Long getCurrentDate() {
        return System.currentTimeMillis();
    }

    public static String getTimeDifferenceUnit(long input_time, Context context)
    {
        long difference = CLOCK_UNIT_0;
        Long date = getCurrentDate();

        if(date > input_time)
        {
            difference = date - input_time;
            final long seconds = difference/CLOCK_UNIT_1000;
            final long minutes = seconds/CLOCK_UNIT_60;
            final long hours = minutes/CLOCK_UNIT_60;
            final long days = hours/CLOCK_UNIT_24;
            final long months = days/CLOCK_UNIT_31;
            final long years = days/CLOCK_UNIT_365;

            if (seconds < CLOCK_UNIT_0) {
                return context.getString(R.string.history_not_yet);
            } else if (seconds < CLOCK_UNIT_60) {
                return seconds == CLOCK_UNIT_1 ? context.getString(R.string.history_one_second_ago) :
                        String.format(context.getString(R.string.history_seconds_ago), seconds);
            } else if (seconds < (CLOCK_UNIT_60 * CLOCK_UNIT_60)) {
                return context.getString(R.string.history_one_minute_ago);
            } else if (seconds < (CLOCK_UNIT_45 * CLOCK_UNIT_60)) {
                // 45 * 60
                return String.format(context.getString(R.string.history_minutes_ago), minutes);
            } else if (seconds < (CLOCK_UNIT_60 * CLOCK_UNIT_90)) {
                // 90 * 60
                return context.getString(R.string.history_one_hour_ago);
            } else if (seconds < (CLOCK_UNIT_24 * CLOCK_UNIT_60 * CLOCK_UNIT_60)) {
                // 24 * 60 * 60
                return String.format(context.getString(R.string.history_hours_ago), hours);
            } else if (seconds < CLOCK_UNIT_48 * CLOCK_UNIT_60 * CLOCK_UNIT_60) {
                // 48 * 60 * 60
                return context.getString(R.string.history_yesterday);
            } else if (seconds < CLOCK_UNIT_30 * CLOCK_UNIT_24 * CLOCK_UNIT_60 * CLOCK_UNIT_60) {
                // 30 * 24 * 60 * 60
                return String.format(context.getString(R.string.history_hours_ago), days);
            } else if (seconds < CLOCK_UNIT_12 * CLOCK_UNIT_30 * CLOCK_UNIT_24 * CLOCK_UNIT_60 * CLOCK_UNIT_60) {
                // 12 * 30 * 24 * 60 * 60
                return months == CLOCK_UNIT_1 ? context.getString(R.string.history_one_month_ago) :
                        String.format(context.getString(R.string.history_months_ago), months);
            } else {
                return years == CLOCK_UNIT_1 ? context.getString(R.string.history_one_year_ago) :
                        String.format(context.getString(R.string.history_years_ago), years);
            }
        }
        return null;
    }
}