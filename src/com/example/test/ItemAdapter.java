package com.example.test;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemAdapter extends BaseAdapter {
	List<Item> Items;
	Data data;
	LayoutInflater inflater;
	boolean is_fav;

	public void setIs_fav(boolean is_fav) {
		this.is_fav = is_fav;
	}

	public ItemAdapter(Context c, List<Item> items) {
		super();
		Items = items;
		inflater = LayoutInflater.from(c);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return Items.size();
	}

	@Override
	public Item getItem(int position) {
		// TODO Auto-generated method stub
		return Items.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return Items.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (is_fav){
			Items = data.getFavItems();
		}else{
		data.updateItems(Items);
		}

		convertView = inflater.inflate(R.layout.icone_list,null);
		
		View tv = convertView.findViewById(R.id.textnom);
		((TextView) tv).setText(Items.get(position).getNom());
		
		View tv2 = convertView.findViewById(R.id.textquartiersecteur);
		String quartier = Items.get(position).getQuartier();
		String secteur = Items.get(position).getSecteur();
		((TextView) tv2).setText(quartier + secteur);
		
		ImageView image =  (ImageView) convertView.findViewById(R.id.image_small);
		image.setImageResource(R.drawable.ic_launcher);
		
		if (Items.get(position).isFavori()) {
			View tvfv = convertView.findViewById(R.id.textfavori);
			((TextView) tvfv).setText("Favori");
		}
		//View tvcat = convertView.findViewById(R.id.textcategorie);
		//((TextView) tvcat).setText(Items.get(position).getCategorie());

		return convertView;
	}

}
