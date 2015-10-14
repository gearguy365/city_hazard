package org.example.basic;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class JSONParser {
	
	
	public static List<Hazard> parseFeed(String content){

		try {
			JSONArray ar=new JSONArray(content);
			List<Hazard> hazardList =new ArrayList<>();

			for(int i=0; i<ar.length(); i++){
				Log.v("object_found", ""+i);
				JSONObject obj=ar.getJSONObject(i);
				Hazard hazard=new Hazard();

				hazard.setId(obj.getInt("id"));
				hazard.setTitle(obj.getString("title"));
				hazard.setCity(obj.getString("city"));
				hazard.setAddress(obj.getString("address"));
				hazard.setCategory(obj.getString("category"));
				hazard.setDesc(obj.getString("desc"));
				hazard.setLikes(obj.getInt("likes"));
				hazard.setImagename(obj.getString("img1"));

				/*
				String imageurl=IMG_BASE_URL+obj.getString("img1");
				InputStream in;
				try {
					in = (InputStream) new URL(imageurl).getContent();
					Bitmap bitmap=BitmapFactory.decodeStream(in);
					hazard.setBitmap(bitmap);
					in.close();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				*/
				

				hazardList.add(hazard);
				//Log.v("object_found", hazard.getTitle());
			}

			return hazardList;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
}
