package rs.reservation.flights;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import rs.flightbooking.R;
import tools.Session;

import static android.graphics.Color.GRAY;

/**
 * Created by Nemanja on 6/13/2017.
 */

public class FlightAdapter extends BaseAdapter {

    private ReservationFlights _reservationFlights;
    private Activity _activity;
    private int _resource;
    private List<FlightView> _flights;

    public FlightAdapter(ReservationFlights reservationFlights, int resource, List<FlightView> flights) {
        super();
        _activity = reservationFlights.getActivity();
        _resource = resource;
        _flights = flights;
        _reservationFlights = reservationFlights;
    }

    @Override
    public int getCount() {
        return _flights.size();
    }

    @Override
    public Object getItem(int position) {
        return _flights.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = _activity.getLayoutInflater();
        View itemView = convertView;
        if (itemView == null) {
            itemView = inflater.inflate(R.layout.reservation_flights_one_row,null);
        }
        FlightView flight = _flights.get(position);

        TextView townFrom = (TextView) itemView.findViewById(R.id.townFrom);
        townFrom.setText(flight.townFrom);

        TextView townTo = (TextView) itemView.findViewById(R.id.townTo);
        townTo.setText(flight.townTo);

        TextView timeFrom = (TextView) itemView.findViewById(R.id.timeFrom);
        timeFrom.setText(flight.timeFrom);

        TextView timeTo = (TextView) itemView.findViewById(R.id.timeTo);
        timeTo.setText(flight.timeTo);

        TextView type = (TextView) itemView.findViewById(R.id.type);
        type.setText(flight.type);

        TextView duration = (TextView) itemView.findViewById(R.id.duration);
        duration.setText(flight.duration);

        TextView company = (TextView) itemView.findViewById(R.id.company);
        company.setText(flight.company);

        TextView price = (TextView) itemView.findViewById(R.id.price);
        price.setText(flight.price);

        Button choose = (Button) itemView.findViewById(R.id.choose);
        if(flight.isFree == false) {
            choose.setText("SOLD OUT");
            choose.setEnabled(false);
            choose.setBackgroundColor(Color.GRAY);
        } else {
            choose.setText("CHOOSE");
            choose.setEnabled(true);
            choose.setBackgroundColor(Color.parseColor("#13b586"));
        }

        return itemView;
    }

    public void setFlights(List<FlightView> flights)
    {
        _flights = flights;
    }

    private void setListenerOnChooseButton(Button button, FlightView flight)
    {

    }

}
