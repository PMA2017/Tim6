package parsers;

import model.Town;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Nemanja on 5/23/2017.
 */

public class JSONParser {

    public static String[] GetAllTowns(JSONArray array)
    {
        String towns[] = new String[array.length()];
        for(int i = 0; i < array.length(); i++) {
            Town town = new Town();
            try {
                JSONObject object = array.getJSONObject(i);
                town.name = object.getString("Name");
            } catch(JSONException e) {
                e.printStackTrace();
            }
            towns[i] = town.name;
        }
        return towns;
    }

    public static ArrayList<String> getErrorsFromResponse(JSONObject object)
    {
        ArrayList<String> errors = new ArrayList<String>();
        try {
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

}
