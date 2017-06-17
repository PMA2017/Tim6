package rs.flights;

/**
 * Created by Rale on 6/11/2017.
 */

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import model.Flight;
import rs.SQLite.FlightDAO;
import rs.authentification.SignupActivity;
import rs.flightbooking.R;
import tools.IServerCaller;
import tools.SendToServer;
import tools.response.ServerResponse;

import static com.loopj.android.http.AsyncHttpClient.log;

public class FlightAddFragment extends Fragment implements OnClickListener, IServerCaller {


    private Button addButton;


    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.ENGLISH);



    Flight flight = null;
    private FlightDAO flightDAO;
    private AddFlightTask task;

    public static final String ARG_ITEM_ID = "flight_add_fragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        flightDAO = new FlightDAO(getActivity());
        _server = new SendToServer(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_flight, container,
                false);

        findViewsById(rootView);

        setListeners();

        return rootView;
    }

   private void setListeners() {

        addButton.setOnClickListener(this);

    }


    public void setFlight() {

        flightDAO = new FlightDAO(getActivity());
        _server = new SendToServer(this);

        SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(this.getContext());

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


    private void findViewsById(View rootView) {

        addButton = (Button) rootView.findViewById(R.id.button_add);

    }

    @Override
    public void onClick(View view) {

            setFlight();
            /*task = new AddFlightTask(getActivity());
            task.execute((Void) null);*/


    }


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
                    String duration = diffHours + ":" + diffMinutes + ":" + diffSeconds;
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




                    task = new AddFlightTask(getActivity());
                    task.execute((Void) null);




                } catch (JSONException e) {

                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }



        } else {
            log.w("greska", "greska");
        }


    }




    public class AddFlightTask extends AsyncTask<Void, Void, Long> {

        private final WeakReference<Activity> activityWeakRef;


        public AddFlightTask(Activity context) {
            this.activityWeakRef = new WeakReference<Activity>(context);
        }

        @Override
        protected Long doInBackground(Void... arg0) {
            long result = flightDAO.save(flight);

            return result;
        }

        @Override
        protected void onPostExecute(Long result) {

            log.w("result", result.toString());

            if (activityWeakRef.get() != null
                    && !activityWeakRef.get().isFinishing()) {


                if (result != -1) {

                    Toast.makeText(activityWeakRef.get(), "Flight Saved",
                            Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
