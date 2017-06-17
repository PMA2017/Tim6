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

        TextView townFromName = (TextView) itemView.findViewById(R.id.townFromName);
        townFromName.setText(flight.townFromName);

        TextView townToName = (TextView) itemView.findViewById(R.id.townToName);
        townToName.setText(flight.townToName);

        TextView type = (TextView) itemView.findViewById(R.id.type);
        type.setText(flight.type);

        TextView duration = (TextView) itemView.findViewById(R.id.duration);
        duration.setText(flight.duration);

        TextView dateDepartReservation = (TextView) itemView.findViewById(R.id.dateDepartReservation);
        dateDepartReservation.setText(flight.dateFrom);

        TextView dateReturnReservation = (TextView) itemView.findViewById(R.id.dateReturnReservation);
        dateReturnReservation.setText(flight.dateTo);

        TextView timeDepart = (TextView) itemView.findViewById(R.id.timeDepart);
        timeDepart.setText(flight.timeFrom);

        TextView timeReturn = (TextView) itemView.findViewById(R.id.timeReturn);
        timeReturn.setText(flight.timeTo);

        TextView company = (TextView) itemView.findViewById(R.id.company);
        company.setText(flight.company);

        TextView price = (TextView) itemView.findViewById(R.id.price);
        price.setText(flight.price);

        return itemView;
    }
}
