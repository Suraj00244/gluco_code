package org.medcada.android.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import org.medcada.android.object.ProfileDataBean;

public class Preferences {
    static final String LOCALE_CLEANED = "PREF_LOCALE_CLEANED";

    private final SharedPreferences sharedPreferences;

    public Preferences(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public boolean isLocaleCleaned() {
        return sharedPreferences.getBoolean(LOCALE_CLEANED, false);
    }

    public void saveLocaleCleaned() {
        sharedPreferences.edit().putBoolean(LOCALE_CLEANED, true).apply();
    }

    public void setProfileData(String json) {
        sharedPreferences.edit().putString("profiledata", json).apply();
    }

    public ProfileDataBean getProfiledata() {
        ProfileDataBean bean = new Gson().fromJson(sharedPreferences.getString("profiledata", ""), ProfileDataBean.class);
        if (bean!=null){
            return bean;
        }else return new ProfileDataBean();
    }
}
