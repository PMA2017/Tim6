package rs.flights;

/**
 * Created by Rale on 6/11/2017.
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RemoteViews;
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
import java.util.HashMap;
import java.util.zip.Inflater;

import rs.SQLite.CustomCommentDialogFragment;
import rs.SQLite.CustomFlightDialogFragment;
import rs.SQLite.CustomInterface;
import model.Flight;
import rs.SQLite.FlightDAO;
import rs.flightbooking.Flights;
import rs.flightbooking.R;
import tools.DateUtil;
import tools.IServerCaller;
import tools.SendToServer;
import tools.response.ServerResponse;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.loopj.android.http.AsyncHttpClient.log;
import static rs.flightbooking.Constants.FIRST_COLUMN;
import static rs.flightbooking.Constants.FOURTH_COLUMN;
import static rs.flightbooking.Constants.SECOND_COLUMN;
import static rs.flightbooking.Constants.THIRD_COLUMN;

/**/



import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
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

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import rs.SQLite.CustomCommentDialogFragment;
import rs.SQLite.CustomFlightDialogFragment;
import rs.SQLite.CustomInterface;
import model.Flight;
import rs.SQLite.FlightDAO;
import rs.flightbooking.R;

/**/

public  class FlightListFragment extends Fragment  {

    public static final String ARG_ITEM_ID = "flight_list";

    Activity activity;
    ListView flightListView;
    ArrayList<Flight> flights;

    FlightListAdapter flightListAdapter;
    FlightDAO flightDAO;
    ImageButton comment;

    private ArrayList<HashMap<String,String>> list;

    private Context context;
    public static RemoteViews remoteViews;
    private NotificationCompat.Builder builder;
    private NotificationManager notificationManager;
    private int notification_id;



    private GetFlightTask task;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = getActivity();
        flightDAO = new FlightDAO(activity);
    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flight_list, container, false);

        context = view.getContext();
        findViewsById(view);

        task = new GetFlightTask(activity);

        task.execute((Void) null);





        /*flightListView.setOnItemClickListener(this);

        flightListView.setOnItemLongClickListener(this);
*/


        return view;
    }

    private void findViewsById(View view) {

        flightListView = (ListView) view.findViewById(R.id.list_flight);



    }


  /*  @Override
    public void onResume() {
        getActivity().setTitle(R.string.app_name);
        getActivity().getActionBar().setTitle(R.string.app_name);
        super.onResume();
    }*/



/*    @Override
    public void onItemClick(AdapterView<?> list, View arg1, int position,
                            long arg3) {
        Flight flight = (Flight) list.getItemAtPosition(position);

        if (flight != null) {

            Bundle arguments = new Bundle();
            arguments.putParcelable("selectedFlight", flight);
            CustomFlightDialogFragment customEmpDialogFragment = new CustomFlightDialogFragment();
            customEmpDialogFragment.setArguments(arguments);

            customEmpDialogFragment.show(getFragmentManager(),
                    CustomFlightDialogFragment.ARG_ITEM_ID);

        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view,
                                   int position, long arg3) {
        Flight flight = (Flight) parent.getItemAtPosition(position);
        // Use AsyncTask to delete from database
        flightDAO.delete(flight);
        flightListAdapter.remove(flight);
        return true;
    }*/
    CustomInterface mListener;
    ArrayList<Flight> flightList;

    public class GetFlightTask extends AsyncTask<Void, Void, ArrayList<Flight>> {

        private final WeakReference<Activity> activityWeakRef;

        public GetFlightTask(Activity context) {
            this.activityWeakRef = new WeakReference<Activity>(context);
        }

        @Override
        protected ArrayList<Flight> doInBackground(Void... arg0) {

             flightList = flightDAO.getFlights();
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
                        populateList();




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
    private void populateList() {

        @SuppressLint("SimpleDateFormat") SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = new Date();



        String datum = sd.format(d);

        Log.d("usao", datum);

        for(int i =0; i<flights.size(); i++){

            Log.d("usao", String.valueOf(flights.size()));

            notificationManager =(NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            remoteViews = new RemoteViews(context.getPackageName(),R.layout.notification);

            remoteViews.setImageViewResource(R.id.notif_icon,R.mipmap.ic_launcher);


            notification_id =(int) System.currentTimeMillis();
            Intent button_intent =new Intent("button_clicked");
            button_intent.putExtra("id",notification_id);
            PendingIntent p_button_intent = PendingIntent.getBroadcast(context,123,button_intent,i);
            remoteViews.setOnClickPendingIntent(R.id.notif_button,p_button_intent);

            try {
                Date date_compare = sd.parse(flightList.get(i).getDate1()+" "+flightList.get(i).getTime1());
                Date date = sd.parse(datum);
                Log.d("date", String.valueOf(date));
                Log.d("date_compare", String.valueOf(date_compare));




                if(date_compare.after(date)){

                    Date date_2d = DateUtil.addDays(date_compare,-2);
                    Date date_12h = DateUtil.addHours(date_compare,-12);
                    Date date_4h = DateUtil.addHours(date_compare,-4);

                    Log.d("one", String.valueOf(date_2d));
                    Log.d("two", String.valueOf(date_12h));
                    Log.d("three", String.valueOf(date_4h));

                    String destination1= flights.get(i).getTownFrom();
                    String destination2= flights.get(i).getTownTo();



                    if(date_2d.after(date)){
                        remoteViews.setTextViewText(R.id.notif_title,"Flight from "+destination1+" to "+destination2+" will begin for less than 2 days");

                        Intent notification_intent = new Intent(context, FlightListFragment.class);
                        PendingIntent pendingIntent = PendingIntent.getActivity(context,i,notification_intent,i);

                        builder = new NotificationCompat.Builder(context);
                        builder.setSmallIcon(R.mipmap.ic_launcher).setAutoCancel(true).setCustomBigContentView(remoteViews).setContentIntent(pendingIntent);

                        notificationManager.notify(notification_id,builder.build());

                    }else if (date_12h.after(date)){
                        remoteViews.setTextViewText(R.id.notif_title,"Flight from "+destination1+" to "+destination2+" will begin for less than 12 hours");

                        Intent notification_intent = new Intent(context, FlightListFragment.class);
                        PendingIntent pendingIntent = PendingIntent.getActivity(context,i,notification_intent,i);

                        builder = new NotificationCompat.Builder(context);
                        builder.setSmallIcon(R.mipmap.ic_launcher).setAutoCancel(true).setCustomBigContentView(remoteViews).setContentIntent(pendingIntent);

                        notificationManager.notify(notification_id,builder.build());

                    }else if(date_4h.after(date)){
                        remoteViews.setTextViewText(R.id.notif_title,"Flight from "+destination1+" to "+destination2+" will begin for less than 4 hours");

                        Intent notification_intent = new Intent(context, FlightListFragment.class);
                        PendingIntent pendingIntent = PendingIntent.getActivity(context,i,notification_intent,i);

                        builder = new NotificationCompat.Builder(context);
                        builder.setSmallIcon(R.mipmap.ic_launcher).setAutoCancel(true).setCustomBigContentView(remoteViews).setContentIntent(pendingIntent);

                        notificationManager.notify(notification_id,builder.build());

                    }else{
                        Log.d("No notification","No notification");
                    }

                }
            }catch (Exception ex){
                Log.d("oh","oh");
                Log.d("message",ex.getMessage());
            }

        }




    }



}
