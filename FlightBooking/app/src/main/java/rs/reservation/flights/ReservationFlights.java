package rs.reservation.flights;

import android.app.Fragment;
import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import rs.flightbooking.R;
import tools.SendToServer;

public class ReservationFlights extends Fragment {

    private SendToServer _server;
    private View _rootView;

    private ArrayList<FlightView> _flights = new ArrayList<FlightView>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _rootView = inflater.inflate(R.layout.activity_reservation_flights, container, false);

        ListView flightList = (ListView) _rootView.findViewById(R.id.flightList);
        mockData();
        FlightAdapter adapter = new FlightAdapter(this.getActivity(),R.layout.reservation_flights_one_row,_flights);
        flightList.setAdapter(adapter);
        return _rootView;
    }

    private void mockData()
    {
        FlightView flight1 = new FlightView();
        flight1.townFrom = "BEG";
        flight1.townTo = "LOS";
        flight1.timeFrom = "10:35";
        flight1.timeTo = "13:00";
        flight1.type = "DIRECT";
        flight1.duration = "11h 25m";
        flight1.company = "Air Serbia";
        flight1.price = "830 €";
        _flights.add(flight1);

        FlightView flight2 = new FlightView();
        flight2.townFrom = "BEG";
        flight2.townTo = "LOS";
        flight2.timeFrom = "07:45";
        flight2.timeTo = "20:15";
        flight2.type = "1 STOP";
        flight2.duration = "22h 30m";
        flight2.company = "Air Serbia";
        flight2.price = "760 €";
        _flights.add(flight2);

        flight2 = new FlightView();
        flight2.townFrom = "BEG";
        flight2.townTo = "LOS";
        flight2.timeFrom = "07:45";
        flight2.timeTo = "20:15";
        flight2.type = "1 STOP";
        flight2.duration = "22h 30m";
        flight2.company = "Air Serbia";
        flight2.price = "760 €";
        _flights.add(flight2);

        flight2 = new FlightView();
        flight2.townFrom = "BEG";
        flight2.townTo = "LOS";
        flight2.timeFrom = "07:45";
        flight2.timeTo = "20:15";
        flight2.type = "1 STOP";
        flight2.duration = "22h 30m";
        flight2.company = "Air Serbia";
        flight2.price = "760 €";
        _flights.add(flight2);
    }


}
