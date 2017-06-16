package rs.reservation.ticket;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import rs.flightbooking.R;
import rs.reservation.flights.FlightView;
import java.util.List;
import android.app.Activity;
import java.util.ArrayList;

import org.w3c.dom.Text;

/**
 * Created by n.starcev on 6/16/2017.
 */

public class TicketAdapter extends BaseAdapter {

    private Ticket _ticket;
    private List<FlightView> _flights;
    private Activity _activity;

    public TicketAdapter(Ticket ticket, List<FlightView> flights)
    {
        super();
        _ticket = ticket;
        _flights = flights;
        _activity = ticket.getActivity();
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
            itemView = inflater.inflate(R.layout.ticket_one_row,null);
        }

        FlightView flight = _flights.get(position);

        TextView townFromMark = (TextView) itemView.findViewById(R.id.townFromMark);
        townFromMark.setText(flight.townFrom);

        TextView townToMark = (TextView) itemView.findViewById(R.id.townToMark);
        townToMark.setText(flight.townTo);

        TextView townToName = (TextView) itemView.findViewById(R.id.townToName);
        townToName.setText(flight.townToName);

        /*TextView text = (TextView) itemView.findViewById(R.id.townFromMark);
        text.setText(flight.townFrom);*/

        return itemView;
    }
}
