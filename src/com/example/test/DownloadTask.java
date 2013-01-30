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

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class DownloadTask extends AsyncTask<ViewPager, Integer, List<Item>> {

	private ProgressDialog progressBar;
	private int progressBarStatus = 0;
	private List<Item> Items;
	private Data data;
	private Context C;
	private ViewPager v;
	private Handler progressBarHandler;
	private HttpClient httpClient = new DefaultHttpClient();
	private HttpGet httpGet = new HttpGet("http://cci.corellis.eu/pois.php");
	
	public DownloadTask(Context c, Handler h) {
		super();
		C = c;
		progressBarHandler=h;
	}

	
	public List<Item> getItems() {
		return Items;
	}

	public void Loading() {
		
		try {
			HttpResponse response = httpClient.execute(httpGet);
			if (response != null) {
				String line = "";
				InputStream inputStream = response.getEntity().getContent();
				line = convertStreamToString(inputStream);

				JSONObject jsonObject = new JSONObject(line);
				JSONArray jsonArray = jsonObject.getJSONArray("results");
				progressBar.setMax(jsonArray.length());
				Log.v("Counter", "Taille de l'ensemble : "+ jsonArray.length());
				for (int i = 0; i < jsonArray.length(); i++) {
					progressBar.setProgress(i);
					JSONObject jSonObjecttoextract = jsonArray.getJSONObject(i);
					long itemid = jSonObjecttoextract.getLong("id");
					String itemcategorie = jSonObjecttoextract.getString("categorie_id");
					String itemname = jSonObjecttoextract.getString("nom");
					String itemsector = jSonObjecttoextract.getString("secteur");
					String itemdescription = jSonObjecttoextract.getString("informations");
					String itemquartier = jSonObjecttoextract.getString("quartier");
					String itemimage = jSonObjecttoextract.getString("image");
					String itemicon = jSonObjecttoextract.getString("small_image");
					double itemlat = jSonObjecttoextract.getDouble("lat");
					double itemlon = jSonObjecttoextract.getDouble("lon");
					Item newItem = new Item(itemid, itemname, itemsector,
							itemquartier, itemcategorie, itemdescription,
							itemicon, itemimage, itemlon, itemlat, false);
					
					Items.add(newItem);
					 //Log.v("Counter", newItem.toString());
					 //Log.v("Counter", i + "/" + jsonArray.length());
					this.publishProgress(0);
				}

			}
			else{
				Log.v("Error", "Reponse = NULL");
				}
		}

		catch (ClientProtocolException e) {
			e.printStackTrace();
			Log.e("AppAndroidS2I", e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			Log.e("AppAndroidS2I", e.getMessage());
		} catch (JSONException e) {
			e.printStackTrace();
			Log.e("AppAndroidS2I", e.getMessage());
		}
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
		progressBar.show();
		
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
		progressBar.setProgress(100);
		progressBar.dismiss();
		Log.v("counter", Integer.toString(Items.size()));
		data.setItems(Items);
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		Items= new ArrayList<Item>();
		progressBar=new ProgressDialog(C);
		progressBar.setCancelable(false);
		progressBar.setMessage("Chargement en cours");
		progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressBar.setProgress(0);
		progressBar.setMax(100);
		
		
	}

	@Override
	protected List<Item> doInBackground(ViewPager... params) {
		// TODO Auto-generated method stub
		//v = params[0];
		
		//progressBar = new ProgressDialog(v.findViewById(R.id.progressBar1).getContext());
		
		//progressBar.show();
		
		Log.v("LoadStart", "Loading starting");
		this.Loading();
		
		this.publishProgress(0);
		
		
		return Items;
	}

	
}
