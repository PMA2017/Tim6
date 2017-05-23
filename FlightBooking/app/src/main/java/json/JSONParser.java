package json;

import model.Town;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import java.util.ArrayList;
/**
 * Created by Nemanja on 5/23/2017.
 */

public class JSONParser {
    public static ArrayList<Town> GetAllTowns(JSONArray array) {

        ArrayList<Town> towns = new ArrayList<Town>();
        for(int i = 0; i < array.length(); i++) {
            Town town = new Town();
            try {
                JSONObject object = array.getJSONObject(i);
                town.name = object.getString("Name");
            } catch(JSONException e) {
                e.printStackTrace();
            }
            towns.add(town);
        }
        return towns;
    }
}
