package com.example.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.test.ItemAdapterFragment;
import com.google.android.maps.MyLocationOverlay;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements OnClickListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	private Handler progressBarHandler = new Handler();
	Data data;
	private List<Item> Items;
	private List<String> categories;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// Items.add(new Item(0, "test", "test", "test", "test", "test", "test",
		// "test", 0, 0, false));
		Items = new ArrayList<Item>();
		categories = new ArrayList<String>();

		DownloadHandler D = new DownloadHandler();
		D.run();

		for (int i = 0; i < Items.size(); i++) {
			Item IteratorItem = Items.get(i);
			String itemcategorie = IteratorItem.getCategorie();
			if (!categories.contains(itemcategorie)) {
				categories.add(itemcategorie);
			}
		}

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		Items = data.getItems();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub

	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			if (position == 0) {

				ItemAdapter a = new ItemAdapter(getApplicationContext(), Items);
				Fragment fragment = new ListItemFragment(a, false);
				return fragment;
			}
			if (position == 1) {

				ItemAdapter a = new ItemAdapter(getApplicationContext(), Items);
				Fragment fragment = new ListItemFragment(a, true);
				return fragment;
			}
			if (position == 2) {
				Intent intentMapActivity = new Intent();
				intentMapActivity.setClass(getBaseContext(),MapActivity.class);
				
				startActivity(intentMapActivity);
				
				ItemAdapter a = new ItemAdapter(getApplicationContext(), Items);
				Fragment fragment = new ListItemFragment(a, false);
				return fragment;
			}

			else {
				Fragment fragment = new DummySectionFragment();
				Bundle args = new Bundle();
				args.putInt(DummySectionFragment.ARG_SECTION_NUMBER,
						position + 2);
				fragment.setArguments(args);
				return fragment;
			}
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase();
			case 1:
				return getString(R.string.title_section2).toUpperCase();
			case 2:
				return getString(R.string.title_section3).toUpperCase();
			}
			return null;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// Create a new TextView and set its text to the fragment's section
			// number argument value.
			TextView textView = new TextView(getActivity());
			textView.setGravity(Gravity.CENTER);
			textView.setText(Integer.toString(getArguments().getInt(
					ARG_SECTION_NUMBER)));
			return textView;
		}

	}

	public class ListItemFragment extends Fragment implements
			OnItemClickListener {
		private ItemAdapter mitemadapter;
		private boolean fav_list;

		public ListItemFragment(ItemAdapter mitemadapter, boolean fav_list) {
			super();
			this.mitemadapter = mitemadapter;
			this.fav_list = fav_list;
			mitemadapter.setIs_fav(fav_list);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// setContentView(R.layout.list);
			// TODO Auto-generated method stub
			ListView mListView = new ListView(getApplication());
			// mListView= (ListView) findViewById(R.id.list);
			mListView.setAdapter(mitemadapter);
			mListView.setActivated(true);
			mListView.setBackgroundColor(Color.BLACK);
			mListView.setOnItemClickListener(this);

			TextView textView = new TextView(getActivity());
			textView.setGravity(Gravity.CENTER);
			textView.setText("On m'execute bien, tout va bien \n"
					+ mitemadapter.getCount());
			return mListView;
		}

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			// TODO Auto-generated method stub
			Intent intentItemActivity = new Intent();
			intentItemActivity.setClass(getBaseContext(), ItemActivity.class);
			intentItemActivity.putExtra("item", Items.get(position).getId());
			Log.v("ItemActivity launcher", "Position of Item" + position);
			Log.v("ItemActivity launcher", "Arg3 of Item" + arg3);
			Log.v("ItemActivity launcher", "Id of Item"
					+ Items.get(position).getId());
			startActivity(intentItemActivity);

		}

	}

	public class DownloadHandler extends TimerTask {

		private Runnable H = null;

		@Override
		public void run() {
			Handler mHandler = new Handler(Looper.getMainLooper());

			mHandler.post(H = new Runnable() {
				public void run() {
					DownloadTask DLT = new DownloadTask(
							mViewPager.getContext(), progressBarHandler);
					DLT.execute(mViewPager);
					try {
						Log.v("Counter", "Items recus par la main activity"
								+ Integer.toString(DLT.get().size()));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Items = DLT.getItems();
					System.out.println("Process Accomplished Runned");
				}

			});
			H.run();

		}

	}

}
