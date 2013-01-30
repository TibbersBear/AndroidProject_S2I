package com.example.test;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ItemAdapterFragment extends FragmentPagerAdapter {

	List<Item> Items;
	LayoutInflater inflater;

	public ItemAdapterFragment(FragmentManager fm, Context c, List<Item> Objects) {
		super(fm);
		// TODO Auto-generated constructor stub
		inflater = LayoutInflater.from(c);
		Items = Objects;
		ArrayListFragment.SetItems(Items);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return Items.size();
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return ArrayListFragment.newInstance(arg0);
	}

	public static class ArrayListFragment extends ListFragment {
		int mNum;
		static List<Item> Items;

		/**
		 * Create a new instance of CountingFragment, providing "num" as an
		 * argument.
		 */
		static ArrayListFragment newInstance(int num) {
			ArrayListFragment f = new ArrayListFragment();

			// Supply num input as an argument.
			Bundle args = new Bundle();
			args.putInt("num", num);
			f.setArguments(args);

			return f;
		}

		public static void SetItems(List<Item> i) {
			Items = i;
		}

		/**
		 * When creating, retrieve this instance's number from its arguments.
		 */
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			mNum = getArguments() != null ? getArguments().getInt("num") : 1;
		}

		/**
		 * The Fragment's UI is just a simple text view showing its instance
		 * number.
		 */
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View v = inflater.inflate(R.layout.icone_list, container, false);
			View tv = v.findViewById(R.id.textnom);
			((TextView) tv).setText(Items.get(mNum).getNom());
			View tv2 = v.findViewById(R.id.textquartiersecteur);
			String quartier = Items.get(mNum).getQuartier();
			String secteur = Items.get(mNum).getSecteur();
			((TextView) tv2).setText(quartier + secteur);
			ImageView image = (ImageView) v.findViewById(R.id.image_small);
			image.setImageResource(R.drawable.ic_launcher);
			if (Items.get(mNum).isFavori()) {
				View tvfv = v.findViewById(R.id.textfavori);
				((TextView) tvfv).setText("Favori");
			}
			//View tvcat = v.findViewById(R.id.textcategorie);
			//((TextView) tvcat).setText(Items.get(mNum).getCategorie());

			return v;
		}

		/*
		 * @Override public void onActivityCreated(Bundle savedInstanceState) {
		 * super.onActivityCreated(savedInstanceState);
		 * setListAdapter((ListAdapter) new ArrayAdapter<String>(getActivity(),
		 * android.R.layout.simple_list_item_1, Cheeses.sCheeseStrings)); }
		 */

		@Override
		public void onListItemClick(ListView l, View v, int position, long id) {
			Log.i("FragmentList", "Item clicked: " + id);
		}
	}

}
