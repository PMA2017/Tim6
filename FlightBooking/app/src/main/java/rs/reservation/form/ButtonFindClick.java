package rs.reservation.form;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.View;

import rs.flightbooking.R;
import rs.reservation.flights.ReservationFlightsActivity;

/**
 * Created by Nemanja on 6/13/2017.
 */

public class ButtonFindClick implements View.OnClickListener {

    private Activity _activity;

    public ButtonFindClick(Activity activity)
    {
        _activity = activity;
    }

    @Override
    public void onClick(View v) {
        ReservationFlightsActivity fragment = new ReservationFlightsActivity();
        FragmentManager fm= _activity.getFragmentManager();
        FragmentTransaction t=fm.beginTransaction();
        t.replace(R.id.main_content,fragment);
        t.addToBackStack(null);
        t.commit();
    }
}
