package rs.reservation.flights;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.app.Activity;

import java.util.ArrayList;

import rs.flightbooking.R;

/**
 * Created by Nemanja on 6/15/2017.
 */

public class ChangeDateButtonClickListener implements View.OnClickListener  {

    private Button _source;
    private ListView _list;
    private Activity _activity;

    public ChangeDateButtonClickListener(Button source, Activity activity, ListView list)
    {
        _source = source;
        _list = list;
    }

    @Override
    public void onClick(View v) {
        _source.setTextColor(Color.RED);
        ArrayList<FlightView> flights = (ArrayList<FlightView>)_source.getTag();

        FlightAdapter adapter = new FlightAdapter(_activity, R.layout.reservation_flights_one_row, flights);
        _list.setAdapter(adapter);
    }
}
