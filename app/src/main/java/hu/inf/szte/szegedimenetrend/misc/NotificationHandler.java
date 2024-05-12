package hu.inf.szte.szegedimenetrend.misc;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import androidx.core.app.NotificationCompat;

import hu.inf.szte.szegedimenetrend.R;
import hu.inf.szte.szegedimenetrend.activity.JaratActivity;

public class NotificationHandler {
    public static final String CHANNEL_ID = "Szegedi menetrend";
    public static final String CHANNEL_NAME = "Szegedi menetrend";
    public static int NOTIFICATION_ID = 0;

    private NotificationManager manager;
    private Context context;

    public NotificationHandler(Context context) {
        this.context = context;
        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
        channel.enableVibration(true);
        channel.enableLights(true);
        channel.setLightColor(Color.YELLOW);
        channel.setDescription("Értesítések a szegedi menetrendről");
        channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});
        manager.createNotificationChannel(channel);
    }

    public void send(String title, String message) {
        Intent intent = new Intent(context, JaratActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.logo_transparent)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        manager.notify(++NOTIFICATION_ID, builder.build());     // Android 13 felett kell hozzá permission
    }
}

