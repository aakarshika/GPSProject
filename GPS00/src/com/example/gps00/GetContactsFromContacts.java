
package com.example.gps00;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;
//import android.util.Log;
import android.widget.TextView;

public class GetContactsFromContacts {

	String phonedata = " ";
	static int k=0;

	Context con;
	//private ArrayList<HashMap<String, String>> productsList;
	public GetContactsFromContacts(Context con)
	{
		this.con=con;
		//	productsList=new ArrayList<HashMap<String,String>>();
	}

	public  ArrayList<HashMap<String, String>> getData()
	{ 
		getDatafromContacts2();

		ArrayList<HashMap<String, String>> finalList = null;
		try{
			MyDB entry = new MyDB(con);
			entry.open();
			finalList=entry.getData("name",null);
			Log.d("getdata","3");

			entry.close();

		}catch (Exception e){
			String error = e.toString();
			Dialog d = new Dialog(con);
			d.setTitle("dang it");
			TextView tv = new TextView(con);
			tv.setText(error);
			d.setContentView(tv);
			d.show();
		}
		return finalList;
	}


	@SuppressLint("InlinedApi")
	private String getDatafromContacts2() {
		// TODO Auto-generated method stub
		//String phonedata = "";

		try {
			/**************************************************/
			ContentResolver cr = con.getContentResolver();
			Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);

			if (cur.getCount() > 0) {

				Log.i("Content provider", "Reading contact  phones");
				while (cur.moveToNext()) {

					String contactId = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
					// Create query to use CommonDataKinds classes to fetch phone's
					Cursor phone = cr.query(
							ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID
							+ " = " + contactId, null, null);
					

					while (phone.moveToNext()) {

						// This would allow you get several phone addresses
						//String phoneAddress = phone.getString(phone
							//	.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER));
						String p1 = phone.getString(phone
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER));
						String p2 = phone.getString(phone
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

						Log.e("phone==>", p1+p2);

						phonedata +=" " +"	"+p1+" "+p2+"-------";
						MyDB entry=new MyDB(con);
						entry.open();
						entry.createEntry(p1, p2, "lala"+k++);
						entry.close();
					}

					phone.close();
				}

			}
			else
			{
				phonedata +="Data not found.";

			}
			cur.close();


		} catch (Exception e) {

			phonedata +="Exception : "+e+"	";
		}
		Log.w("yoyoyoyo",phonedata);
		return phonedata;
	}
	
	

	@SuppressLint("InlinedApi")
	public ArrayList<HashMap<String, String>> getDatafromContacts3() {
		// TODO Auto-generated method stub
		//String phonedata = "";

		ArrayList<HashMap<String, String>> main_list = new ArrayList<HashMap<String,String>>();
		try {
			/**************************************************/
			ContentResolver cr = con.getContentResolver();
			Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);

			if (cur.getCount() > 0) {

				Log.i("Content provider", "Reading contact  phones");
				HashMap<String, String> map=new HashMap<String, String>();
				while (cur.moveToNext()) {

					String contactId = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
					// Create query to use CommonDataKinds classes to fetch phone's
					Cursor phone = cr.query(
							ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID
							+ " = " + contactId, null, null);
					

					while (phone.moveToNext()) {

						// This would allow you get several phone addresses
						//String phoneAddress = phone.getString(phone
							//	.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER));
						String p1 = phone.getString(phone
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER));
						String p2 = phone.getString(phone
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

						Log.e("phone==>", p1+p2);


						map=new HashMap<String, String>();
						phonedata +=" " +"	"+p1+" "+p2+"-------";
						map.put("number", p1);
						map.put("name",p2);
						// adding HashList to ArrayList
						main_list.add(map);	
					}

					phone.close();
				}

			}
			else
			{
				phonedata +="Data not found.";

			}
			cur.close();


		} catch (Exception e) {

			phonedata +="Exception : "+e+"	";
		}
		Log.w("yoyoyoyo",phonedata);
		return main_list;
	}
}
