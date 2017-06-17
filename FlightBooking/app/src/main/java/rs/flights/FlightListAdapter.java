package rs.flights;

/**
 * Created by Rale on 6/11/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import rs.SQLite.CustomInterface;
import rs.SQLite.Flight;
import rs.flightbooking.R;


public class FlightListAdapter extends ArrayAdapter<Flight> {

    private Context context;
    List<Flight> flights;

    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.ENGLISH);

    public  static int broj;

    private CustomInterface mListener;
    public FlightListAdapter(Context context, List<Flight> flights, CustomInterface mListener) {
        super(context, R.layout.list_item, flights);
        this.context = context;
        this.flights = flights;
        this.mListener = mListener;
    }

    private class ViewHolder {
        TextView flightIdTxt;
        TextView flightTownFromTxt;
        TextView flightTownToTxt;
        TextView flightDateFromTxt;
        TextView flightDateToTxt;
        TextView priceTxt;
        TextView companyTxt;
        TextView TownFromMarkTxt;
        TextView TownToMarkTxt;
        TextView durationTxt;
        TextView date1Txt;
        TextView date2Txt;
        Button imgbt1;
        Button imgbt2;
        Button imgbt3;
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
    public View getView(int position, View convertView, final ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {

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

            holder.priceTxt = (TextView) convertView
                    .findViewById(R.id.price);
            holder.companyTxt = (TextView) convertView
                    .findViewById(R.id.company);
            holder.TownFromMarkTxt = (TextView) convertView
                    .findViewById(R.id.mark1);
            holder.TownToMarkTxt = (TextView) convertView
                    .findViewById(R.id.mark2);
            holder.durationTxt = (TextView) convertView
                    .findViewById(R.id.duration);
            holder.date1Txt = (TextView) convertView
                    .findViewById(R.id.date1);
            holder.date2Txt = (TextView) convertView
                    .findViewById(R.id.date2);




            holder.imgbt1 = (Button) convertView
                    .findViewById(R.id.imageButton1);
            holder.imgbt2 = (Button) convertView
                    .findViewById(R.id.imageButton2);
            holder.imgbt3 = (Button) convertView
                    .findViewById(R.id.imageButton3);

            holder.imgbt1.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {

                    if(mListener != null)
                        mListener.Dialog1();
                }
            });



            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Flight flight = (Flight) getItem(position);
        holder.flightIdTxt.setText(flight.getId() + "");
        holder.flightTownFromTxt.setText(flight.getTownFrom()+ "");
        holder.flightTownToTxt.setText(flight.getTownTo() + "");
        holder.flightDateFromTxt.setText(flight.getTime1() + "");
        holder.flightDateToTxt.setText(flight.getTime2() + "");
        holder.priceTxt.setText(flight.getPrice()+ "");
        holder.companyTxt.setText(flight.getCompany() + "");
        holder.TownFromMarkTxt.setText(flight.getTownFromMark() + "");
        holder.TownToMarkTxt.setText(flight.getTownToMark() + "");
        holder.durationTxt.setText(flight.getDuration() + "");
        holder.date1Txt.setText(flight.getDate1() + "");
        holder.date2Txt.setText(flight.getDate2() + "");



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
