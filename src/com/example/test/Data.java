package com.example.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class Data extends Observable {
	private static List<Item> items;
	private static SharedPreferences preference;
	private static SharedPreferences.Editor preferenceEditor;
	private static int maxdata;
	private static boolean fullyloaded=false;
	private static boolean started=false;

	public Data(){
		if(!started){
			items = new ArrayList<Item>();
			maxdata=274;
			started = true;
		}
		
	}
	
	public void InitializePreferences(SharedPreferences pref){
		preference = pref;
		preferenceEditor= preference.edit();
	}
	
	public void InitializeFavori(){
		for (Item item : items){
			item.setFavori(preference.getBoolean(Long.toString(item.getId()), false));
		}
	}
	
	public void addItem(Item item){
		if (!items.contains(item)){
			items.add(item);
			//Log.v("addItem","Item ajouté : "+this.getsize());
		}
	}
	
	public void addItems(List<Item> items){
		for (int i=0;i<items.size();i++){
			this.addItem(items.get(i));
		}
	}
	
	public static List<Item> getItems() {
		return items;
	}
	
	public static List<Item> getFavItems() {
		List<Item> FavItems = new ArrayList<Item>();
		for (int i=0;i<items.size();i++){
			if (items.get(i).isFavori()){
				FavItems.add(items.get(i));
			}
		}
		return FavItems;
	}

	public static void setItems(List<Item> items) {
		Data.items = items;
	}

	public static Item getOneItembyId(long tempid){
		for (int i=0;i<items.size();i++){
			if (items.get(i).getId()==tempid){
				return items.get(i);
			}
		}
		return null;
		
	}
	
	public static void updateData(List<Item> newitems){
		items=newitems;
	}
	public static void updateItems(List<Item> newitems){
		newitems=items;
	}
	
	public static void ChangeFavoriState (long id){
		getOneItembyId(id).setFavori(! getOneItembyId(id).isFavori() ) ;
		preferenceEditor.putBoolean(Long.toString(id),getOneItembyId(id).isFavori());
		preferenceEditor.apply();
	}

	public int getsize() {
		// TODO Auto-generated method stub
		return items.size();
	}

	public void setMax(int length) {
		// TODO Auto-generated method stub
		maxdata=length;
	}

	public int getMax() {
		// TODO Auto-generated method stub
		return maxdata;
	}
	
	public void IsLoaded (){
		Log.v("IsLoaded","Is Loaded");
		fullyloaded=true;
		this.hasChanged();
		this.notifyObservers();
		Log.v("IsLoaded","Number of Observer notified : "+this.countObservers());
	}

}
