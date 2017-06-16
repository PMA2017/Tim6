package rs.reservation.flights;

import android.app.Fragment;
import android.database.DataSetObserver;
import android.graphics.Color;
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
    private ListView _flightList;

    private ArrayList<ArrayList<FlightView>> _flights = new ArrayList<ArrayList<FlightView>>();

    public ReservationFlights()
    {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _rootView = inflater.inflate(R.layout.activity_reservation_flights, container, false);
        _flightList = (ListView) _rootView.findViewById(R.id.flightList);
        mainMock();
        setListenerOnChangeDateButtons(0);
        FlightAdapter adapter = new FlightAdapter(this,R.layout.reservation_flights_one_row,_flights.get(3));
        _flightList.setAdapter(adapter);
        return _rootView;
    }

    public void setListenerOnChangeDateButtons(int start)
    {
        ArrayList<Button> buttons = new ArrayList<Button>();
        Button button1 = (Button) _rootView.findViewById(R.id.first);
        Button button2 = (Button) _rootView.findViewById(R.id.second);
        Button button3 = (Button) _rootView.findViewById(R.id.third);
        Button button4 = (Button) _rootView.findViewById(R.id.fourth);
        Button button5 = (Button) _rootView.findViewById(R.id.fifth);

        button1.setTag(_flights.get(0+start));
        button2.setTag(_flights.get(1+start));
        button3.setTag(_flights.get(2+start));
        button3.setTextColor(Color.RED);
        button4.setTag(_flights.get(3+start));
        button5.setTag(_flights.get(4+start));

        buttons.add(button1);
        buttons.add(button2);
        buttons.add(button3);
        buttons.add(button4);
        buttons.add(button5);

        button1.setOnClickListener(new ChangeDateButtonClickListener(buttons, button1,this.getActivity(),_flightList));
        button2.setOnClickListener(new ChangeDateButtonClickListener(buttons, button2,this.getActivity(),_flightList));
        button3.setOnClickListener(new ChangeDateButtonClickListener(buttons, button3,this.getActivity(),_flightList));
        button4.setOnClickListener(new ChangeDateButtonClickListener(buttons, button4,this.getActivity(),_flightList));
        button5.setOnClickListener(new ChangeDateButtonClickListener(buttons, button5,this.getActivity(),_flightList));
    }

    private void mainMock()
    {
        _flights.add(mockData());
        _flights.add(new ArrayList<FlightView>());
        _flights.add(mockData());
        _flights.add(mockData());
        _flights.add(new ArrayList<FlightView>());
    }

    private ArrayList<FlightView> mockData()
    {
        ArrayList<FlightView> flights = new ArrayList<FlightView>();
        FlightView flight1 = new FlightView();
        flight1.townFrom = "BEG";
        flight1.townTo = "LOS";
        flight1.timeFrom = "10:35";
        flight1.timeTo = "13:00";
        flight1.type = "DIRECT";
        flight1.duration = "11h 25m";
        flight1.company = "Air Serbia";
        flight1.price = "830 €";
        flight1.isFree = true;
        flights.add(flight1);

        FlightView flight2 = new FlightView();
        flight2.townFrom = "BEG";
        flight2.townTo = "LOS";
        flight2.timeFrom = "07:45";
        flight2.timeTo = "20:15";
        flight2.type = "1 STOP";
        flight2.duration = "22h 30m";
        flight2.company = "Air Serbia";
        flight2.price = "760 €";
        flight2.isFree = false;
        flights.add(flight2);
        return flights;
    }


}
