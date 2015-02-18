package com.example.gps00;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity {

	private ArrayList<HashMap<String, String>> productsList;
	private ListView main_list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("main","oncreate");
		setContentView(R.layout.activity_main);
		main_list=(ListView) findViewById(R.id.main_list);
		
		GetContactsFromContacts g =new GetContactsFromContacts(getBaseContext());
		
		productsList =g.getData();
		Log.d("main","get data done");
		Log.d("main list",""+productsList.toString());
		setupList(productsList);
		Log.d("main","setuplist done");
	}

	private void setupList(final ArrayList<HashMap<String, String>> productsList2) {
		// TODO Auto-generated method stub
		Log.d("main","setup");
		runOnUiThread(new Runnable() {
			public void run() {
				/**
				 * Updating parsed JSON data into ListView
				 * */
				ListAdapter adapter = new SimpleAdapter(
						MainActivity.this, productsList2,
						R.layout.list_item, new String[] { "name",
								"loc","num"},
						new int[] { R.id.name_inlist, R.id.location_inlist, R.id.num_inlist });
				// updating listview
				main_list.setAdapter(adapter);
				Log.d("main","setup 2");

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
