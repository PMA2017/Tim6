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

//
public class FlightAddFragment extends Fragment implements OnClickListener, IServerCaller {

    // UI references
    private EditText flTownFormEtxt;
    private EditText flTownToEtxt;
    private EditText flDateFromEtxt;
    private EditText flDateToEtxt;
    private Button addButton;
    private Button resetButton;

    SignupActivity _signupActivity;

    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.ENGLISH);

    DatePickerDialog datePickerDialog;
    DatePickerDialog datePickerDialog1;
    Calendar dateCalendar;
    Calendar dateCalendar1;

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

        //For orientation change.
        if (savedInstanceState != null) {

            dateCalendar = Calendar.getInstance();
            if (savedInstanceState.getLong("dateCalendar") != 0)
                dateCalendar.setTime(new Date(savedInstanceState
                        .getLong("dateCalendar")));

            dateCalendar1 = Calendar.getInstance();
            if (savedInstanceState.getLong("dateCalendar1") != 0)
                dateCalendar1.setTime(new Date(savedInstanceState
                        .getLong("dateCalendar1")));

        }


        return rootView;
    }

   private void setListeners() {

        addButton.setOnClickListener(this);

    }

    protected void resetAllFields() {
        flTownFormEtxt.setText("");
        flTownToEtxt.setText("");
        flDateFromEtxt.setText("");
        flDateToEtxt.setText("");
    }

    public void setFlight() {
        /*flight = new Flight();*/

        SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(this.getContext());

        String iden = preferences1.getString("id", "");


        int iden1 = Integer.parseInt(iden);

        _server.checkFlights(iden1);

       /* SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.getContext());

        String id = preferences.getString("flightId", "");
        int id1 = Integer.parseInt(id);



        flight.setId(id1);
        flight.setTownFrom(preferences.getString("townFrom", ""));
        flight.setTownTo(preferences.getString("townTo", ""));
        flight.setTownFromMark(preferences.getString("townFromMark", ""));
        flight.setTownToMark(preferences.getString("townToMark", ""));
        flight.setPrice(preferences.getString("price", ""));
        flight.setCompany(preferences.getString("company", ""));
        flight.setDate1(preferences.getString("date1", ""));
        flight.setDate2(preferences.getString("date2", ""));
        flight.setTime1(preferences.getString("time1", ""));
        flight.setTime2(preferences.getString("time2", ""));
        flight.setDuration(preferences.getString("duration", ""));
*/



    }
   /* @Override
    public void onResume() {
        log.w("res","res");
        getActivity().setTitle(R.string.add_emp);
        getActivity().getActionBar().setTitle(R.string.add_emp);
        super.onResume();
        log.w("res1","res1");
    }*/

    @Override
    public void onSaveInstanceState(Bundle outState) {

        if (dateCalendar != null)
            outState.putLong("dateCalendar", dateCalendar.getTime().getTime());

        if (dateCalendar1 != null)
            outState.putLong("dateCalendar1", dateCalendar1.getTime().getTime());
    }

    private void findViewsById(View rootView) {
        /*flTownFormEtxt = (EditText) rootView.findViewById(R.id.etxt_townFrom);
        flTownToEtxt = (EditText) rootView.findViewById(R.id.etxt_townTo);
        flDateFromEtxt = (EditText) rootView.findViewById(R.id.etxt_dateFrom);
        flDateFromEtxt.setInputType(InputType.TYPE_NULL);
        flDateToEtxt = (EditText) rootView.findViewById(R.id.etxt_dateTo);
        flDateToEtxt.setInputType(InputType.TYPE_NULL);*/
        addButton = (Button) rootView.findViewById(R.id.button_add);
     //   resetButton = (Button) rootView.findViewById(R.id.button_reset);
    }

    @Override
    public void onClick(View view) {
        if (view == flDateFromEtxt) {
            datePickerDialog.show();
        }else if(view == flDateToEtxt) {
            datePickerDialog1.show();
        }else if (view == addButton) {
            setFlight();
            /*task = new AddFlightTask(getActivity());
            task.execute((Void) null);*/

        } else if (view == resetButton) {
            resetAllFields();
        }
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

                    /*SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.getContext());

                    preferences.edit().putString("flightId", String.valueOf(id)).commit();
                    preferences.edit().putString("townFrom", json_data.getString("townFrom")).commit();
                    preferences.edit().putString("townTo", json_data.getString("townTo")).commit();
                    preferences.edit().putString("townFromMark", json_data.getString("townFromMark")).commit();
                    preferences.edit().putString("townToMark", json_data.getString("townToMark")).commit();
                    preferences.edit().putString("price", json_data.getString("Price")).commit();
                    preferences.edit().putString("company", json_data.getString("company")).commit();
                    preferences.edit().putString("date1", time1[0]).commit();
                    preferences.edit().putString("date2", end_time1[0]).commit();
                    preferences.edit().putString("time1", time11[0]).commit();
                    preferences.edit().putString("time2", end_time11[0]).commit();
                    preferences.edit().putString("duration", duration).commit();
                    preferences.edit().putInt("fromLatitude", json_data.getInt("townFromLatitude")).commit();
                    preferences.edit().putInt("toLatitude", json_data.getInt("townToLatitude")).commit();
                    preferences.edit().putInt("fromLongitude", json_data.getInt("townFromLongitude")).commit();
                    preferences.edit().putInt("toLongitude", json_data.getInt("townToLongitude")).commit();*/


                    flight = new Flight();


                    //SharedPreferences preferences2 = PreferenceManager.getDefaultSharedPreferences(this.getContext());

                    //String ide = preferences2.getString("flightId", "");
                    //int ide1 = Integer.parseInt(ide);

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
                    flight.setTownFromLatitude(json_data.getInt("townFromLatitude"));
                    flight.setTownToLatitude( json_data.getInt("townToLatitude"));
                    flight.setTownFromLongitude(json_data.getInt("townFromLongitude"));
                    flight.setTownToLongitude(json_data.getInt("townToLongitude"));


                    /*flight.setId(ide1);
                    flight.setTownFrom(preferences2.getString("townFrom", ""));
                    flight.setTownTo(preferences2.getString("townTo", ""));
                    flight.setTownFromMark(preferences2.getString("townFromMark", ""));
                    flight.setTownToMark(preferences2.getString("townToMark", ""));
                    flight.setPrice(preferences2.getString("price", ""));
                    flight.setCompany(preferences2.getString("company", ""));
                    flight.setDate1(preferences2.getString("date1", ""));
                    flight.setDate2(preferences2.getString("date2", ""));
                    flight.setTime1(preferences2.getString("time1", ""));
                    flight.setTime2(preferences2.getString("time2", ""));
                    flight.setDuration(preferences2.getString("duration", ""));
                    flight.setTownFromLatitude(preferences2.getInt("fromLatitude", 0));
                    flight.setTownToLatitude(preferences2.getInt("toLatitude", 0));
                    flight.setTownFromLongitude(preferences2.getInt("fromLongitude", 0));
                    flight.setTownToLongitude(preferences2.getInt("toLongitude", 0));*/

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
            log.w("flightDB4","flightDB4");
            long result = flightDAO.save(flight);
            log.w("flightDB5","flightDB5");

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
