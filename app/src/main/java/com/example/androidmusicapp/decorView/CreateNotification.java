package com.example.androidmusicapp.decorView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.media.session.MediaSessionCompat;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.androidmusicapp.R;
import com.example.androidmusicapp.model.entity.Song;

public class CreateNotification {
    public static final String CHANNEL_ID = "channel1";
    public static final String ACTIONPREVIOUS = "actionprevious";
    public static final String ACTIONNEXT = "actionnext";
    public static final String ACTIONPLAY = "actionplay";
    public static android.app.Notification notification;

    @SuppressLint("NotConstructor")
    public void createNotification(Context context, Song song, int playpause, int pos, int size) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
            MediaSessionCompat mediaSessionCompat = new MediaSessionCompat(context, "tag");

//            Glide.with(context)
//                    .asBitmap()
//                    .load(song.getCoverArt());
            //.into();

            notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_logo_music)
                    .setContentTitle(song.getTitle())
                    .setOnlyAlertOnce(true)
                    .setShowWhen(false)
                    .setPriority(NotificationCompat.PRIORITY_LOW)
                    .build();

            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            notificationManagerCompat.notify(1, notification);
        }
    }

}
