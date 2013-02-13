package com.example.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.appengine.repackaged.com.google.api.client.json.Json;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class DownloadTask extends AsyncTask<ViewPager, Integer, List<Item>> {

	private List<Item> Items;
	private Data data;
	private Context context;
	private ViewPager v;
	private HttpClient httpClient = new DefaultHttpClient();
	private HttpGet httpGet = new HttpGet("http://cci.corellis.eu/pois.php");
	private boolean loaded = false;
	private String line = "";
	private InputStream inputStream;
	private JSONObject jsonObject;
	private JSONArray jsonArray;
	private int iterateur =0;

	public DownloadTask(Context c, Handler h,Data d) {
		super();
		context = c;
		data=d;
		Log.v("Observercheck","Observer dans le DLT" +data.countObservers());
	}

	public List<Item> getItems() {
		return Items;
	}

	public void Loading() {

		try {
			
				JSONObject jSonObjecttoextract = jsonArray.getJSONObject(iterateur);
				long itemid = jSonObjecttoextract.getLong("id");
				String itemcategorie = jSonObjecttoextract
						.getString("categorie_id");
				String itemname;
				itemname = jSonObjecttoextract.getString("nom");
				String itemsector = jSonObjecttoextract.getString("secteur");
				String itemdescription = jSonObjecttoextract
						.getString("informations");
				String itemquartier = jSonObjecttoextract.getString("quartier");
				String itemimage = jSonObjecttoextract.getString("image");
				String itemicon = jSonObjecttoextract.getString("small_image");
				double itemlat = jSonObjecttoextract.getDouble("lat");
				double itemlon = jSonObjecttoextract.getDouble("lon");
				Item newItem = new Item(itemid, itemname, itemsector,
						itemquartier, itemcategorie, itemdescription, itemicon,
						itemimage, itemlon, itemlat, false);

				Items.add(newItem);
				data.addItem(newItem);
				// Log.v("Counter", newItem.toString());
				// Log.v("Counter", i + "/" + jsonArray.length());
				this.publishProgress(0);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
		

	}

	private String convertStreamToString(InputStream inputStream) {
		String ligne = "";
		StringBuilder total = new StringBuilder();
		BufferedReader rd = new BufferedReader(new InputStreamReader(
				inputStream));
		try {
			while ((ligne = rd.readLine()) != null) {
				total.append(ligne);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return total.toString();
	}

	@Override
	protected void onPostExecute(List<Item> result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);

		Log.v("counter", Integer.toString(Items.size()));
		//data.addItems(Items);
		Log.v("counter data", Integer.toString(data.getsize()));
		data.IsLoaded();
		data.InitializeFavori();
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		Items = new ArrayList<Item>();

		try {
			HttpResponse response = httpClient.execute(httpGet);
			if (response != null) {
				String line = "";
				InputStream inputStream = response.getEntity().getContent();
				line = convertStreamToString(inputStream);

				

				jsonObject = new JSONObject(line);

				jsonArray = jsonObject.getJSONArray("results");
				data.setMax(jsonArray.length());
				Log.v("Counter", "Taille de l'ensemble : " + jsonArray.length());
			} else {
				Log.v("Error", "Reponse = NULL");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	protected List<Item> doInBackground(ViewPager... params) {
		// TODO Auto-generated method stub
		// v = params[0];

		// progressBar = new
		// ProgressDialog(v.findViewById(R.id.progressBar1).getContext());

		// progressBar.show();

		Log.v("LoadStart", "Loading starting");
		for (iterateur=0;iterateur<jsonArray.length();iterateur++){
			this.Loading();
		}

		this.publishProgress(0);

		return Items;
	}

}
