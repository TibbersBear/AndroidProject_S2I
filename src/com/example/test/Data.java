package com.example.test;

import java.util.ArrayList;
import java.util.List;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Data {
	private static List<Item> items;
	private static SharedPreferences preference;
	private static SharedPreferences.Editor preferenceEditor;

	public Data(){
		
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
		items.get((int) id).setFavori(! items.get((int) id).isFavori() ) ;
	}

}
