package rs.reservation.flights;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rs.flightbooking.R;
import tools.SendToServer;

public class ReservationFlightsActivity extends Fragment {

    private SendToServer _server;
    private View _rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _rootView = inflater.inflate(R.layout.activity_reservation_flights, container, false);
        return _rootView;
    }
}
