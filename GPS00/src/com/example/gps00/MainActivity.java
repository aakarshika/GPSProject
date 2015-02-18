package com.example.gps00;

import java.util.ArrayList;
import java.util.HashMap;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
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

		Intent intent=new Intent(this,SyncLocationFromDBtS_BR.class);
		PendingIntent pi=PendingIntent.getBroadcast(this.getApplicationContext(),0,intent,0);
		AlarmManager am=(AlarmManager)getSystemService(ALARM_SERVICE);
		am.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,1000, AlarmManager.INTERVAL_HALF_HOUR, pi);
		
		Intent intent2=new Intent(this,SendMyLoc_BR.class);
		PendingIntent pi2=PendingIntent.getBroadcast(this.getApplicationContext(),0,intent,0);
		AlarmManager am2=(AlarmManager)getSystemService(ALARM_SERVICE);
		am.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,1000, AlarmManager.INTERVAL_FIFTEEN_MINUTES, pi);
		
		
		new LoadContacts().execute();
		
		
	}

	
	
	class LoadContacts extends AsyncTask<String, String, String>
	
	{

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			//TEMP
			GetContactsFromContacts g =new GetContactsFromContacts(getBaseContext());
			productsList =g.getData();
			
			//ACTUAL
			
			runOnUiThread(new Runnable() {
				public void run() {
					GetEverythingFromDBFinal g2=new GetEverythingFromDBFinal();
					productsList=g2.getList(getApplicationContext());
				}});
			Log.d("main","get data done");
			Log.d("main list",""+productsList.toString());
			Log.d("main","setuplist done");
			
			return null;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			runOnUiThread(new Runnable() {
				public void run() {
					/**
					 * Updating parsed JSON data into ListView
					 * */
					ListAdapter adapter = new SimpleAdapter(
							MainActivity.this, productsList,
							R.layout.list_item, new String[] { "name",
									"loc","num"},
							new int[] { R.id.name_inlist, R.id.location_inlist, R.id.num_inlist });
					// updating listview
					main_list.setAdapter(adapter);
					Log.d("main","setup 2");

				}
			});
		}
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
