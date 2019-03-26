package com.example.ayush.digitalmenu;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.ayush.digitalmenu.MainMenu.MainMenuActivity;

public class OurReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent intent1 = new Intent(context, MainMenuActivity.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(intent1);

        Intent serviceIntent  = new Intent(context, OurService.class);
        Notify(context);
//        context.startService(serviceIntent);
    }

    private void Notify(Context context) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        Intent notifyintent = new Intent(context, MainMenuActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notifyintent, 0);

        Notification notification = new Notification();
        NotificationCompat.Builder builder;
        builder = new NotificationCompat.Builder(context);

        notification = builder.setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher).setTicker("ticker")
                .setWhen(System.currentTimeMillis()).setAutoCancel(true)
                .setContentTitle("Notification Starts")
                .setContentText("Hello I am notification").build();

        notificationManager.notify(1010, notification);
    }
}
