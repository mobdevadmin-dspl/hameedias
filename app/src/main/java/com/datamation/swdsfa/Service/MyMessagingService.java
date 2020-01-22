package com.datamation.swdsfa.Service;

import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.datamation.swdsfa.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyMessagingService extends FirebaseMessagingService {


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());

    }

    public void showNotification(String title, String msg){
        NotificationCompat.Builder  builder = new NotificationCompat.Builder(this,"mynotific")
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_img_notification)
                .setAutoCancel(false)
                .setContentText(msg);

        NotificationManagerCompat mnger = NotificationManagerCompat.from(this);
        mnger.notify(999, builder.build());
    }
}
