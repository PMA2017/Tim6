package rs.SQLite;

/**
 * Created by Rale on 6/11/2017.
 */

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import rs.flightbooking.R;

import static com.loopj.android.http.AsyncHttpClient.log;


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
        ImageButton imgbt1;
        ImageButton imgbt2;
        ImageButton imgbt3;
        LinearLayout stars;
        Button star1;
        Button star2;
        Button star3;
        Button star4;
        Button star5;
        Button arrowUp;
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
            holder.imgbt1 = (ImageButton) convertView
                    .findViewById(R.id.imageButton1);
            holder.imgbt2 = (ImageButton) convertView
                    .findViewById(R.id.imageButton2);
            holder.imgbt3 = (ImageButton) convertView
                    .findViewById(R.id.imageButton3);

            holder.star1 = (Button) convertView.findViewById(R.id.star1);
            holder.star2 = (Button) convertView.findViewById(R.id.star2);
            holder.star3 = (Button) convertView.findViewById(R.id.star3);
            holder.star4 = (Button) convertView.findViewById(R.id.star4);
            holder.star5 = (Button) convertView.findViewById(R.id.star5);
            holder.arrowUp = (Button) convertView.findViewById(R.id.arrowUp);


            holder.stars = (LinearLayout) convertView.findViewById(R.id.stars);

            final LinearLayout starsLayout = holder.stars;
            final Button star1 = holder.star1;
            final Button star2 = holder.star2;
            final Button star3 = holder.star3;
            final Button star4 = holder.star4;
            final Button star5 = holder.star5;

            holder.imgbt1.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {

                    if(mListener != null)
                        mListener.Dialog1();
                }
            });

            //TO do
            holder.imgbt2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!=null){
                        starsLayout.setVisibility(View.VISIBLE);
                    }

                }
            });

            holder.imgbt3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!=null){

                    }

                }
            });


            holder.star1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                       v.setBackgroundResource(R.drawable.staryellow);
                       star2.setBackgroundResource(R.drawable.star_empty);
                        star3.setBackgroundResource(R.drawable.star_empty);
                        star4.setBackgroundResource(R.drawable.star_empty);
                        star5.setBackgroundResource(R.drawable.star_empty);


                }
            });

            holder.star2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    star1.setBackgroundResource(R.drawable.staryellow);
                    v.setBackgroundResource(R.drawable.staryellow);
                    star3.setBackgroundResource(R.drawable.star_empty);
                    star4.setBackgroundResource(R.drawable.star_empty);
                    star5.setBackgroundResource(R.drawable.star_empty);

                }
            });

            holder.star3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    star1.setBackgroundResource(R.drawable.staryellow);
                    star2.setBackgroundResource(R.drawable.staryellow);
                    v.setBackgroundResource(R.drawable.staryellow);
                    star4.setBackgroundResource(R.drawable.star_empty);
                    star5.setBackgroundResource(R.drawable.star_empty);

                }
            });

            holder.star4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    star1.setBackgroundResource(R.drawable.staryellow);
                    star2.setBackgroundResource(R.drawable.staryellow);
                    star3.setBackgroundResource(R.drawable.staryellow);
                    v.setBackgroundResource(R.drawable.staryellow);
                    star5.setBackgroundResource(R.drawable.star_empty);

                }
            });

            holder.star5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    star1.setBackgroundResource(R.drawable.staryellow);
                    star2.setBackgroundResource(R.drawable.staryellow);
                    star3.setBackgroundResource(R.drawable.staryellow);
                    star4.setBackgroundResource(R.drawable.staryellow);
                    v.setBackgroundResource(R.drawable.staryellow);

                }
            });

            holder.arrowUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    starsLayout.setVisibility(View.GONE);
                }
            });



            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Flight flight = (Flight) getItem(position);
        holder.flightIdTxt.setText(flight.getId() + "");
        holder.flightTownFromTxt.setText(flight.getTownFrom());
        holder.flightTownToTxt.setText(flight.getTownTo() + "");
        holder.flightDateFromTxt.setText(formatter.format(flight.getDateFrom()));
        holder.flightDateToTxt.setText(formatter.format(flight.getDateTo()));

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
