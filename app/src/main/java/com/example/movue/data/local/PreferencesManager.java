package com.example.movue.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.movue.utils.Constants;

public class PreferencesManager {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public PreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setLoginState(boolean isLoggedIn) {
        editor.putBoolean("is_logged_in", isLoggedIn);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean("is_logged_in", false);
    }

    public void saveUsername(String username) {
        editor.putString(Constants.KEY_USERNAME, username);
        editor.apply();
    }

    public String getUsername() {
        return sharedPreferences.getString(Constants.KEY_USERNAME, "");
    }

    public void clear() {
        editor.clear();
        editor.apply();
    }
}