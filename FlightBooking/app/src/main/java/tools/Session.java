package tools;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.content.Context;

/**
 * Created by n.starcev on 6/9/2017.
 */

public class Session {

    private SharedPreferences prefs;

    public Session(Context cntx) {
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setUsername(String usename) {
        prefs.edit().putString("usename", usename).commit();
    }

    public String getUsername() {
        String usename = prefs.getString("usename","");
        return usename;
    }
}
