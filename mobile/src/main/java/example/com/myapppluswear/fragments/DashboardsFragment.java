package example.com.myapppluswear.fragments;

import java.util.List;

import android.app.Dialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.commonsware.cwac.merge.MergeAdapter;
import example.com.myapppluswear.R;
import example.com.myapppluswear.interfaces.ViewActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class DashboardsFragment extends Fragment {

	public DashboardsFragment() {
	}

	private boolean mInProgress;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_dashboard, container, false);

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
                        .setContentTitle("Status")
                        .setContentText("I'm at a bus stop!")
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
		return v;

	}



	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getActivity().getActionBar().setTitle(getResources().getString(R.string.menu_title_dashboards));
	}

}
