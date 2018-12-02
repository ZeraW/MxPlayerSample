package digitalsigma.com.mxplayersample.Download;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import digitalsigma.com.mxplayersample.R;
import digitalsigma.com.mxplayersample.PlayVideo.VideoPlayerActivity;

public class CustomNotificationDownload {
    private Context context;

    public CustomNotificationDownload(Context context) {
        this.context = context;
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getString(R.string.channela_name);
            String description = context.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel("NotificationIDz", name, importance);
            channel.setDescription(description);
            //color of notification
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    public void startNewNotification(String title , String body, int smallIcon){

        Intent notificationIntent = new Intent(context, VideoPlayerActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 552,notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        createNotificationChannel();
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "NotificationIDz")
                .setSmallIcon(smallIcon)
                .setContentTitle(title)
                .setColor(Color.GREEN)
                .setContentText(body)
                .setAutoCancel(true)
                .setContentIntent(contentIntent)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(body))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(5252, mBuilder.build());

    }
}