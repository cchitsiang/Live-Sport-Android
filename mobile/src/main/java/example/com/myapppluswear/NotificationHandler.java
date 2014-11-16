package example.com.myapppluswear;

/**
 * Created by ChitSiang on 11/16/2014.
 */

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import org.json.JSONException;
import org.json.JSONObject;

public class NotificationHandler  extends com.microsoft.windowsazure.notifications.NotificationsHandler{

    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
    Context ctx;
    private String jsonObject;

    @Override
    public void onReceive(Context context, Bundle bundle) {
        ctx = context;
        String nhMessage = bundle.getString("message");
        sendNotification(nhMessage);
    }


    private void sendNotification(String msg) {

        JSONObject obj = null;
        String matchId = "DEFAULT";
        String startTime = "DEFAULT";
        String minutes = "DEFAULT";


        mNotificationManager = (NotificationManager)
                ctx.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(ctx, 0,
                new Intent(ctx, MainActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(ctx)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("FootBall News")
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                        .setContentText(msg)
                        .extend(new NotificationCompat.WearableExtender().setBackground(BitmapFactory.decodeResource(ctx.getResources(), R.drawable.ic_launcher)))
                        .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});


        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());

    }

}
