package parsers;

import model.Town;
import rs.SQLite.Flight;
import rs.contact.Airline;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.util.ArrayList;

import static com.loopj.android.http.AsyncHttpClient.log;

/**
 * Created by Nemanja on 5/23/2017.
 */

public class JSONParser {

    public static ArrayList<String> getAllTowns(JSONArray array)
    {
        ArrayList<String> towns = new ArrayList<String>();
        for(int i = 0; i < array.length(); i++) {
            Town town = new Town();
            try {
                JSONObject object = array.getJSONObject(i);
                town.name = object.getString("Name");
                town.mark = object.getString("Mark");
            } catch(JSONException e) {
                e.printStackTrace();
            }
            towns.add(town.name + " (" + town.mark + ")");
        }
        return towns;
    }

    public static ArrayList<String> getFlights(JSONArray array)
    {
        int iden;
        ArrayList<String> flights = new ArrayList<String>();
        for(int i = 0; i < array.length(); i++) {
            Flight flight = new Flight();
            try {
                JSONObject object = array.getJSONObject(i);
                iden = object.getInt("FlightDuration");
               log.w("iden", String.valueOf(iden));
                flights.add(String.valueOf(iden));
            } catch(JSONException e) {
                e.printStackTrace();
            }

        }
        return flights;
    }

    public static Airline getAirline(JSONObject object)
    {

        try {
            Airline airline = new Airline(object.getString("Name"),object.getString("Address"), object.getString("PhoneNumber"), object.getString("Town"));
            return airline;
        } catch(JSONException e){
            e.printStackTrace();
        }

        return null;
    }

    public static ArrayList<String> getErrorsFromResponse(JSONObject object)
    {
        ArrayList<String> errors = new ArrayList<String>();
        try {
            object = object.getJSONObject("message");
            JSONArray array = object.getJSONArray("errors");
            for (int i = 0; i < array.length(); i++) {
                JSONObject singleError = array.getJSONObject(i);
                errors.add(singleError.getString("message"));
            }
            return errors;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return errors;
    }

    public static ArrayList<String> getErrorsFromUserResponse(JSONObject object)
    {
        ArrayList<String> errors = new ArrayList<String>();
        if(object.has("message")) {
            try {
                errors.add(object.getString("message"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return errors;
    }

    public static String getUsername(JSONObject object)
    {
        String username = null;
        try {
            username = object.getString("Username");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return username;
    }

    public static int getUserId(JSONObject object)
    {
        int id = 0;
        try {
            id = object.getInt("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return id;
    }

}
