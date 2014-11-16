package example.com.myapppluswear;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Point;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Toast;


import example.com.myapppluswear.R;

import example.com.myapppluswear.fragments.DashboardsFragment;
import example.com.myapppluswear.interfaces.OnSlidingMenuListItemClicked;
import example.com.myapppluswear.SlidingMenuListFragment;

import com.microsoft.windowsazure.notifications.NotificationsManager;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity {

    protected ListFragment mFrag;
    private DashboardsFragment dashboardFragment;
    AsyncTask<Void, Void, Void> mRegisterTask;
    final List<Map<String, ?>> myAccount = new LinkedList<Map<String, ?>>();
    final List<Map<String, ?>> featureList = new LinkedList<Map<String, ?>>();

    private String SENDER_ID = "1049668455499";
    //private GoogleCloudMessaging gcm;
    //private NotificationHub hub;
    private Notifications notifications;

    NotificationManager mNotificationManager;
    private OnSlidingMenuListItemClicked slidingMenuListItemClicked = new OnSlidingMenuListItemClicked() {
        @Override
        public void onSlidingMenuListItemClicked() {

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // set the Main Content view
        setContentView(R.layout.activity_main);

        // set the Menu
        setBehindContentView(R.layout.slidingmenu_frame);
        FragmentTransaction t = this.getSupportFragmentManager()
                .beginTransaction();
        mFrag = new SlidingMenuListFragment(slidingMenuListItemClicked);
        t.replace(R.id.menu_frame, mFrag);
        t.commit();

        // customize the SlidingMenu
        SlidingMenu sm = getSlidingMenu();
        // sm.setShadowWidthRes(R.dimen.shadow_width);
        // sm.setShadowDrawable(R.drawable.shadow);
        // sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        sm.setFadeDegree(0.35f);
        // sm.setBehindWidth(450);
        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);

        int width = 0;
        Display display = ((WindowManager) getBaseContext().getSystemService(
                Context.WINDOW_SERVICE)).getDefaultDisplay();
        try {
            Class<?> cls = Display.class;
            Class<?>[] parameterTypes = { Point.class };
            Point parameter = new Point();
            Method method = cls.getMethod("getSize", parameterTypes);
            method.invoke(display, parameter);
            width = parameter.x;
        } catch (Exception e) {
            width = display.getWidth();
        }
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            sm.setBehindWidth((int) (width * 0.4));
        } else
            sm.setBehindWidth((int) (width * 0.75));

        // Removed the title. Requested by Khoo
        getActionBar().setTitle("");
        getActionBar().setIcon(android.R.drawable.ic_menu_my_calendar);
        getActionBar().setDisplayHomeAsUpEnabled(false);
        getActionBar().setHomeButtonEnabled(true);


        NotificationsManager.handleNotifications(this, SENDER_ID, NotificationHandler.class);
       // Intent intent = new Intent(this, GcmIntentService.class);
       // startService(intent);
        //gcm = GoogleCloudMessaging.getInstance(this);

        //"live-sport", "Endpoint=sb://live-sport.servicebus.windows.net/;SharedAccessKeyName=DefaultListenSharedAccessSignature;SharedAccessKey=9hPtVm2Uyi7Bl0D1hUtID3DYCYSDiTz7vmiXd7oPavc="
        //String connectionString = "<your listen access connection string>";
        //hub = new NotificationHub("<your notification hub name>", connectionString, this);

        notifications = new Notifications(this, SENDER_ID);
        notifications.subscribeToCategories(notifications.retrieveCategories());

        FragmentTransaction d_fragment = this.getSupportFragmentManager()
                .beginTransaction();
        dashboardFragment = new DashboardsFragment();

        d_fragment.replace(R.id.content_frame, dashboardFragment);
        d_fragment.commit();


    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                toggle();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
