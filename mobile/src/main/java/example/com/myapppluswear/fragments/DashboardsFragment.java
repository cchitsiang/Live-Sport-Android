package example.com.myapppluswear.fragments;

import java.util.HashSet;
import java.util.Set;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import example.com.myapppluswear.MainActivity;
import example.com.myapppluswear.Notifications;
import example.com.myapppluswear.R;
import example.com.myapppluswear.ViewActivity;

public class DashboardsFragment extends Fragment {

    final static String GROUP_KEY_EMAILS = "group_key_emails";

    private String SENDER_ID = "1049668455499";
    //private GoogleCloudMessaging gcm;
    //private NotificationHub hub;
    private Notifications notifications;
    private Button btn_push;
    private Button btn_push2;
    private Button btn_push3;
    private Button btn_push4;

    NotificationManager mNotificationManager;
	public DashboardsFragment() {
	}

	private boolean mInProgress;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_dashboard, container, false);

        btn_push = (Button)v.findViewById(R.id.btnPush);
        btn_push2 = (Button)v.findViewById(R.id.btnPush2);
        btn_push3 = (Button)v.findViewById(R.id.btnPush3);
        btn_push4 = (Button)v.findViewById(R.id.btnPush4);


        btn_push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //selfPushNotification("Equalizer!!!");
                testPushNotification("Equalizer!!!", "Arteta just scored a penalty!", R.drawable.minutes);
            }
        });

        btn_push2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testPushNotification("Match going to start in 15 mins.", "Kick off at 11.00 p.m.", R.drawable.arsvsliv2);
            }
        });

        btn_push3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testPushNotification("Suarez just scored. :(", "Liverpool is leading now...", R.drawable.zeroone);
            }
        });


        btn_push4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testPushNotification("Match ended with 1-1.", "Not too shabby right mate?", R.drawable.arsvslivend);
            }
        });


		return v;


	}

    private void testPushNotification(String title, String description, int id)
    {
        int NOTIFICATION_ID =1;
        mNotificationManager = (NotificationManager)
                getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(getActivity(), 0,
                new Intent(getActivity(), MainActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getActivity())
                        .setSmallIcon(R.drawable.wearablelogo)
                        .setContentTitle(title)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(description))
                        .setContentText(description)
                        .extend(new NotificationCompat.WearableExtender().setBackground(BitmapFactory.decodeResource(getActivity().getResources(), id)))
                        .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});


        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

    private void selfPushNotification(String title)
    {
        int notificationId = 1;
        // Build intent for notification content
        Intent viewIntent = new Intent(getActivity(), ViewActivity.class);
        //viewIntent.putExtra(EXTRA_EVENT_ID, eventId);
        PendingIntent viewPendingIntent =
                PendingIntent.getActivity(getActivity(), 0, viewIntent, 0);

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
                new NotificationCompat.Builder(getActivity())
                        .setStyle(secondPageStyle)
                        .build();

        Notification thirdPageNotification = new NotificationCompat.Builder(getActivity()).setStyle(thirdPageStyle).build();

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(getActivity())
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle(title)
                        .setContentText("Manchester United mu - 0 Manchester City")
                        .addAction(R.drawable.ic_launcher, "Open Main", viewPendingIntent)
                        .extend(new NotificationCompat.WearableExtender().addAction(wearableAction).addPage(secondPageNotification).setBackground(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher)))
                        .setStyle(bigStyle)
                        .setContentIntent(viewPendingIntent);

        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(getActivity());

        // Build the notification and issues it with notification manager.
        notificationManager.notify(notificationId, notificationBuilder.build());
        notificationManager.notify(2, thirdPageNotification);
    }

    public void subscribe() {
        final Set<String> categories = new HashSet<String>();

        categories.add("sports");

        notifications.storeCategoriesAndSubscribe(categories);
    }


	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getActivity().getActionBar().setTitle(getResources().getString(R.string.menu_title_dashboards));
	}

}
