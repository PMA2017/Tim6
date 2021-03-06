package rs.flights;

/**
 * Created by Rale on 6/11/2017.
 */

import android.app.Activity;

import android.graphics.drawable.Drawable;
import android.media.Rating;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.DialogFragment;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;


import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import android.widget.ListView;
import android.widget.TextView;


import com.loopj.android.http.RequestParams;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.w3c.dom.Text;

import parsers.JSONParser;
import rs.flightbooking.R;


import rs.SQLite.CustomInterface;
import model.Flight;
import rs.flightbooking.R;
import rs.maps.MapFragment;
import tools.IServerCaller;
import tools.SendToServer;
import tools.Session;
import tools.ToastTool;
import tools.response.ServerResponse;


public class FlightListAdapter extends ArrayAdapter<Flight>{

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
        getRatings();
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
        ListView listaKom;
       ArrayList<String> arrayList;
        ArrayAdapter<String> adapter;
        EditText txtinput;

        /*ImageButton imgbt1;
        ImageButton imgbt2;
        ImageButton imgbt3;*/
        LinearLayout stars;
        Button star1;
        Button star2;
        Button star3;
        Button star4;
        Button star5;
        Button arrowUp;

        Flight flight;

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
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder holder = null;

        System.out.println("position"+ position);

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();

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

           holder.star1 = (Button) convertView.findViewById(R.id.star1);
            holder.star2 = (Button) convertView.findViewById(R.id.star2);
            holder.star3 = (Button) convertView.findViewById(R.id.star3);
            holder.star4 = (Button) convertView.findViewById(R.id.star4);
            holder.star5 = (Button) convertView.findViewById(R.id.star5);
            holder.arrowUp = (Button) convertView.findViewById(R.id.arrowUp);


            holder.stars = (LinearLayout) convertView.findViewById(R.id.stars);
            holder.flight = new Flight();




            final LinearLayout starsLayout = holder.stars;
            final Button star1 = holder.star1;
            final Button star2 = holder.star2;
            final Button star3 = holder.star3;
            final Button star4 = holder.star4;
            final Button star5 = holder.star5;

            final Flight flight1 = holder.flight;

            //COMMENT


            holder.listaKom=(ListView) convertView.findViewById(R.id.listaa);
            String[] comments ={"Comment1","Comment2"};
            holder.arrayList = new ArrayList<>(Arrays.asList(comments));
            holder.adapter = new ArrayAdapter<String>(this.getContext(),R.layout.comment_item,R.id.commentItem,holder.arrayList);
            holder.listaKom.setAdapter(holder.adapter);
            holder.txtinput=(EditText) convertView.findViewById(R.id.txtinput);


            final ViewHolder finalHolder = holder;
            holder.imgbt1.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {

                  /*  if(mListener != null)
                        mListener.Dialog1();*/
                   String new_item = finalHolder.txtinput.getText().toString();
                    finalHolder.arrayList.add(new_item);
                    finalHolder.adapter.notifyDataSetChanged();
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

                    ActionBarActivity thisActivity = (ActionBarActivity) context;
                    android.support.v4.app.Fragment v4Fragment = new MapFragment();
                    Bundle bundle = new Bundle();

                    bundle.putString("nameA",flight1.getTownFrom());
                    bundle.putString("nameB",flight1.getTownTo());
                    bundle.putDouble("latA",flight1.getTownFromLongitude());
                    bundle.putDouble("latB",flight1.getTownToLongitude());
                    bundle.putDouble("lonA",flight1.getTownFromLatitude());
                    bundle.putDouble("lonB",flight1.getTownToLatitude());
                    v4Fragment.setArguments(bundle);
                    if(null!=v4Fragment){
                        android.support.v4.app.FragmentManager fm= thisActivity.getSupportFragmentManager();
                        android.support.v4.app.FragmentTransaction t=fm.beginTransaction();
                        t.replace(R.id.main_content,v4Fragment);
                        t.addToBackStack(null);
                        t.commit();


                    }

                }
            });


            holder.star1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final View view = v;
                    Activity thisActivity = (Activity) context;
                    SendToServer sts = new SendToServer(new IServerCaller() {
                        @Override
                        public void OnServerResponse(ServerResponse response) {
                            if(response.statusCode==200)
                            {
                                view.setBackgroundResource(R.drawable.staryellow);
                                star2.setBackgroundResource(R.drawable.star_empty);
                                star3.setBackgroundResource(R.drawable.star_empty);
                                star4.setBackgroundResource(R.drawable.star_empty);
                                star5.setBackgroundResource(R.drawable.star_empty);
                            }else{

                                ToastTool tool = new ToastTool((Activity)context);
                                tool.showString("You cannot rate a flight which hasn't started yet");
                            }
                        }
                    });
                    Session session = new Session(thisActivity.getApplicationContext());

                    RequestParams params = new RequestParams();

                    params.put("User_ID",Integer.parseInt(session.getId()));
                    params.put("Drive_ID",flight1.getId());
                    params.put("Rating",1);

                    System.out.println("Params");
                    System.out.println(flight1.getId());
                    System.out.println(session.getId());
                    sts.post("Rating",params);


                }
            });

            holder.star2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final View view = v;
                    Activity thisActivity = (Activity) context;
                    SendToServer sts = new SendToServer(new IServerCaller() {
                        @Override
                        public void OnServerResponse(ServerResponse response) {
                            if(response.statusCode==200)
                            {
                                star1.setBackgroundResource(R.drawable.staryellow);
                                view.setBackgroundResource(R.drawable.staryellow);
                                star3.setBackgroundResource(R.drawable.star_empty);
                                star4.setBackgroundResource(R.drawable.star_empty);
                                star5.setBackgroundResource(R.drawable.star_empty);
                            }else{

                                ToastTool tool = new ToastTool((Activity)context);
                                tool.showString("You cannot rate a flight which hasn't started yet");
                            }
                        }
                    });
                    Session session = new Session(thisActivity.getApplicationContext());

                    RequestParams params = new RequestParams();

                    params.put("User_ID",Integer.parseInt(session.getId()));
                    params.put("Drive_ID",flight1.getId());
                    params.put("Rating",2);

                    System.out.println("Params");
                    System.out.println(flight1.getId());
                    System.out.println(session.getId());
                    sts.post("Rating",params);

                }
            });

            holder.star3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final View view = v;
                    Activity thisActivity = (Activity) context;
                    SendToServer sts = new SendToServer(new IServerCaller() {
                        @Override
                        public void OnServerResponse(ServerResponse response) {
                            if(response.statusCode==200)
                            {
                                star1.setBackgroundResource(R.drawable.staryellow);
                                star2.setBackgroundResource(R.drawable.staryellow);
                                view.setBackgroundResource(R.drawable.staryellow);
                                star4.setBackgroundResource(R.drawable.star_empty);
                                star5.setBackgroundResource(R.drawable.star_empty);
                            }else{

                                ToastTool tool = new ToastTool((Activity)context);
                                tool.showString("You cannot rate a flight which hasn't started yet");
                            }
                        }
                    });
                    Session session = new Session(thisActivity.getApplicationContext());

                    RequestParams params = new RequestParams();

                    params.put("User_ID",Integer.parseInt(session.getId()));
                    params.put("Drive_ID",flight1.getId());
                    params.put("Rating",3);

                    System.out.println("Params");
                    System.out.println(flight1.getId());
                    System.out.println(session.getId());
                    sts.post("Rating",params);

                }
            });

            holder.star4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final View view = v;
                    Activity thisActivity = (Activity) context;
                    SendToServer sts = new SendToServer(new IServerCaller() {
                        @Override
                        public void OnServerResponse(ServerResponse response) {
                            if(response.statusCode==200)
                            {
                                star1.setBackgroundResource(R.drawable.staryellow);
                                star2.setBackgroundResource(R.drawable.staryellow);
                                star3.setBackgroundResource(R.drawable.staryellow);
                                view.setBackgroundResource(R.drawable.staryellow);
                                star5.setBackgroundResource(R.drawable.star_empty);
                            }else{

                                ToastTool tool = new ToastTool((Activity)context);
                                tool.showString("You cannot rate a flight which hasn't started yet");
                            }

                        }
                    });
                    Session session = new Session(thisActivity.getApplicationContext());

                    RequestParams params = new RequestParams();

                    params.put("User_ID",Integer.parseInt(session.getId()));
                    params.put("Drive_ID",flight1.getId());
                    params.put("Rating",4);

                    System.out.println("Params");
                    System.out.println(flight1.getId());
                    System.out.println(session.getId());
                    sts.post("Rating",params);


                }
            });

            holder.star5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final View view = v;
                    Activity thisActivity = (Activity) context;
                    SendToServer sts = new SendToServer(new IServerCaller() {
                        @Override
                        public void OnServerResponse(ServerResponse response) {
                            if(response.statusCode==200)
                            {
                                star1.setBackgroundResource(R.drawable.staryellow);
                                star2.setBackgroundResource(R.drawable.staryellow);
                                star3.setBackgroundResource(R.drawable.staryellow);
                                star4.setBackgroundResource(R.drawable.staryellow);
                                view.setBackgroundResource(R.drawable.staryellow);
                            }
                            else{

                                ToastTool tool = new ToastTool((Activity)context);
                                tool.showString("You cannot rate a flight which hasn't started yet");
                            }
                        }
                    });
                    Session session = new Session(thisActivity.getApplicationContext());

                    RequestParams params = new RequestParams();

                    params.put("User_ID",Integer.parseInt(session.getId()));
                    params.put("Drive_ID",flight1.getId());
                    params.put("Rating",5);

                    System.out.println("Params");
                    System.out.println(flight1.getId());
                    System.out.println(session.getId());
                    sts.post("Rating",params);


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
//        holder.flightIdTxt.setText(flight.getId() + "");
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
        holder.flight.setTownFrom(flight.getTownFrom());
        holder.flight.setTownTo(flight.getTownTo());
        holder.flight.setTownFromLatitude(flight.getTownFromLatitude());
        holder.flight.setTownFromLongitude(flight.getTownFromLongitude());
        holder.flight.setTownToLatitude(flight.getTownToLatitude());
        holder.flight.setTownToLongitude(flight.getTownToLongitude());
        holder.flight.setId(flight.getId());
        holder.flight.setRating(flight.getRating());

        holder.star1.setBackgroundResource(R.drawable.star_empty);
        holder.star2.setBackgroundResource(R.drawable.star_empty);
        holder.star3.setBackgroundResource(R.drawable.star_empty);
        holder.star4.setBackgroundResource(R.drawable.star_empty);
        holder.star5.setBackgroundResource(R.drawable.star_empty);
        switch (holder.flight.getRating()) {
            case 0:
                break;
            case 1:
                holder.star1.setBackgroundResource(R.drawable.staryellow);

                break;
            case 2:
                holder.star1.setBackgroundResource(R.drawable.staryellow);
                holder.star2.setBackgroundResource(R.drawable.staryellow);
                break;
            case 3:
                holder.star1.setBackgroundResource(R.drawable.staryellow);
                holder.star2.setBackgroundResource(R.drawable.staryellow);
                holder.star3.setBackgroundResource(R.drawable.staryellow);
                //startActivity(new Intent(MainActivity.this, MapsActivity.class));
                break;
            case 4:
                holder.star1.setBackgroundResource(R.drawable.staryellow);
                holder.star2.setBackgroundResource(R.drawable.staryellow);
                holder.star3.setBackgroundResource(R.drawable.staryellow);
                holder.star4.setBackgroundResource(R.drawable.staryellow);
                break;

            case 5:
                holder.star1.setBackgroundResource(R.drawable.staryellow);
                holder.star2.setBackgroundResource(R.drawable.staryellow);
                holder.star3.setBackgroundResource(R.drawable.staryellow);
                holder.star4.setBackgroundResource(R.drawable.staryellow);
                holder.star5.setBackgroundResource(R.drawable.staryellow);
                break;

            default:
                break;
        }

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

    public void getRatings(){
        JSONArray array = new JSONArray();
        for(int i=0; i<flights.size(); i++)
        {
            array.put(flights.get(i).getId());
        }
        SendToServer sendToServer = new SendToServer(new IServerCaller() {
            @Override
            public void OnServerResponse(ServerResponse response) {
                ArrayList<Integer> ratings = JSONParser.getAllRatings(response.responseArray);
                ArrayList<Integer> driveIds = JSONParser.getAllDriveIds(response.responseArray);
                for(int i=0; i<driveIds.size(); i++)
                {
                    for(int j=0; j<flights.size(); j++)
                    {
                       if(driveIds.get(i)==flights.get(j).getId())
                       {
                           flights.get(j).setRating(ratings.get(i));
                           break;
                       }
                    }
                }
            }
        });
        Session session = new Session(context.getApplicationContext());
        RequestParams params = new RequestParams();
        params.put("driveIds",array);
        sendToServer.getRatingsForFlights(Integer.parseInt(session.getId()),params);
    }



}
