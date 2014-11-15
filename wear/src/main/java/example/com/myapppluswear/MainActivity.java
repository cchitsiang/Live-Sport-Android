package example.com.myapppluswear;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.wearable.view.WatchViewStub;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
//                mTextView = (TextView) stub.findViewById(R.id.text);
//                mTextView.setText("Hello WEAR");

                int notificationId = 1;
                // Build intent for notification content
                Intent viewIntent = new Intent(stub.getContext(), MainActivity.class);
                //viewIntent.putExtra(EXTRA_EVENT_ID, eventId);
                PendingIntent viewPendingIntent =
                        PendingIntent.getActivity(stub.getContext(), 0, viewIntent, 0);

                // Specify the 'big view' content to display the long
                // event description that may not fit the normal content text.
                NotificationCompat.BigTextStyle bigStyle = new NotificationCompat.BigTextStyle();
                String eventDescription = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";
                bigStyle.bigText(eventDescription);

                NotificationCompat.Action wearableAction = new NotificationCompat.Action(R.drawable.ic_launcher, "Reply", viewPendingIntent);


                // Create a big text style for the second page
                NotificationCompat.BigTextStyle secondPageStyle = new NotificationCompat.BigTextStyle();
                secondPageStyle.setBigContentTitle("Page 2")
                        .bigText("A lot of text...");

                NotificationCompat.BigTextStyle thirdPageStyle = new NotificationCompat.BigTextStyle();
                thirdPageStyle.setBigContentTitle("Page 3")
                        .bigText("Lorem");

                // Create second page notification
                Notification secondPageNotification =
                        new NotificationCompat.Builder(stub.getContext())
                                .setStyle(secondPageStyle)
                                .build();

                Notification thirdPageNotification = new NotificationCompat.Builder(stub.getContext()).setStyle(thirdPageStyle).build();

                NotificationCompat.Builder notificationBuilder =
                        new NotificationCompat.Builder(stub.getContext())
                                .setSmallIcon(R.drawable.ic_launcher)
                                .setContentTitle("Status")
                                .setContentText("I'm at a bus stop!")
                                .addAction(R.drawable.ic_launcher, "Open Main", viewPendingIntent)
                                .extend(new NotificationCompat.WearableExtender().addAction(wearableAction).addPage(secondPageNotification).setBackground(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher)))
                                .setStyle(bigStyle)
                                .setContentIntent(viewPendingIntent);

                // Get an instance of the NotificationManager service
                NotificationManagerCompat notificationManager =
                        NotificationManagerCompat.from(stub.getContext());

                // Build the notification and issues it with notification manager.
                notificationManager.notify(notificationId, notificationBuilder.build());
                notificationManager.notify(2, thirdPageNotification);
            }
        });
    }
}
