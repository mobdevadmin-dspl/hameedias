package com.datamation.hmdsfa.api;

import android.content.Context;
import android.util.Log;

import com.datamation.hmdsfa.R;
import com.datamation.hmdsfa.helpers.SharedPref;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
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

        //add 2020-03-19 becz sockettimeoutexception
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                // .callTimeout(2, TimeUnit.MINUTES)
                .connectTimeout(50, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS);

        pref = SharedPref.getInstance(contextt);
        String domain = pref.getBaseURL();
        Log.d("baseURL>>>>>>>>>", domain);
        baseURL = domain + contextt.getResources().getString(R.string.connection_string);

        //commented 2020-03-19 becz sockettimeoutexception
        //        if (retrofit==null) {
//            retrofit = new Retrofit.Builder()
//                    .baseUrl(baseURL)
//                    .addConverterFactory(GsonConverterFactory.create());
//                   // .build();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create());
        builder.client(httpClient.build());

        if(retrofit == null){
            retrofit = builder.build();
        }
        return retrofit;
    }
}
