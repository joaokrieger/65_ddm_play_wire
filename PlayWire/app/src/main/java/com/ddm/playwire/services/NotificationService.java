package com.ddm.playwire.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.ddm.playwire.R;
import com.ddm.playwire.ui.main.MenuActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class NotificationService extends FirebaseMessagingService {

    private static NotificationService instance;

    public static NotificationService getInstance(){
        if(instance == null){
            instance = new NotificationService();
        }
        return instance;
    }

    private NotificationService(){}

    @Override
    public void onNewToken(@NonNull String token) {
        Log.d("Token", "Refreshed token: " + token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getNotification() != null) {
            showNotification(
                remoteMessage.getNotification().getTitle(),
                remoteMessage.getNotification().getBody(),
                    this
            );
        }
    }

    public void showNotification(String title, String message, Context context) {

        try {
            Intent intent = new Intent(context, MenuActivity.class);
            String channel_id = "notification_channel";
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channel_id)
                    .setSmallIcon(R.drawable.ic_videogame)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                    .setOnlyAlertOnce(true)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel notificationChannel = new NotificationChannel(channel_id, "notification_channel", NotificationManager.IMPORTANCE_HIGH);
                notificationManager.createNotificationChannel(notificationChannel);
            }
            notificationManager.notify(0, builder.build());
        }
        catch (Exception e){
            Log.d("Notification Exception",e.getMessage());
        }
    }
}