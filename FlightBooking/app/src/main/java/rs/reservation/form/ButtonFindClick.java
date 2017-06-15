package rs.reservation.form;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Parcelable;
import android.view.View;
import android.os.Bundle;

import java.util.ArrayList;

import rs.flightbooking.R;
import rs.reservation.flights.FlightView;
import rs.reservation.flights.ReservationFlights;
import tools.IServerCaller;
import tools.response.ServerResponse;

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
        ReservationFlights fragment = new ReservationFlights();
        Bundle bundle=new Bundle();
        bundle.putString("isReturn", "");
        bundle.putBoolean("isReturn",false);

        fragment.setArguments(bundle);
        FragmentManager fm= _activity.getFragmentManager();
        FragmentTransaction t=fm.beginTransaction();
        t.replace(R.id.main_content,fragment);
        t.addToBackStack(null);
        t.commit();
    }

}
