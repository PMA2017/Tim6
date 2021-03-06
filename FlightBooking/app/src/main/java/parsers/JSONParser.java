package parsers;

import model.Town;
import model.Flight;
import rs.contact.Airline;
import rs.reservation.flights.FlightView;
import rs.flightbooking.R;

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

    public static int getRating(JSONObject object)
    {
        int rating = 0;
        try {
            rating = object.getInt("Rating");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return rating;
    }

    public static int getDriveIdFromRating(JSONObject object)
    {
        int driveId = 0;
        try {
            driveId = object.getInt("Drive_ID");
        }catch (JSONException e){
            e.printStackTrace();
        }
        return driveId;
    }

    public static ArrayList getAllRatings(JSONArray array)
    {
        ArrayList array1 = new ArrayList<Integer>();
        for(int i=0; i<array.length(); i++)
        {
            try {
                array1.add(((JSONObject)array.get(i)).getInt("Rating"));
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        return array1;
    }

    public static ArrayList getAllDriveIds(JSONArray array)
    {
        ArrayList array1 = new ArrayList<Integer>();
        for(int i=0; i<array.length(); i++)
        {
            try {
                array1.add(((JSONObject)array.get(i)).getInt("Drive_ID"));
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        return array1;
    }

    public static ArrayList<ArrayList<FlightView>> getAllFlightsFromGetAroungDateResponse(JSONObject object, boolean isReturn, int persons)
    {
        ArrayList<ArrayList<FlightView>> allFlights = new ArrayList<ArrayList<FlightView>>();
        int counter = 10;
        if(!isReturn) {
            counter = 5;
        }
        for(int i = 1; i <= counter; i++) {
            String key = "day" + i;
            String type = "DEPART";
            if(i > 5) {
                type = "RETURN";
            }
            try {
                JSONArray array = object.getJSONArray(key);
                ArrayList<FlightView> flights = new ArrayList<FlightView>();
                for(int j = 0; j < array.length(); j++) {
                    JSONObject flightObject = array.getJSONObject(j);
                    Integer id = flightObject.getInt("id");
                    String price = flightObject.getString("Price");
                    String duration = flightObject.getString("duration");
                    String company = flightObject.getString("company");
                    boolean free = flightObject.getBoolean("free");
                    String startTimeDate = flightObject.getString("StartTimeDate");
                    String endTimeDate = flightObject.getString("EndTimeDate");
                    String startTimeTime = flightObject.getString("StartTimeTime");
                    String endTimeDTime = flightObject.getString("EndTimeTime");

                    Integer finalPrice = new Integer(price) * persons;

                    FlightView flightView = new FlightView();
                    flightView.id = id;
                    flightView.price = finalPrice.toString() + " €";
                    flightView.duration = duration;
                    flightView.company = company;
                    flightView.isFree = free;
                    flightView.dateFrom = startTimeDate;
                    flightView.timeFrom = startTimeTime;
                    flightView.dateTo = endTimeDate;
                    flightView.timeTo = endTimeDTime;
                    flightView.type = type;
                    flights.add(flightView);
                }
                allFlights.add(flights);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return allFlights;
    }

    public static ArrayList<Flight> getFlightsFromArray(JSONArray array)
    {
        ArrayList<Flight> flights = new ArrayList<Flight>();
        for(int i = 0; i < array.length(); i++) {
            try {
                JSONObject object = (JSONObject) array.getJSONObject(i);
                Flight flight = new Flight();

                Integer id = object.getInt("id");
                String price = object.getString("Price");
                String company = object.getString("company");
                String townFrom = object.getString("townFrom");
                String townFromMark = object.getString("townFromMark");
                Double townFromLatitude = object.getDouble("townFromLatitude");
                Double townFromLongitude = object.getDouble("townFromLongitude");
                String townToMark = object.getString("townToMark");
                String townTo = object.getString("townTo");
                Double townToLatitude = object.getDouble("townToLatitude");
                Double townToLongitude = object.getDouble("townToLongitude");




            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return flights;
    }

}
