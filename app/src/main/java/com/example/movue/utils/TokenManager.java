package com.example.movue.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenManager {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public TokenManager(Context context) {
        sharedPreferences = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveTokens(String accessToken, String refreshToken) {
        editor.putString(Constants.KEY_TOKEN, accessToken);
        editor.putString(Constants.KEY_REFRESH_TOKEN, refreshToken);
        editor.apply();
    }

    public String getAccessToken() {
        return sharedPreferences.getString(Constants.KEY_TOKEN, null);
    }

    public String getRefreshToken() {
        return sharedPreferences.getString(Constants.KEY_REFRESH_TOKEN, null);
    }

    public void clearTokens() {
        editor.remove(Constants.KEY_TOKEN);
        editor.remove(Constants.KEY_REFRESH_TOKEN);
        editor.apply();
    }
}