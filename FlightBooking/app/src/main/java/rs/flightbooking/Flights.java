package rs.flightbooking;

import static android.content.Context.NOTIFICATION_SERVICE;
import  static rs.flightbooking.Constants.FIRST_COLUMN;
import  static rs.flightbooking.Constants.SECOND_COLUMN;
import  static rs.flightbooking.Constants.THIRD_COLUMN;
import  static rs.flightbooking.Constants.FOURTH_COLUMN;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RemoteViews;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Rale on 4/26/2017.
 */

public class Flights extends Fragment {

    public Flights() {
    }

    private ArrayList<HashMap<String,String>> list;

    private Context context;
    private RemoteViews remoteViews;
    private NotificationCompat.Builder builder;
    private NotificationManager notificationManager;
    private int notification_id;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.activity_flights, container, false);

        context = rootView.getContext();


        ListView listView= (ListView)rootView.findViewById(R.id.listView1);
        populateList();

        ListViewAdapter listAdapter=new ListViewAdapter(this.getActivity(),list);

        listView.setAdapter(listAdapter);




        return rootView;
    }

    private void populateList() {

        list= new ArrayList<HashMap<String, String>>();

        HashMap<String, String> temp = new HashMap<String, String>();
        temp.put(FIRST_COLUMN,"Belgrade");
        temp.put(SECOND_COLUMN,"Rome");
        temp.put(THIRD_COLUMN,"14-4-2019");
        temp.put(FOURTH_COLUMN,"15-8-2019");
        list.add(temp);

        HashMap<String, String> temp1 = new HashMap<String, String>();
        temp1.put(FIRST_COLUMN,"Sevilla");
        temp1.put(SECOND_COLUMN,"Amsterdam");
        temp1.put(THIRD_COLUMN,"7-9-2019");
        temp1.put(FOURTH_COLUMN,"11-10-2019");
        list.add(temp1);

        HashMap<String, String> temp2 = new HashMap<String, String>();
        temp2.put(FIRST_COLUMN,"Munich");
        temp2.put(SECOND_COLUMN,"Paris");
        temp2.put(THIRD_COLUMN,"2-5-2017");
        temp2.put(FOURTH_COLUMN,"3-9-2017");
        list.add(temp2);

        HashMap<String, String> temp3 = new HashMap<String, String>();
        temp3.put(FIRST_COLUMN,"Frankfurt");
        temp3.put(SECOND_COLUMN,"Valencia");
        temp3.put(THIRD_COLUMN,"6-6-2017");
        temp3.put(FOURTH_COLUMN,"7-11-2017");
        list.add(temp3);

        HashMap<String, String> temp4 = new HashMap<String, String>();
        temp4.put(FIRST_COLUMN,"Berlin");
        temp4.put(SECOND_COLUMN,"Copenhagen");
        temp4.put(THIRD_COLUMN,"6-2-2016");
        temp4.put(FOURTH_COLUMN,"16-5-2016");
        list.add(temp4);

        HashMap<String, String> temp5 = new HashMap<String, String>();
        temp5.put(FIRST_COLUMN,"Lisabon");
        temp5.put(SECOND_COLUMN,"Moscow");
        temp5.put(THIRD_COLUMN,"2-9-2017");
        temp5.put(FOURTH_COLUMN,"11-9-2017");
        list.add(temp5);

        HashMap<String, String> temp6 = new HashMap<String, String>();
        temp6.put(FIRST_COLUMN,"Torino");
        temp6.put(SECOND_COLUMN,"Monaco");
        temp6.put(THIRD_COLUMN,"13-5-2016");
        temp6.put(FOURTH_COLUMN,"21-6-2016");
        list.add(temp6);

        HashMap<String, String> temp7 = new HashMap<String, String>();
        temp7.put(FIRST_COLUMN,"Dortmund");
        temp7.put(SECOND_COLUMN,"Istanbul");
        temp7.put(THIRD_COLUMN,"6-2-2017");
        temp7.put(FOURTH_COLUMN,"25-9-2017");
        list.add(temp7);



        @SuppressLint("SimpleDateFormat") SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy");
        Date d = new Date();

        String datum = sd.format(d);



        for(int i =0; i<list.size(); i++){

            Log.d("usao","usao");

            notificationManager =(NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            remoteViews = new RemoteViews(context.getPackageName(),R.layout.notification);

            remoteViews.setImageViewResource(R.id.notif_icon,R.mipmap.ic_launcher);
            remoteViews.setTextViewText(R.id.notif_title,"Notification");

            notification_id =(int) System.currentTimeMillis();
            Intent button_intent =new Intent("button_clicked");
            button_intent.putExtra("id",notification_id);
            PendingIntent p_button_intent = PendingIntent.getBroadcast(context,123,button_intent,i);
            remoteViews.setOnClickPendingIntent(R.id.notif_button,p_button_intent);

            try {
                Date date_compare = sd.parse(list.get(i).get(THIRD_COLUMN));
                Date date = sd.parse(datum);
                Log.d("date", String.valueOf(date));
                Log.d("date_compare", String.valueOf(date_compare));
                if(date_compare.after(date)){

                    Log.d("jeeej","jeeej");
                    Intent notification_intent = new Intent(context, Flights.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(context,i,notification_intent,i);

                    builder = new NotificationCompat.Builder(context);
                    builder.setSmallIcon(R.mipmap.ic_launcher).setAutoCancel(true).setCustomBigContentView(remoteViews).setContentIntent(pendingIntent);

                    notificationManager.notify(notification_id,builder.build());
                }
            }catch (Exception ex){
                Log.d("oh","oh");
                Log.d("message",ex.getMessage());
            }

        }




    }




}

