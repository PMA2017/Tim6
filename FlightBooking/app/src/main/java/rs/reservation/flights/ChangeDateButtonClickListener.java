package rs.reservation.flights;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.app.Activity;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import rs.flightbooking.R;

/**
 * Created by Nemanja on 6/15/2017.
 */

public class ChangeDateButtonClickListener implements View.OnClickListener  {

    private Button _source;
    private ListView _list;
    private Activity _activity;
    private ArrayList<Button> _topChangeDateButtons;

    public ChangeDateButtonClickListener(ArrayList<Button> topChangeDateButtons, Button source, Activity activity, ListView list)
    {
        _source = source;
        _list = list;
        _topChangeDateButtons = topChangeDateButtons;
    }

    @Override
    public void onClick(View v) {
        if(_list.getAdapter() instanceof FlightAdapter) {
            clearColorOnButtons();
            _source.setTextColor(Color.RED);
            ArrayList<FlightView> flights = (ArrayList<FlightView>)_source.getTag();
            ((FlightAdapter)_list.getAdapter()).setFlights(flights);
            ((FlightAdapter) _list.getAdapter()).notifyDataSetChanged();
        }
    }

    private void clearColorOnButtons()
    {
        for(int i = 0; i < _topChangeDateButtons.size(); i++)
        {
            _topChangeDateButtons.get(i).setTextColor(Color.BLACK);
        }
    }

}
