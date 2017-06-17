package rs.flights;

/**
 * Created by Rale on 6/11/2017.
 */

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.zip.Inflater;

import rs.SQLite.CustomCommentDialogFragment;
import rs.SQLite.CustomFlightDialogFragment;
import rs.SQLite.CustomInterface;
import model.Flight;
import rs.SQLite.FlightDAO;
import rs.flightbooking.R;
import tools.IServerCaller;
import tools.SendToServer;
import tools.response.ServerResponse;

import static com.loopj.android.http.AsyncHttpClient.log;

/**/



public  class FlightListFragment extends Fragment implements View.OnClickListener, IServerCaller {

    public static final String ARG_ITEM_ID = "flight_list";

    Activity activity;
    ListView flightListView;
    ArrayList<Flight> flights;

    FlightListAdapter flightListAdapter;
    FlightDAO flightDAO;
    FlightAddFragment flightadd;
    ImageButton comment;
    Flight flight = null;
    public static int br=0;





    private GetFlightTask task;
    private AddFlightTask task1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        flightDAO = new FlightDAO(getActivity());

        _server = new SendToServer(this);
        setFlight();



    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_flight_list, container, false);
        findViewsById(view);
        task = new GetFlightTask(activity);

        task.execute((Void) null);

        return view;
    }

    private void findViewsById(View view) {

        flightListView = (ListView) view.findViewById(R.id.list_flight);

    }

    public void setFlight() {

        flightDAO = new FlightDAO(getActivity());
        _server = new SendToServer(this);


        SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(this.getContext());

        String iden = preferences1.getString("id", "");


        int iden1 = Integer.parseInt(iden);

        _server.checkFlights(iden1);

    }

    CustomInterface mListener;

    @Override
    public void onClick(View v) {

    }

    public class GetFlightTask extends AsyncTask<Void, Void, ArrayList<Flight>> {



        private final WeakReference<Activity> activityWeakRef;

        public GetFlightTask(Activity context) {
            this.activityWeakRef = new WeakReference<Activity>(context);
        }

        @Override
        protected ArrayList<Flight> doInBackground(Void... arg0) {

            ArrayList<Flight> flightList = flightDAO.getFlights();

            return flightList;
        }

        @Override
        protected void onPostExecute(ArrayList<Flight> flightList) {
            if (activityWeakRef.get() != null
                    && !activityWeakRef.get().isFinishing()) {
  
                flights = flightList;
                if (flightList != null) {
                    if (flightList.size() != 0) {
                        flightListAdapter = new FlightListAdapter(activity,
                                flightList, new CustomInterface(){
                            @Override
                            public void Dialog1(){

                                CustomCommentDialogFragment customComDialogFragment = new CustomCommentDialogFragment();
                                customComDialogFragment.show(getFragmentManager(),
                                        CustomFlightDialogFragment.ARG_ITEM_ID);
                            }
                        });
                        flightListView.setAdapter(flightListAdapter);




                    } else {
                        Toast.makeText(activity, "No Flight Records",
                                Toast.LENGTH_LONG).show();
                    }
                }

            }
        }
    }


    public void updateView() {
        task = new GetFlightTask(activity);
        task.execute((Void) null);
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

                    flight = new Flight();



                    flight.setId(id);
                    log.w("aaa66","aaa66");
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


                    task1 = new FlightListFragment.AddFlightTask(getActivity());
                    task1.execute((Void) null);




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
