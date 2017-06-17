package rs.reservation.flights;

import android.view.View;
import android.widget.Button;

import rs.SQLite.Flight;
import rs.flightbooking.R;

import rs.reservation.form.Reservations;

/**
 * Created by Nemanja on 6/15/2017.
 */

public class ChooseButtonClickListener implements View.OnClickListener {

    private ReservationFlights _reservationsFlight;
    private FlightView _flight;

    public ChooseButtonClickListener(ReservationFlights reservationsFlight, FlightView flight)
    {
        _reservationsFlight = reservationsFlight;
        _flight = flight;
    }

    @Override
    public void onClick(View v)
    {
        _reservationsFlight.doNextProcess(_flight);
    }

}
