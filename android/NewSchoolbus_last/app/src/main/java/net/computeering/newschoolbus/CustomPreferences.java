package net.computeering.newschoolbus;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Seongho on 2016-03-23.
 * <p>
 * // init
 * CustomPreferences pref = new CustomPreferences(this);
 * <p>
 * // set
 * pref.put(CustomPreferences.PREF_USER_AGREEMENT, true);
 * <p>
 * // get
 * pref.getValue(CustomPreferences.PREF_USER_AGREEMENT, false);
 **/

public class CustomPreferences {
    public final static String AUTOLOGIN_prevalue = "AUTOLOGIN_prevalue";
    public final static String AMIFIRST_prefvalue = "AMIFIRST_prefvalue";
    public final static String NAME_prefvalue = "NAME_prefvalue";
    public final static String EMAIL_prefvalue = "EMAIL_prefvalue";
    public final static String PASSWORD_prefvalue = "PASSWORD_prefvalue";
    public final static String Bluetooth_prefvalue = "Bluetooth_prefvalue";
    public final static String TODAY_prevalue = "TODAY_prevalue";

    static Context mContext;

    private final String PREF_NAME = "net.computeering.pref";

    public CustomPreferences(Context c) {
        mContext = c;
    }

    public void put(String key, String value) {
        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString(key, value);
        editor.commit();
    }

    public void put(String key, boolean value) {
        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putBoolean(key, value);
        editor.commit();
    }

    public void put(String key, int value) {
        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putInt(key, value);
        editor.commit();
    }

    public String getValue(String key, String dftValue) {
        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE);

        try {
            return pref.getString(key, dftValue);
        } catch (Exception e) {
            return dftValue;
        }

    }

    public int getValue(String key, int dftValue) {
        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);

        try {
            return pref.getInt(key, dftValue);
        } catch (Exception e) {
            return dftValue;
        }

    }

    public boolean getValue(String key, boolean dftValue) {
        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);

        try {
            return pref.getBoolean(key, dftValue);
        } catch (Exception e) {
            return dftValue;
        }
    }
}
