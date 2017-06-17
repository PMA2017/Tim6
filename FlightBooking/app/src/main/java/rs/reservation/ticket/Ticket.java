package rs.reservation.ticket;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Button;

import rs.flightbooking.R;
import rs.reservation.flights.FlightAdapter;
import rs.reservation.flights.FlightView;
import java.util.ArrayList;

public class Ticket extends Fragment {

    private View _rootView;

    private ArrayList<FlightView> _flights;

    private FlightView _flightDepart;
    private FlightView _flightReturn;
    private Integer _passangers;

    private ListView _ticketList;
    private TicketAdapter _ticketAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _rootView = inflater.inflate(R.layout.activity_ticket, container, false);
        Bundle bundle = getArguments();
        _flightDepart = (FlightView) bundle.getSerializable("departFlight");
        _flightReturn = (FlightView) bundle.getSerializable("returnFlight");
        _passangers = bundle.getInt("passangers");

        _flights = new ArrayList<FlightView>();
        _flights.add(_flightDepart);
        if(_flightReturn != null) {
            _flights.add(_flightReturn);
        }

        _ticketList = (ListView) _rootView.findViewById(R.id.ticketList);
        _ticketAdapter = new TicketAdapter(this,_flights);
        _ticketList.setAdapter(_ticketAdapter);

        Button reservation = (Button) _rootView.findViewById(R.id.reservation);
        reservation.setOnClickListener(new MakeReservationClickListener(this,_flights,_passangers));

        return _rootView;
    }
}
