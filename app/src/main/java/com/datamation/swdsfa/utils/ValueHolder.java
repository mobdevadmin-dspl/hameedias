package com.datamation.swdsfa.utils;


import java.util.ArrayList;

/**
 *
 * Holds the commonly used request codes and other variables throughout the app life cycle.
 */
public class ValueHolder {


    public static final int CODE_OK = 1;
    public static final int CODE_ERROR = -1;
    public static final int CODE_EXIT = 2;

    public static final int REQUEST_PRINT = 10001;
    public static final int REQUEST_COLLECTION = 10002;
    public static final int REQUEST_RETURN = 10003;
    public static final int REQUEST_DISCOUNT = 10004;
    public static final int REQUEST_BALANCE_STOCK = 10005;

    public static final int ALARM_REQUEST_TRACKER = 10021;
    public static final int ALARM_REQUEST_NOTIFIER = 10022;

//    public static final int NOTIFICATION_DAY_END_REMINDER = 10031;
//    public static final int NOTIFICATION_DAY_END_FINAL_REMINDER = 10032;

//    public static final int KEY_CATEGORIZED = 101001;
//    public static final int KEY_OTHER = 101002;

    public static final String KEY_STARTING_SEQUENCE = "starting_sequence";

//    public static final String KEY_TEMPORARY_ROUTE = "temporary_route";

    public static final String KEY_PROCEED_TO_PRINT = "proceed_to_print";
    public static final String KEY_PROCEED_TO_RETURN = "proceed_to_return";
    public static final String KEY_PROCEED_TO_DISCOUNT = "proceed_to_discount";
    public static final String KEY_PROCEED_TO_COLLECTION = "proceed_to_collection";

    public static final int REQUEST_CAMERA_SHOP_FRONT_IMAGE = 12001;
    public static final int REQUEST_CAMERA_SHOP_SHOWCASE_IMAGE = 12002;
    public static final int REQUEST_CAMERA_SHOP_PROMO_ONE_IMAGE = 12003;
    public static final int REQUEST_CAMERA_SHOP_PROMO_TWO_IMAGE = 12004;
    public static  final int REQUEST_IMAGE_CAPTURE_FOR_EXPENSES=12005;
    public static final int REQUEST_CAPTURE_FOR_CASHBANKING=12006;

    public static final long DAY_IN_MILLIS = 1000 * 60 * 60 * 24;
    public static final int LOCATION_SWITCH_DELAY = 10 * 1000;

    public static final long MINUTE_IN_MILLIS = 1000 * 60;

    public static final long RECEIVER_LOCATION_ACCESS_TIMEOUT = 1000 * 30;

    public static final int FRAGMENT_STATE_ATTACH = 20051;

    public static final int FRAGMENT_REDIRECT_TO_SELECT_ROUTE = 20052;
    public static final int FRAGMENT_REDIRECT_TO_SELECT_DEALER = 20053;
    public static final int FRAGMENT_REDIRECT_TO_DASHBOARD = 20054;

    public static final int FRAGMENT_UPDATE_TITLE = 20055;

    public static final int ENTER_DEALER_DETAIL = 20066;


}
