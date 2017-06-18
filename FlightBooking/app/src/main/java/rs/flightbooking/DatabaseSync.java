package rs.flightbooking;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import model.Flight;
import rs.SQLite.FlightDAO;
import tools.IServerCaller;
import tools.SendToServer;
import tools.response.ServerResponse;

import static com.loopj.android.http.AsyncHttpClient.log;

/**
 * Created by Milos on 6/18/2017.
 */

public class DatabaseSync extends Service implements IServerCaller{

    public DatabaseSync(Activity activity){
       // this.activity = activity;
    }


    public DatabaseSync(){
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private Button addButton;


    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.ENGLISH);



    Flight flight = null;
    private FlightDAO flightDAO;

    public static final String ARG_ITEM_ID = "flight_add_fragment";


    @Override
    public void onCreate() {
        super.onCreate();
        flightDAO = new FlightDAO(this);
        _server = new SendToServer(this);
        setFlight();
    }

    public void setFlight() {
        _server = new SendToServer(this);
        SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(this);
        String iden = preferences1.getString("id", "");
        int iden1 = Integer.parseInt(iden);
        _server.checkFlights(iden1);

    }
   /* @Override
    public void onResume() {
        log.w("res","res");
        getActivity().setTitle(R.string.add_emp);
        getActivity().getActionBar().setTitle(R.string.add_emp);
        super.onResume();
        log.w("res1","res1");
    }*/


    private SendToServer _server;
    private JSONArray array;
    public static ArrayList<Integer> list_integer =new ArrayList<Integer>();

    @Override
    public void OnServerResponse(ServerResponse response) {


        if(response.statusCode == 200) {

            array = response.responseArray;


            for(int i=0; i<array.length(); i++) {
                try {
                    JSONObject json_data = array.getJSONObject(i);
                    int id = json_data.getInt("id");

                    list_integer.add(id);



                    String time = json_data.getString("StartTime");
                    String end_time = json_data.getString("endTime");
                    String[] time1 = time.split("T");
                    String[] time11 = time1[1].split("\\.");

                    String[] end_time1 = end_time.split("T");
                    String[] end_time11 = end_time1[1].split("\\.");

                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    Date d1 = sdf.parse(time11[0]);
                    Date d2 = sdf.parse(end_time11[0]);

                    long diff = d2.getTime() - d1.getTime();
                    long diffSeconds = diff / 1000 % 60;
                    long diffMinutes = diff / (60 * 1000) % 60;
                    long diffHours = diff / (60 * 60 * 1000);
                    String duration = diffHours + "h :" + diffMinutes + "m :" + diffSeconds+ "s";
                    log.w("usao20", String.valueOf(duration));
                    flight = new Flight();

                    flight.setId(id);
                    flight.setTownFrom(json_data.getString("townFrom"));
                    flight.setTownTo(json_data.getString("townTo"));
                    flight.setTownFromMark(json_data.getString("townFromMark"));
                    flight.setTownToMark(json_data.getString("townToMark"));
                    flight.setPrice(json_data.getString("Price"));
                    flight.setCompany(json_data.getString("company"));
                    flight.setDate1(time1[0]);
                    flight.setDate2(end_time1[0]);
                    flight.setTime1(time11[0]);
                    flight.setTime2(end_time11[0]);
                    flight.setDuration(duration);
                    flight.setTownFromLatitude(json_data.getDouble("townFromLatitude"));
                    flight.setTownToLatitude( json_data.getDouble("townToLatitude"));
                    flight.setTownFromLongitude(json_data.getDouble("townFromLongitude"));
                    flight.setTownToLongitude(json_data.getDouble("townToLongitude"));
                   // task = new AddFlightTask(activity);
                   // task.execute((Void) null);
                    flightDAO.save(flight);



                } catch (JSONException e) {

                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }



        } else {
            log.w("greska", "greska");
        }


    }


}
