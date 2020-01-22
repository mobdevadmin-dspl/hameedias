package com.datamation.swdsfa.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeUtils {

    private static Calendar calendar;
    private static SimpleDateFormat dateFormat;
    private static SimpleDateFormat timeFormat;
    private static SimpleDateFormat dateTimeFormat;

    // Find a better name to this
    private static SimpleDateFormat dateNameFormat;
    private static SimpleDateFormat monthFormat;

    /**
     * Use this function to get the day start time in milliseconds.
     *
     * @param millisTime The time of a day to convert.
     * @return The starting time of the given day.
     */
    public static long getDayBeginningTime(long millisTime) {

        if (calendar == null) calendar = Calendar.getInstance();

        calendar.setTimeInMillis(millisTime);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTimeInMillis();
    }

    /**
     * Use this function to get the day end time in milliseconds.
     *
     * @param millisTime The time of a day to convert.
     * @return The ending time of the given day.
     */
    public static long getDayEndTime(long millisTime) {

        if (calendar == null) calendar = Calendar.getInstance();

        calendar.setTimeInMillis(millisTime);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);

        return calendar.getTimeInMillis();
    }

    /**
     * Use this function to get the month start time in milliseconds.
     *
     * @param millisTime The time of a month to convert.
     * @return The starting time of the given month.
     */
    public static long getMonthBeginTime(long millisTime) {

        if (calendar == null) calendar = Calendar.getInstance();

        calendar.setTimeInMillis(millisTime);

        int startDay = calendar.getActualMinimum(Calendar.DATE);

        calendar.set(Calendar.DATE, startDay);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTimeInMillis();
    }

    /**
     * Use this function to get the month end time in milliseconds.
     *
     * @param millisTime The time of a month to convert.
     * @return The ending time of the given month.
     */
    public static long getMonthEndTime(long millisTime) {

        if (calendar == null) calendar = Calendar.getInstance();

        calendar.setTimeInMillis(millisTime);

        int startDay = calendar.getActualMaximum(Calendar.DATE);

        calendar.set(Calendar.DATE, startDay);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);

        return calendar.getTimeInMillis();
    }

    /**
     * Parse time variables in integers into one milliseconds time.
     *
     * @return the actual time in milliseconds
     */
    public static long parseIntoTimeInMillis(int year, int month, int day,
                                             int hour, int minute, int seconds, int milliseconds) {

        if (calendar == null) calendar = Calendar.getInstance();

        calendar.set(year, month, day, hour, minute, seconds);
        calendar.set(Calendar.MILLISECOND, milliseconds);

        return calendar.getTimeInMillis();
    }

    /**
     * Parse time variables in integers into one milliseconds time.
     * Hour, Minutes and Seconds will be 0.
     *
     * @return the actual time in milliseconds
     */
    public static long parseIntoTimeInMillis(int year, int month, int day) {

        if (calendar == null) calendar = Calendar.getInstance();

        calendar.set(year, month, day, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTimeInMillis();
    }

    /**
     * Returns the time in millis of a String date.
     * @param date The string date in the format of (yyyy-MM-dd)
     * @return The time in milliseconds.
     * @throws ParseException When the date is not in the correct format.
     */
    public static long parseIntoTimeInMillis(String date) throws ParseException {

        if(dateFormat == null) dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        return dateFormat.parse(date).getTime();
    }

    /**
     * Returns the time in millis of a String date and time.
     * @param date The string date in the format of (yyyy-MM-dd)
     * @param time The string time in the format of (HH:mm:ss)
     * @return The time in milliseconds.
     * @throws ParseException When the date is not in the correct format.
     */
    public static long parseIntoTimeInMillis(String date, String time) throws ParseException {

        if(dateTimeFormat == null) dateTimeFormat =
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        return dateFormat.parse(date + " " + time).getTime();
    }

    /**
     * Convert the milliseconds time into yyyy-MM-dd format
     *
     * @param timeInMillis The time in milliseconds
     * @return The string of the formatted date
     */
    public static String formatDate(long timeInMillis) {
        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        }

        return dateFormat.format(new Date(timeInMillis));
    }

    /**
     * Convert the milliseconds time into hh:mm:ss format
     *
     * @param timeInMillis The time in milliseconds
     * @return The string of the formatted time
     */
    public static String formatTime(long timeInMillis) {
        if (timeFormat == null) {
            timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        }

        return timeFormat.format(new Date(timeInMillis));
    }

    public static String formatDateTime(long timeInMillis) {

        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        }

        if (timeFormat == null) {
            timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        }

        return dateFormat.format(new Date(timeInMillis)) + " " + timeFormat.format(new Date(timeInMillis));
    }

    /**
     * Get the date as a formatted string of the following format
     * "Saturday, 12 June"
     *
     * @param year  The year to format the date to
     * @param month The month to format to
     * @param day   The day of the month
     * @return The formatted String
     */
    public static String getFormattedDay(int year, int month, int day) {
        if (calendar == null) calendar = Calendar.getInstance();

        calendar.set(year, month, day, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        if (dateNameFormat == null)
            dateNameFormat = new SimpleDateFormat("EEE, d", Locale.getDefault());

        return dateNameFormat.format(new Date(calendar.getTimeInMillis()));
    }

    /**
     * Get the day of week of provided date.
     * 1 : Sunday, 2 : Monday, etc.
     *
     * @param year  The year to format the date to
     * @param month The month to format to
     * @param day   The day of the month
     * @return The day of the week in int (0-6)
     */
    public static int getDayOfWeek(int year, int month, int day) {
        if (calendar == null) calendar = Calendar.getInstance();

        calendar.set(year, month, day, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * Get the date as a formatted string of the following format
     * "Saturday, 12 June"
     *
     * @param timeInMillis The time in milliseconds
     * @return The formatted String
     */
    public static String getFormattedDay(long timeInMillis) {
        if (calendar == null) calendar = Calendar.getInstance();

        calendar.setTimeInMillis(timeInMillis);

        if (dateNameFormat == null)
            dateNameFormat = new SimpleDateFormat("EEE, d", Locale.getDefault());

        return dateNameFormat.format(new Date(calendar.getTimeInMillis()));
    }

    /**
     * Get the day of week of provided date.
     * 1 : Sunday, 2 : Monday, etc.
     *
     * @param timeInMillis The time in milliseconds
     * @return The day of the week in int (0-6)
     */
    public static int getDayOfWeek(long timeInMillis) {
        if (calendar == null) calendar = Calendar.getInstance();

        calendar.setTimeInMillis(timeInMillis);

        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * Returns the appropriate suffix of the provided day
     *
     * @param day The day of the month
     * @return The appropriate suffix
     */
    public static String getDayNumberSuffix(int day) {

//        Log.d("TimeUtil", "Day : " + day);

        if (day >= 11 && day <= 13) {
            return "th";
        }
        switch (day % 10) {
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }
    }

    /**
     * Gets the time in milliseconds and returns the day number
     *
     * @param timeInMillis The time to parse in milliseconds
     * @return The day number
     */
    public static int getDayNumber(long timeInMillis) {
        if (calendar == null) calendar = Calendar.getInstance();

        calendar.setTimeInMillis(timeInMillis);

        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Get the month name of the give time.
     * @param timeInMillis The time in milliseconds
     * @return The month name as String
     */
    public static String getMonthName(long timeInMillis) {

        if(monthFormat == null) monthFormat = new SimpleDateFormat("MMMM", Locale.getDefault());

        return monthFormat.format(new Date(timeInMillis));
    }

    /**
     * Extract the time from the invoice id.
     * Until Sat Nov 20 2286 23:16:40 the time in milliseconds is 13 digits.
     * Therefore it is assumed that the last 13 digits are the correct time.
     * @param invoiceId The invoice ID
     * @return The time in milliseconds of the invoice. 0 if unable to extract.
     */
    public static long extractTimeFromInvoiceId(long invoiceId) {

        String invoiceIdString = String.valueOf(invoiceId);

        if(invoiceIdString.length() < 13) {
            return 0;
        } else {
            String extractedTimeString = invoiceIdString.substring(invoiceIdString.length() - 13, invoiceIdString.length());
            return Long.parseLong(extractedTimeString);
        }

    }

}

