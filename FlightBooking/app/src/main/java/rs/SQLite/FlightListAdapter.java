package rs.SQLite;

/**
 * Created by Rale on 6/11/2017.
 */

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import rs.flightbooking.R;

import static com.loopj.android.http.AsyncHttpClient.log;


public class FlightListAdapter extends ArrayAdapter<Flight> {

    private Context context;
    List<Flight> flights;

    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.ENGLISH);

    public FlightListAdapter(Context context, List<Flight> flights) {
        super(context, R.layout.list_item, flights);
        this.context = context;
        this.flights = flights;
    }

    private class ViewHolder {
        TextView flightIdTxt;
        TextView flightTownFromTxt;
        TextView flightTownToTxt;
        TextView flightDateFromTxt;
        TextView flightDateToTxt;
    }

    @Override
    public int getCount() {
        return flights.size();
    }

    @Override
    public Flight getItem(int position) {
        return flights.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {

            log.w("f17","f17");
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();

            holder.flightIdTxt = (TextView) convertView
                    .findViewById(R.id.txt_fl_id);
            holder.flightTownFromTxt = (TextView) convertView
                    .findViewById(R.id.txt_fl_townFrom);
            holder.flightTownToTxt = (TextView) convertView
                    .findViewById(R.id.txt_fl_townTo);
            holder.flightDateFromTxt = (TextView) convertView
                    .findViewById(R.id.txt_fl_dateFrom);
            holder.flightDateToTxt = (TextView) convertView
                    .findViewById(R.id.txt_fl_dateTo);
            log.w("a18","a18");
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        log.w("pp","pp");
        Flight flight = (Flight) getItem(position);
        holder.flightIdTxt.setText(flight.getId() + "");
        holder.flightTownFromTxt.setText(flight.getTownFrom());
        holder.flightTownToTxt.setText(flight.getTownTo() + "");
        log.w("pp1","pp1");
        holder.flightDateFromTxt.setText(formatter.format(flight.getDateFrom()));
        log.w("pp2","pp2");
        holder.flightDateToTxt.setText(formatter.format(flight.getDateTo()));
        log.w("prosao","prosao");
        return convertView;
    }

    @Override
    public void add(Flight flight) {
        flights.add(flight);
        notifyDataSetChanged();
        super.add(flight);
    }

    @Override
    public void remove(Flight flight) {
        flights.remove(flight);
        notifyDataSetChanged();
        super.remove(flight);
    }
}
