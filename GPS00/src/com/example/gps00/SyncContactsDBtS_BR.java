package com.example.gps00;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class SyncContactsDBtS_BR extends BroadcastReceiver
{

	JSONParser jparser;
	private String url_syncContacts;
	private String TAG_CONTACTS_ARRAY;
	private String TAG_NUMBER;
	private String TAG_BOOL;
	private ArrayList<HashMap<String, String>> main_list;
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_PRODUCTS = "products";
	private static final String TAG_PID = "pid";
	private static final String TAG_NAME = "name";
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		///make http request

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		JSONObject json = jparser.makeHttpRequest(url_syncContacts, "GET", params);

		try {
			// Checking for SUCCESS TAG
			int success = json.getInt(TAG_SUCCESS);
			if (success == 1) {
				// products found
				// Getting Array of Products
				JSONArray list_contacts = json.getJSONArray(TAG_CONTACTS_ARRAY);
				// looping through All Products
				for (int i = 0; i < list_contacts.length(); i++) {
					JSONObject c = list_contacts.getJSONObject(i);
					// Storing each json item in variable
					String number = c.getString(TAG_NUMBER);
					String yn = c.getString(TAG_BOOL);
					// creating new HashMap
					if(yn=="yes")
					{
						HashMap<String, String> map = new HashMap<String, String>();
						map.put(TAG_NUMBER, number);
						map.put(TAG_BOOL,yn);
						// adding HashList to ArrayList
						main_list.add(map);	
						map=new HashMap<String, String>();
					}
					else if(yn=="no")
					{
						Log.e("noooooootttt presenttttt","");
					}
				}
			} else {
				Log.e("no contactttttsssssssssssssss","");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}


	}

}
