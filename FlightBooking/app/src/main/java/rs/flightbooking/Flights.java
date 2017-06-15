package rs.flightbooking;

import static android.content.Context.NOTIFICATION_SERVICE;
import  static rs.flightbooking.Constants.FIRST_COLUMN;
import  static rs.flightbooking.Constants.SECOND_COLUMN;
import  static rs.flightbooking.Constants.THIRD_COLUMN;
import  static rs.flightbooking.Constants.FOURTH_COLUMN;

import android.annotation.SuppressLint;
import android.app.Fragment;
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
import android.widget.ListView;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import tools.DateUtil;

/**
 * Created by Rale on 4/26/2017.
 */

public class Flights extends Fragment {

    public Flights() {
    }

    private ArrayList<HashMap<String,String>> list;

    private Context context;
    public static RemoteViews remoteViews;
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
        temp.put(THIRD_COLUMN,"14-4-2016 20:00:00");
        temp.put(FOURTH_COLUMN,"15-8-2016 20:00:00");
        list.add(temp);

        HashMap<String, String> temp1 = new HashMap<String, String>();
        temp1.put(FIRST_COLUMN,"Sevilla");
        temp1.put(SECOND_COLUMN,"Amsterdam");
        temp1.put(THIRD_COLUMN,"7-9-2016 20:00:00");
        temp1.put(FOURTH_COLUMN,"11-10-2016 20:00:00");
        list.add(temp1);

        HashMap<String, String> temp2 = new HashMap<String, String>();
        temp2.put(FIRST_COLUMN,"Munich");
        temp2.put(SECOND_COLUMN,"Paris");
        temp2.put(THIRD_COLUMN,"2-5-2017 20:00:00");
        temp2.put(FOURTH_COLUMN,"3-9-2017 20:00:00");
        list.add(temp2);

        HashMap<String, String> temp3 = new HashMap<String, String>();
        temp3.put(FIRST_COLUMN,"Frankfurt");
        temp3.put(SECOND_COLUMN,"Valencia");
        temp3.put(THIRD_COLUMN,"6-6-2017 20:00:00");
        temp3.put(FOURTH_COLUMN,"7-11-2017 20:00:00");
        list.add(temp3);

        HashMap<String, String> temp4 = new HashMap<String, String>();
        temp4.put(FIRST_COLUMN,"Berlin");
        temp4.put(SECOND_COLUMN,"Copenhagen");
        temp4.put(THIRD_COLUMN,"6-2-2016 20:00:00");
        temp4.put(FOURTH_COLUMN,"16-5-2016 20:00:00");
        list.add(temp4);

        HashMap<String, String> temp5 = new HashMap<String, String>();
        temp5.put(FIRST_COLUMN,"Lisabon");
        temp5.put(SECOND_COLUMN,"Moscow");
        temp5.put(THIRD_COLUMN,"2-9-2017 20:00:00");
        temp5.put(FOURTH_COLUMN,"11-9-2017 20:00:00");
        list.add(temp5);

        HashMap<String, String> temp6 = new HashMap<String, String>();
        temp6.put(FIRST_COLUMN,"Torino");
        temp6.put(SECOND_COLUMN,"Monaco");
        temp6.put(THIRD_COLUMN,"23-5-2017 23:00:00");
        temp6.put(FOURTH_COLUMN,"21-6-2017 20:00:00");
        list.add(temp6);

        HashMap<String, String> temp7 = new HashMap<String, String>();
        temp7.put(FIRST_COLUMN,"Dortmund");
        temp7.put(SECOND_COLUMN,"Istanbul");
        temp7.put(THIRD_COLUMN,"23-5-2017 4:00:00");
        temp7.put(FOURTH_COLUMN,"25-9-2017 20:00:00");
        list.add(temp7);

        HashMap<String, String> temp8 = new HashMap<String, String>();
        temp8.put(FIRST_COLUMN,"Belgrade");
        temp8.put(SECOND_COLUMN,"Rome");
        temp8.put(THIRD_COLUMN,"14-4-2016 20:00:00");
        temp8.put(FOURTH_COLUMN,"15-8-2016 20:00:00");
        list.add(temp8);

        HashMap<String, String> temp9 = new HashMap<String, String>();
        temp9.put(FIRST_COLUMN,"Sevilla");
        temp9.put(SECOND_COLUMN,"Amsterdam");
        temp9.put(THIRD_COLUMN,"7-9-2016 20:00:00");
        temp9.put(FOURTH_COLUMN,"11-10-2016 20:00:00");
        list.add(temp9);

        HashMap<String, String> temp10 = new HashMap<String, String>();
        temp10.put(FIRST_COLUMN,"Munich");
        temp10.put(SECOND_COLUMN,"Paris");
        temp10.put(THIRD_COLUMN,"2-5-2017 20:00:00");
        temp10.put(FOURTH_COLUMN,"3-9-2017 20:00:00");
        list.add(temp10);

        HashMap<String, String> temp11 = new HashMap<String, String>();
        temp11.put(FIRST_COLUMN,"Frankfurt");
        temp11.put(SECOND_COLUMN,"Valencia");
        temp11.put(THIRD_COLUMN,"6-6-2017 20:00:00");
        temp11.put(FOURTH_COLUMN,"7-11-2017 20:00:00");
        list.add(temp11);

        HashMap<String, String> temp12 = new HashMap<String, String>();
        temp12.put(FIRST_COLUMN,"Berlin");
        temp12.put(SECOND_COLUMN,"Copenhagen");
        temp12.put(THIRD_COLUMN,"6-2-2016 20:00:00");
        temp12.put(FOURTH_COLUMN,"16-5-2016 20:00:00");
        list.add(temp12);

        HashMap<String, String> temp13 = new HashMap<String, String>();
        temp13.put(FIRST_COLUMN,"Lisabon");
        temp13.put(SECOND_COLUMN,"Moscow");
        temp13.put(THIRD_COLUMN,"2-9-2017 20:00:00");
        temp13.put(FOURTH_COLUMN,"11-9-2017 20:00:00");
        list.add(temp13);

        HashMap<String, String> temp14 = new HashMap<String, String>();
        temp14.put(FIRST_COLUMN,"Torino");
        temp14.put(SECOND_COLUMN,"Monaco");
        temp14.put(THIRD_COLUMN,"23-5-2017 23:00:00");
        temp14.put(FOURTH_COLUMN,"21-6-2017 20:00:00");
        list.add(temp14);

        HashMap<String, String> temp15 = new HashMap<String, String>();
        temp15.put(FIRST_COLUMN,"Dortmund");
        temp15.put(SECOND_COLUMN,"Istanbul");
        temp15.put(THIRD_COLUMN,"23-5-2017 4:00:00");
        temp15.put(FOURTH_COLUMN,"25-9-2017 20:00:00");
        list.add(temp15);


        @SuppressLint("SimpleDateFormat") SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date d = new Date();

        String datum = sd.format(d);



        for(int i =0; i<list.size(); i++){

            Log.d("usao","usao");

            notificationManager =(NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            remoteViews = new RemoteViews(context.getPackageName(),R.layout.notification);

            remoteViews.setImageViewResource(R.id.notif_icon,R.mipmap.ic_launcher);


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

                    Date date_2d = DateUtil.addDays(date_compare,-2);
                    Date date_12h = DateUtil.addHours(date_compare,-12);
                    Date date_4h = DateUtil.addHours(date_compare,-4);

                    Log.d("one", String.valueOf(date_2d));
                    Log.d("two", String.valueOf(date_12h));
                    Log.d("three", String.valueOf(date_4h));

                    String destination1= list.get(i).get(FIRST_COLUMN);
                    String destination2= list.get(i).get(SECOND_COLUMN);


                    if(date_2d.after(date)){
                        remoteViews.setTextViewText(R.id.notif_title,"Flight from "+destination1+" to "+destination2+" will begin for less than 2 days");

                        Intent notification_intent = new Intent(context, Flights.class);
                        PendingIntent pendingIntent = PendingIntent.getActivity(context,i,notification_intent,i);

                        builder = new NotificationCompat.Builder(context);
                        builder.setSmallIcon(R.mipmap.ic_launcher).setAutoCancel(true).setCustomBigContentView(remoteViews).setContentIntent(pendingIntent);

                        notificationManager.notify(notification_id,builder.build());

                    }else if (date_12h.after(date)){
                        remoteViews.setTextViewText(R.id.notif_title,"Flight from "+destination1+" to "+destination2+" will begin for less than 12 hours");

                        Intent notification_intent = new Intent(context, Flights.class);
                        PendingIntent pendingIntent = PendingIntent.getActivity(context,i,notification_intent,i);

                        builder = new NotificationCompat.Builder(context);
                        builder.setSmallIcon(R.mipmap.ic_launcher).setAutoCancel(true).setCustomBigContentView(remoteViews).setContentIntent(pendingIntent);

                        notificationManager.notify(notification_id,builder.build());

                    }else if(date_4h.after(date)){
                        remoteViews.setTextViewText(R.id.notif_title,"Flight from "+destination1+" to "+destination2+" will begin for less than 4 hours");

                        Intent notification_intent = new Intent(context, Flights.class);
                        PendingIntent pendingIntent = PendingIntent.getActivity(context,i,notification_intent,i);

                        builder = new NotificationCompat.Builder(context);
                        builder.setSmallIcon(R.mipmap.ic_launcher).setAutoCancel(true).setCustomBigContentView(remoteViews).setContentIntent(pendingIntent);

                        notificationManager.notify(notification_id,builder.build());

                    }else{
                        Log.d("No notification","No notification");
                    }

                }
            }catch (Exception ex){
                Log.d("oh","oh");
                Log.d("message",ex.getMessage());
            }

        }




    }




}

