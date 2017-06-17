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

    public String getFilghtId() {
        String flightId = prefs.getString("flightId","");
        return flightId;
    }

    public void setFilghtId(String flightId) {
        prefs.edit().putString("flightId", flightId).commit();

    }


    public String getTownFrom() {
        String townFrom = prefs.getString("townFrom","");
        return townFrom;
    }

    public void setTownFrom(String townFrom) {
        prefs.edit().putString("townFrom", townFrom).commit();

    }

    public String getTownTo() {
        String townTo = prefs.getString("townTo","");
        return townTo;
    }

    public void setTownTo(String townTo) {
        prefs.edit().putString("townTo", townTo).commit();

    }

    public String getTownFromMark() {
        String townFromMark = prefs.getString("townFromMark","");
        return townFromMark;
    }
    public void setTownFromMark(String townFromMark) {
        prefs.edit().putString("townFromMark", townFromMark).commit();

    }

    public String getTownToMark() {
        String townToMark = prefs.getString("townToMark","");
        return townToMark;
    }
    public void setTownToMark(String townToMark) {
        prefs.edit().putString("townToMark", townToMark).commit();

    }

    public String getPrice() {
        String price = prefs.getString("price","");
        return price;
    }

    public void setPrice(String price) {
        prefs.edit().putString("price", price).commit();

    }


    public String getCompany() {
        String company = prefs.getString("company","");
        return company;
    }

    public void setCompany(String company) {
        prefs.edit().putString("company", company).commit();

    }
    public String getDate1() {
        String date1 = prefs.getString("date1","");
        return date1;
    }
    public void setDate1(String date1) {
        prefs.edit().putString("date1", date1).commit();

    }

    public String getDate2() {
        String date2 = prefs.getString("date2","");
        return date2;
    }
    public void setDate2(String date2) {
        prefs.edit().putString("date2", date2).commit();

    }

    public String getTime1() {
        String time1 = prefs.getString("time1","");
        return time1;
    }
    public void setTime1(String time1) {
        prefs.edit().putString("time1", time1).commit();

    }



    public String getTime2() {
        String time2 = prefs.getString("time2","");
        return time2;
    }

    public void setTime2(String time2) {
        prefs.edit().putString("time2", time2).commit();

    }
    public String getDuration() {
        String duration = prefs.getString("duration","");
        return duration;
    }

    public void setDuration(String duration) {
        prefs.edit().putString("duration", duration).commit();

    }



}
