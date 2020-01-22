package com.datamation.swdsfa.settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.datamation.swdsfa.view.ActivityLogin;


public class UserSessionManager {

    private Context context;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public UserSessionManager(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences("login", 0);
        editor = preferences.edit();
    }


    public void Logout() {
        editor.clear();
        editor.commit();
        Intent intent = new Intent(context, ActivityLogin.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }
}
