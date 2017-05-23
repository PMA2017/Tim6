package rs.flightbooking;

import android.app.Fragment;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;

import static android.content.Context.NOTIFICATION_SERVICE;


/**
 * Created by Rale on 4/23/2017.
 */

public class Informations extends Fragment {
    public Informations() {
    }

    private Context context;
    private RemoteViews remoteViews;
    private NotificationCompat.Builder builder;
    private NotificationManager notificationManager;
    private int notification_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.activity_informations, container, false);

        context = rootView.getContext();


        notificationManager =(NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        remoteViews = new RemoteViews(context.getPackageName(),R.layout.notification);

        remoteViews.setImageViewResource(R.id.notif_icon,R.mipmap.ic_launcher);
        remoteViews.setTextViewText(R.id.notif_title,"Notification");

        notification_id =(int) System.currentTimeMillis();
        Intent button_intent =new Intent("button_clicked");
        button_intent.putExtra("id",notification_id);
        PendingIntent p_button_intent = PendingIntent.getBroadcast(context,123,button_intent,0);
        remoteViews.setOnClickPendingIntent(R.id.notif_button,p_button_intent);

        rootView.findViewById(R.id.button_notification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent notification_intent = new Intent(context, Informations.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(context,0,notification_intent,0);

                builder = new NotificationCompat.Builder(context);
                builder.setSmallIcon(R.mipmap.ic_launcher).setAutoCancel(true).setCustomBigContentView(remoteViews).setContentIntent(pendingIntent);

                notificationManager.notify(notification_id,builder.build());
            }
        });

        return rootView;
    }



}
