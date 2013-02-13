package com.example.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.widget.ProgressBar;

import com.example.test.ItemAdapterFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.example.test.MainActivity.SectionsPagerAdapter;

public class LoadActivity extends Activity implements Observer {

	private Handler progressBarHandler = new Handler();
	private ViewPager mViewPager;
	private Data data;
	private List<Item> Items = new ArrayList<Item>();
	private SectionsPagerAdapter mSectionsPagerAdapter;
	private ProgressBar progressBar;
	int iterateur =0;
	private Intent mainActivity;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_load);
		mViewPager = (ViewPager) findViewById(R.id.pager);
		data=new Data();
		progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		progressBar.setProgress(0);
		progressBar.setMax(data.getMax());
		//progressBar.show();
		mainActivity = new Intent(this, MainActivity.class);
		
		data.addObserver(this);
		data.InitializePreferences(getSharedPreferences("AppECM", MODE_PRIVATE));
		
		 Thread thread1 = new Thread() {
             public void run() {
                 while (iterateur < progressBar.getMax()) {
                     

                     // Update the progress bar
                     progressBarHandler.post(new Runnable() {
                         public void run() {
                        	 progressBar.setProgress(data.getsize());
                        	 progressBar.setMax(data.getMax());
                        	 //Log.v("run", "Data.getsize()"+data.getsize());
                        	 //Log.v("run", "Data.getMax()"+data.getMax());
                        	
                        	 
                         }
                     });
                 }
             }
         };
		thread1.start();
		
		DownloadHandler D = new DownloadHandler();
		D.run();
		
		
		Log.v("Counter",
				"Items recus par la main activity"
						+ Integer.toString(data.getsize()));
		
//		 while  (! (data.getsize()==137)){
//    		 //Log.v("nexactivity", "Launch"); 
//    		 startActivity(mainActivity);
//    	 }
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_load, menu);
		return true;
	}

	public class DownloadHandler extends TimerTask {

		private Runnable H = null;

		@Override
		public void run() {
			Handler mHandler = new Handler(Looper.getMainLooper());

			mHandler.post(H = new Runnable() {
				public void run() {
					Thread thread = new Thread() {
						DownloadTask DLT = new DownloadTask(
								getApplicationContext(), progressBarHandler,data);

						@Override
						public void run() {
							Looper.prepare();
							try {
								DLT.execute(new ViewPager(getBaseContext()));

								Log.v("Counter",
										"Items recus par la main activity"
												+ Integer.toString(data.getsize()));
								Items = DLT.getItems();
								System.out
										.println("Process Accomplished Runned");

							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					};
					thread.start();

				}

			});
			H.run();

		}

	}

	@Override
	public void update(Observable observable, Object data) {
		// TODO Auto-generated method stub
		Log.v("IsLoaded","Observer Updated");
		startActivity(new Intent(getBaseContext(),MainActivity.class));
	}

}
