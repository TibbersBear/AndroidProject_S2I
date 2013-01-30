package com.example.test;



import android.os.Bundle;
import android.app.Activity;
import android.content.DialogInterface;
import android.view.View.OnClickListener;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemActivity extends Activity implements OnClickListener {
	Data data;
	Item item;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item);
		Bundle extras = getIntent().getExtras();
		long tempid = extras.getLong("item");//(Long) getIntent().getExtras().get("item");
		Log.v("ItemActivity","Id of Item"+tempid);
		item =data.getOneItembyId(tempid);
		
		((TextView) findViewById(R.id.nom)).setText(item.getNom());
		((TextView) findViewById(R.id.quartier)).setText(item.getQuartier());
		((TextView) findViewById(R.id.secteur)).setText(item.getSecteur());
		((TextView) findViewById(R.id.description)).setText(item.getDescription().replaceAll("</br>", "\n"));
		
		((ImageView) findViewById(R.id.icone)).setImageResource(R.drawable.ic_launcher);
		if (item.isFavori()){
			((TextView) findViewById(R.id.estFavori)).setText("Favori");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_item, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId()==R.id.boutonFavoris){
			data.ChangeFavoriState(item.getId());
		}
		
	}

}
