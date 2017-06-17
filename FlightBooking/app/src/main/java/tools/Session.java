package tools;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.content.Context;

import static android.R.attr.id;

/**
 * Created by n.starcev on 6/9/2017.
 */

public class Session {

    private SharedPreferences prefs;

    public Session(Context cntx) {
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setUsername(String username) {
        prefs.edit().putString("username", username).commit();

    }

    public void setId(String id) {
        prefs.edit().putString("id", id).commit();

    }

    public String getUsername() {
        String username = prefs.getString("username","");
        return username;
    }

    public String getId() {
        String id = prefs.getString("id","");
        return id;
    }
}
