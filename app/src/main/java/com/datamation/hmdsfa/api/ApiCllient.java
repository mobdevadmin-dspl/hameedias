package com.datamation.hmdsfa.api;

import android.content.Context;
import android.util.Log;

import com.datamation.hmdsfa.R;
import com.datamation.hmdsfa.helpers.SharedPref;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Rashmi on 7/15/2019.
 */

public class ApiCllient {
    private final String LOG_TAG = ApiCllient.class.getSimpleName();
    private static String baseURL;
    private static SharedPref pref;
    private static Retrofit retrofit = null;

//    public ApiCllient(Context contextt) {
//        this.context = contextt;
//        pref = SharedPref.getInstance(context);
//        String domain = pref.getBaseURL();
//        Log.d("baseURL>>>>>>>>>", domain);
//        baseURL = domain + context.getResources().getString(R.string.connection_string);
//    }

    public static Retrofit getClient(Context contextt) {
        pref = SharedPref.getInstance(contextt);
        String domain = pref.getBaseURL();
        Log.d("baseURL>>>>>>>>>", domain);
        baseURL = domain + contextt.getResources().getString(R.string.connection_string);
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
