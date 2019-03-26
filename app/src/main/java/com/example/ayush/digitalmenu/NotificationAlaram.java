package com.example.ayush.digitalmenu;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ayush.digitalmenu.MainMenu.MainMenuActivity;

import java.util.Calendar;

public class NotificationAlaram extends AppCompatActivity {

    Button button1, button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_alaram);

        button1 = findViewById(R.id.notification);
        button2 = findViewById(R.id.alarm);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            Notify(NotificationAlaram.this);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAlarm();
            }
        });
    }

    private void Notify(Context context) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        Intent notifyintent = new Intent(context, MainMenuActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notifyintent, 0);

        Notification notification = new Notification();
        notification.defaults |= Notification.DEFAULT_SOUND;

        NotificationCompat.Builder builder;
        builder = new NotificationCompat.Builder(context);
        builder.setLights(Color.BLUE, 500, 500);
        long[] pattern = {500,500,500,500,500}; // Ekchoti vibrate garna 2 ota 500 use garnu parx lik
        builder.setVibrate(pattern);
        builder.setStyle(new NotificationCompat.InboxStyle());

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(alarmSound);

        notification = builder.setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher).setTicker("ticker")
                .setWhen(System.currentTimeMillis()).setAutoCancel(true)
                .setContentTitle("Notification Starts")
                .setContentText("Hello I am notification").build();

        notificationManager.notify(1010, notification);
    }

    public void setAlarm() {

        Intent alarmintent = new Intent(this, OurReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmintent, 0);

        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        long currentTImeX = System.currentTimeMillis();
        long chainTimeY = 6 * 60 * 60 * 100;

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, 1);
        calendar.set(Calendar.SECOND, 1);

        calendar.getTimeInMillis();

        long value = (24 * 60 * 60 * 100 - currentTImeX) + chainTimeY;

        manager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntent);
    }
}
