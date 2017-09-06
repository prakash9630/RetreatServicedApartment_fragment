package com.example.prakash.RetreatServicedApartment.Firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.text.Html;

import com.example.prakash.RetreatServicedApartment.R;
import com.google.firebase.messaging.RemoteMessage;



public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage){







        this.showNotification(remoteMessage.getNotification().getBody());



    }

    private void showNotification(String message){


        Intent i = new Intent(this,NewsNotification.class);
        i.putExtra("myInfo",message);

        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri sound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle("Retreat Apartment")
                .setContentText(Html.fromHtml(message))
                .setSmallIcon(R.mipmap.launcher)
                .setSound(sound)
                .setContentIntent(pendingIntent);



        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(0,builder.build());
    }

}
