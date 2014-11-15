
package example.com.myapppluswear;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Fragment;
import android.support.v4.app.ListFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import android.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.commonsware.cwac.merge.MergeAdapter;

import example.com.myapppluswear.R;

import example.com.myapppluswear.fragments.DashboardsFragment;
import example.com.myapppluswear.interfaces.OnSlidingMenuListItemClicked;

import com.slidingmenu.lib.app.SlidingFragmentActivity;

public class SlidingMenuListFragment extends ListFragment {

    // private ListView list;
    private MergeAdapter adapter = null;

    private ArrayList<String> mListDefaultItems = null;


    private OnSlidingMenuListItemClicked listener;

    public SlidingMenuListFragment() {
        this.listener = null;
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public SlidingMenuListFragment(
            OnSlidingMenuListItemClicked slidingMenuListItemClicked) {
        this.listener = slidingMenuListItemClicked;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.slidingmenu_list, null);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new MergeAdapter();

        adapter.addView(buildDefaultItemsHeader());
        adapter.addAdapter(buildDefaultTypesList());

        setListAdapter(adapter);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (listener != null)
            listener.onSlidingMenuListItemClicked();


        // Implementation here...
        if (v.getTag() instanceof DefaultItemWrapper) {
            DefaultItemWrapper wrapper = (DefaultItemWrapper) v.getTag();

            if (wrapper.getLabel().getText() == getActivity().getResources()
                    .getString(R.string.menu_title_dashboards)) {

                String Tag = "dashboards";
                DashboardsFragment myFragment = (DashboardsFragment) getFragmentManager()
                        .findFragmentByTag(Tag);

                if (myFragment == null|| myFragment.isVisible() == false) {
                    FragmentTransaction t_map = getActivity()
                            .getFragmentManager()
                            .beginTransaction();
                    Fragment dashboardFragment = new Fragment();
                    Bundle b = new Bundle();
                    dashboardFragment.setArguments(b);
                    t_map.replace(R.id.content_frame, dashboardFragment, Tag);
                    t_map.commit();

                }
                ((SlidingFragmentActivity) getActivity()).toggle();
            }else {

            }

        }
    }

    /* Default Items */
    private View buildDefaultItemsHeader() {
        View view = LayoutInflater.from(getActivity()).inflate(
                R.layout.slidingmenu_list_header_image, null);

//		ImageView imageView = (ImageView) view.findViewById(R.id.logo);
//		imageView.setImageResource(R.drawable.ic_launcher);

//		TextView textView = (TextView) view.findViewById(R.id.title);
//		textView.setText(getResources().getString(R.string.app_name));
        return view;
    }

    private DefaultItemAdapter<String> buildDefaultTypesList() {
        mListDefaultItems = new ArrayList<String>();
        mListDefaultItems.add(getActivity().getResources().getString(R.string.menu_title_dashboards));

        return (new DefaultItemAdapter<String>(getActivity(),
                R.layout.slidingmenu_item_default, mListDefaultItems));
    }

    class DefaultItemWrapper {
        View row = null;
        TextView label = null;
        public int Position = 0;

        DefaultItemWrapper(View row) {
            this.row = row;
        }

        DefaultItemWrapper(View row, int position) {
            this.row = row;
            this.Position = position;
        }

        TextView getLabel() {
            if (label == null) {
                label = (TextView) row.findViewById(R.id.label);
            }
            return (label);
        }
    }

    class DefaultItemAdapter<T> extends ArrayAdapter<T> {
        private int mLayout;

        DefaultItemAdapter(Context ctxt, int textViewResourceId, List<T> objects) {
            super(ctxt, textViewResourceId, objects);
            mLayout = textViewResourceId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            DefaultItemWrapper wrapper = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity())
                        .inflate(mLayout, parent, false);
                wrapper = new DefaultItemWrapper(convertView, position);
                convertView.setTag(wrapper);
            } else {
                wrapper = (DefaultItemWrapper) convertView.getTag();
            }

            wrapper.getLabel().setText(mListDefaultItems.get(position));

            return (convertView);
        }
    }
}
