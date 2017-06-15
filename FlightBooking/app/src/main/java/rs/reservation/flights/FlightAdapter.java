package rs.reservation.flights;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import rs.flightbooking.R;

/**
 * Created by Nemanja on 6/13/2017.
 */

public class FlightAdapter extends ArrayAdapter<FlightView> {

    private Activity _activity;
    private int _resource;
    private List<FlightView> _flights;

    public FlightAdapter(Activity activity, int resource, List<FlightView> flights) {
        super(activity, resource, flights);
        _activity = activity;
        _resource = resource;
        _flights = flights;
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

        return itemView;
    }

}
