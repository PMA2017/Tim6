package parsers;

import model.Town;
import rs.contact.Airline;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.util.ArrayList;

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
            } catch(JSONException e) {
                e.printStackTrace();
            }
            towns.add(town.name);
        }
        return towns;
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

}
