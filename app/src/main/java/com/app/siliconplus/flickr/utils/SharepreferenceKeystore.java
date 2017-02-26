package com.app.siliconplus.flickr.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class SharepreferenceKeystore {
    private static SharepreferenceKeystore store;
    private SharedPreferences SP;
    private static String filename = "SILICONPLUS_KEYS";

    private SharepreferenceKeystore(Context context) {
        SP = context.getApplicationContext().getSharedPreferences(filename, 0);
    }

    public static SharepreferenceKeystore getInstance(Context context) {
        if (store == null) {
            store = new SharepreferenceKeystore(context);
        }
        return store;
    }

    public void putInt(String key, int value) {
        Editor editor;
        editor = SP.edit();
        editor.putInt(key, value);
        editor.commit();
    }


    public int getIntValue(String key) {//Log.v("Keystore","GET from "+key);
        return SP.getInt(key, 0);
    }

    public void put(String key, String value) {
        Editor editor;
        editor = SP.edit();
        editor.putString(key, value);
        editor.commit();
    }


    public String get(String key) {//Log.v("Keystore","GET from "+key);
        return SP.getString(key, null);
    }

    public void clear() {
        Editor editor;
        editor = SP.edit();
        editor.clear();
        editor.commit();
    }

    public void remove() {
        Editor editor;
        editor = SP.edit();
        editor.remove(filename);
        editor.commit();
    }
}
