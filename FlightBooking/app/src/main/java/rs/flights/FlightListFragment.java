package rs.flights;

/**
 * Created by Rale on 6/11/2017.
 */

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

public  class FlightListFragment extends Fragment implements OnItemClickListener,
        OnItemLongClickListener {

    public static final String ARG_ITEM_ID = "flight_list";

    Activity activity;
    ListView flightListView;
    ArrayList<Flight> flights;

    FlightListAdapter flightListAdapter;
    FlightDAO flightDAO;
    ImageButton comment;



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
        findViewsById(view);

        task = new GetFlightTask(activity);

        task.execute((Void) null);



        flightListView.setOnItemClickListener(this);

        flightListView.setOnItemLongClickListener(this);



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



    @Override
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
    }
    CustomInterface mListener;

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


}
