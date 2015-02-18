package com.example.gps00;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SyncContactsDBtS_BR extends BroadcastReceiver
{

	JSONParser jparser;
	private String url_syncContacts="something.synccontacts.php";
	private final String  TAG_CONTACTS_ARRAY="arraynumberpresent";
	private final String  TAG_NUMBER="number";
	private final String  TAG_BOOL="present";
	private final String  TAG_INDEX="index";
	private final String TAG_SUCCESS = "success";
	private ArrayList<HashMap<String, String>> main_list;
	private final String TAG_NAME="name";
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		///make http request


		MyDB e=new MyDB(context);
		e.open();
		e.deleteAll();
		e.close();
		ArrayList<HashMap<String,String>> nameandnumberlist = new ArrayList<HashMap<String,String>>();
		GetContactsFromContacts g=new GetContactsFromContacts(context);
		nameandnumberlist=g.getDatafromContacts3();
	//	String consarray = null;
	//	List<NameValuePair> params = new ArrayList<NameValuePair>();
	//	params.add(new BasicNameValuePair(TAG_CONTACTS_ARRAY, consarray));
		JSONObject json = jparser.makeHttpRequest(url_syncContacts, "POST", nameandnumberlist);

		try {
			// Checking for SUCCESS TAG
			int success = json.getInt(TAG_SUCCESS);
			if (success == 1) {
				// products found
				// Getting Array of Products
				JSONArray list_contacts = json.getJSONArray(TAG_CONTACTS_ARRAY);
				// looping through All Products
				HashMap<String, String> map = new HashMap<String, String>();
				HashMap<String, String> map1 = new HashMap<String, String>();
				for (int i = 0; i < list_contacts.length(); i++) {
					JSONObject c = list_contacts.getJSONObject(i);
					// Storing each json item in variable
					String number = c.getString(TAG_NUMBER);
					int index = c.getInt(TAG_INDEX);					
					String yn = c.getString(TAG_BOOL);
					// creating new HashMap
					if(yn=="yes")
					{
						// mydb entry sequence for number only.
						//might even wanna delete the previous database ;P
						//also,take number from here, and name from the contacts and then setup the list.
						//forget it. ill do it.
						//done.
						map1 = nameandnumberlist.get(index);
						String name=map1.get("name");
						
						map.put(TAG_NAME, name);
						map.put(TAG_NUMBER, number);
						map.put(TAG_BOOL,yn);
						main_list.add(map);	
						map=new HashMap<String, String>();
						
						
						
						e.open();
						e.createEntry(number, name, "5555");
						e.close();
					}
					else if(yn=="no")
					{
						Log.e("noooooootttt presenttttt",number);
					}
				}
			} else {
				Log.e("no contactttttsssssssssssssss","");
			}
		} catch (JSONException ex) {
			ex.printStackTrace();
		}


		Log.d("main list in br", main_list.toString()+"");
	}

}
