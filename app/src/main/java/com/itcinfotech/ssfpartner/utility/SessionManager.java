package com.itcinfotech.ssfpartner.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionManager {
    private SharedPreferences pref;
    private Editor editor;
    private int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "SSF_PARTNER";
    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String SESSION_TOKEN = "SESSION_TOKEN";
    private static final String PSID = "PSID";
    private static final String IS_MANAGER = "IS_MANAGER";
    private static final String IS_TOKEN_UPDATED = "IS_TOKEN_UPDATED";
    private static final String IS_CAB_DRIVER = "IS_CAB_DRIVER";
    private static final String USER_NAME = "USER_NAME";

    private static SessionManager instance = null;

    // Constructor
    private SessionManager(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        editor.commit();
    }

    public static SessionManager getInstance(Context context) {

        if (instance == null) {
            instance = new SessionManager(context);
        }

        return instance;
    }

    public void clearSharedPreferences(){

        editor.clear();
        editor.commit();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public void setIsLogin(boolean value2) {
        editor.putBoolean(IS_LOGIN, value2);
        editor.commit();
    }

    public boolean getIsManager() {
        return pref.getBoolean(IS_MANAGER, false);
    }

    public void setIsManager(boolean isManager) {
        editor.putBoolean(IS_MANAGER, isManager);
        editor.commit();
    }

    public String getPSID() {
        return pref.getString(PSID, null);
    }

    public void storePSID(String psid) {
        editor.putString(PSID, psid);
        editor.commit();
    }

    public String getSessionToken() {
        return pref.getString(SESSION_TOKEN, null);
    }

    public void setSessionToken(String token) {
        editor.putString(SESSION_TOKEN, token);
        editor.commit();
    }

    public boolean isTokenUpdated() {
        return pref.getBoolean(IS_TOKEN_UPDATED, false);
    }

    public void setTokenUpdated(boolean value2) {
        editor.putBoolean(IS_TOKEN_UPDATED, value2);
        editor.commit();
    }

    public boolean getIsCabDriver() {
        return pref.getBoolean(IS_CAB_DRIVER, false);
    }

    public void setIsCabDriver(boolean isManager) {
        editor.putBoolean(IS_CAB_DRIVER, isManager);
        editor.commit();
    }

    public String getUserName() {
        return pref.getString(USER_NAME, null);
    }

    public void setUserName(String token) {
        editor.putString(USER_NAME, token);
        editor.commit();
    }
}