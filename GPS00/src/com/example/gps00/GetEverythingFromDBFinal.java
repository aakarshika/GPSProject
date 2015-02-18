package com.example.gps00;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;

public class GetEverythingFromDBFinal {

	
	private ArrayList<HashMap<String, String>> list;

	public ArrayList<HashMap<String, String>> getList(Context con) {
		// TODO Auto-generated method stub
		MyDB e=new MyDB(con);
		e.open();
		list=e.getData("name", null);
		e.close();
		
		
		return list;
	}

}
