package com.example.gps00;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSONParser {

	static InputStream is = null;
	static JSONObject jObj = null;
	static String json = "";

	// constructor
	public JSONParser() {

	}

	// function get json from url
	// by making HTTP POST or GET mehtod
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JSONObject makeHttpRequest(String url, String method,
			ArrayList<HashMap<String, String>> params) {

		// Making HTTP request
		try {

			// check for request method
			if(method == "POST"){
				// request method is POST
				/* defaultHttpClient
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(params));

                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent(); */

				//instantiates httpclient to make request
				DefaultHttpClient httpclient = new DefaultHttpClient();

				//url with the post data
				HttpPost httpost = new HttpPost(url);

				//convert parameters into JSON object
				JSONObject holder = getJsonObjectFromMap(params);

				//passes the results to a string builder/entity
				StringEntity se = new StringEntity(holder.toString());
				//sets the post request as the resulting string
				httpost.setEntity(se);
				//sets a request header so the page receving the request
				//will know what to do with it
				httpost.setHeader("Accept", "application/json");
				httpost.setHeader("Content-type", "application/json");
				//Handles what is returned from the page 
				ResponseHandler responseHandler = new BasicResponseHandler();
				HttpResponse httpResponse =httpclient.execute(httpost, responseHandler);
				HttpEntity httpEntity = httpResponse.getEntity();
				is = httpEntity.getContent();

			}else if(method == "GET"){
				// request method is GET
				DefaultHttpClient httpClient = new DefaultHttpClient();
				String paramString = URLEncodedUtils.format((List<? extends NameValuePair>) params, "utf-8");
				url += "?" + paramString;
				HttpGet httpGet = new HttpGet(url);

				HttpResponse httpResponse = httpClient.execute(httpGet);
				HttpEntity httpEntity = httpResponse.getEntity();
				is = httpEntity.getContent();
			}           

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}

		// try parse the string to a JSON object
		try {
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());

			Log.d("",""+jObj.toString());
		}

		// return JSON String
		return jObj;

	}

	private JSONObject getJsonObjectFromMap(ArrayList<HashMap<String, String>> params) {
		// TODO Auto-generated method stub
		Iterator<HashMap<String, String>> iter = params.iterator();
		//Stores JSON
		JSONObject holder = new JSONObject();
		JSONObject entryinarray = new JSONObject();
		JSONArray array = new JSONArray();
		HashMap<String, String> map= new HashMap<String, String>();
		try {
			while (iter.hasNext())
			{
				//gets an entry in the params
				map = (HashMap<String, String>) iter.next();
				String number = map.get("number");
				int index=params.indexOf(map);
				entryinarray.put("number", number);
				entryinarray.put("index", index);
				array.put(entryinarray);
			}
			holder.put("arraynumberindex", array);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return holder;
	}

	public JSONObject makeHttpRequestget(String url, String method,
			List<NameValuePair> params) {
		// TODO Auto
		try{
			if(method == "GET"){
				// request method is GET
				DefaultHttpClient httpClient = new DefaultHttpClient();
				String paramString = URLEncodedUtils.format((List<? extends NameValuePair>) params, "utf-8");
				url += "?" + paramString;
				HttpGet httpGet = new HttpGet(url);

				HttpResponse httpResponse = httpClient.execute(httpGet);
				HttpEntity httpEntity = httpResponse.getEntity();
				is = httpEntity.getContent();
			}           

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}

		// try parse the string to a JSON object
		try {
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());

			Log.d("",""+jObj.toString());
		}

		// return JSON String
		return jObj;
	}
}