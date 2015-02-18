package com.example.gps00;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDB {

	public static final String DATABASE_NAME = "yayy";
	public static final String DATABASE_TABLE = "gpscontacts2";
	public static final int DATABASE_VERSION = 1;
	
	private DbHelper help;
	private final Context c;
	private SQLiteDatabase db;
	
	private static class DbHelper extends SQLiteOpenHelper{

		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			Log.d("db","1");


		}

		@Override
		public void onCreate(SQLiteDatabase data) {

Log.d("db","2.0");
			
			data.execSQL("CREATE TABLE " + DATABASE_TABLE + "(num INTEGER PRIMARY KEY , name TEXT , loc TEXT );");

Log.d("db","2");

		}

		@Override
		public void onUpgrade(SQLiteDatabase data, int oldversion, int newversion) {
			Log.w(DbHelper.class.getName(),
			        "Upgrading database from version " + oldversion + " to "
			            + newversion + ", which will destroy all old data");
			data.execSQL("DROP TABLE IF EXISTS" + DATABASE_TABLE);
			onCreate(data);
		}
	}
	
	public MyDB(Context con){
		c=con;
		Log.d("","1");


	}

	public MyDB open() throws SQLException{
		help = new DbHelper(c);
		db = help.getWritableDatabase();
		return this;
	}
	
	public void close(){
		help.close();
	}

	public void createEntry(String num, String name, String loc) {
		ContentValues cv = new ContentValues();
		cv.put("num", num);
		cv.put("name", name);
		cv.put("loc", loc);

	    db.insert(DATABASE_TABLE, null, cv);
	    Log.d("db create entry","inserted");
	    
	}

	public ArrayList<HashMap<String, String>> getData(String orderby, String groupby)  throws SQLException{
		String[] columns = new String[]{"num","name","loc"};
		Cursor c = db.query(DATABASE_TABLE, columns, null, null, groupby, null, orderby);
		HashMap<String, String> map=new HashMap<String, String>();
		ArrayList<HashMap<String, String>> result=new ArrayList<HashMap<String, String>>();
		int inum = c.getColumnIndex("num");
		int iname = c.getColumnIndex("name");
		int iloc = c.getColumnIndex("loc");

Log.d("db get data","4");

		for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
		{
			map.put("num", c.getString(inum)+"");
			map.put("name", c.getString(iname)+"");
			map.put("loc", c.getString(iloc)+"");

		//	Log.d("db","4.2");	
			result.add(map);
			map=new HashMap<String, String>();
		//	Log.d("db","4.3");
		}

		String s=result.toString();
		
Log.d("db get data",s);

		return result;
	}
/*
	public String getlikes(String id)  throws SQLException {
		Log.d("ID=",id);
		String[] columns = new String[]{KEY_LIKES,KEY_IMG};
		Cursor c = db.query(DATABASE_TABLE, columns, KEY_POST_FTEXT + " = \"A\"", null, null, null, null);
		if(c != null){
			c.moveToFirst();
			int il = c.getColumnIndex(KEY_LIKES);
			return c.getString(il)+"";
		}
		return null;
	}public String getdislikes(String id)  throws SQLException{
		String[] columns = new String[]{KEY_UNLIKES};
		Cursor c = db.query(DATABASE_TABLE, columns, KEY_ROWID + "-" + id, null, null, null, null);
		if(c != null){
			c.moveToFirst();
			int il = c.getColumnIndex(KEY_UNLIKES);
			return c.getString(il);
		}
		return null;
	} 


	public String getDislikes(String pid)  throws SQLException{
		String[] columns = new String[]{KEY_DISLIKES};
		Cursor c = db.query(DATABASE_TABLE, columns, KEY_ROWID + "-\"" + pid + "\"", null, null, null, null);
		if(c != null){
			c.moveToFirst();
			String lk = c.getString(0);
			Log.d("getting dislikes from db.",lk);
			return lk;
		}
			return null;
	}

	public void clickedld(String pid,String keyld)  throws SQLException{
	    ContentValues cv1 = new ContentValues();
	    String ld;
	    if(keyld.equals("likes"))
	    	 ld=getlikes(pid);
	    else
	    	 ld=getdislikes(pid);
	    		
	    int i=Integer.parseInt(ld);
	    i++;
	    cv1.put(KEY_LIKES, i+"");
	    db.update(DATABASE_TABLE, cv1, KEY_ROWID + "-" + pid, null);

	    Log.d("update db with like",i+"");
	}
*/
	public void updateEntry(String num,String key,String data) throws SQLException
	{
		ContentValues cv = new ContentValues();
		cv.put(key, data);
		db.update(DATABASE_TABLE, cv, "num"+" = "+num , null);
	}
	public void deleteEntry(long lrow1) throws SQLException{
		db.delete(DATABASE_TABLE, "num" + "-" + lrow1, null);
	}

}