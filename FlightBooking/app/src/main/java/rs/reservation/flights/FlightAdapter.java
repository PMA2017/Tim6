package rs.reservation.flights;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import rs.flightbooking.R;

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

        TextView townTo = (TextView) itemView.findViewById(R.id.townToMark);
        townTo.setText(flight.townTo);

        TextView timeFrom = (TextView) itemView.findViewById(R.id.timeFrom);
        timeFrom.setText(flight.timeFrom);

        TextView timeTo = (TextView) itemView.findViewById(R.id.townToName);
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
            choose.setText(R.string.sold_out);
            choose.setEnabled(false);
            choose.setBackgroundColor(Color.GRAY);
        } else {
            choose.setText(R.string.choose);
            choose.setEnabled(true);
            choose.setBackgroundColor(Color.parseColor("#13b586"));
        }
        choose.setOnClickListener(new ChooseButtonClickListener(_reservationFlights, flight));

        return itemView;
    }

    public void setFlights(List<FlightView> flights)
    {
        _flights = flights;
    }
}
