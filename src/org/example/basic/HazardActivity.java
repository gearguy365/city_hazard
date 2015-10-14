package org.example.basic;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class HazardActivity extends Activity {

	//TextView output;
	ProgressBar pb;
	List<MyTask> tasks;


	public static List<Hazard> hazardList;
	public static String IMG_BASE_URL="http://apphost.byethost7.com/images/";

	public static ListView l;
	public static ArrayAdapter<Hazard> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//*********************************************
		new AlertDialog.Builder(this)
		.setTitle("Notice")
		.setMessage("The app is still under development, so all features may not be available of use.")
		.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) { 
				// continue with tasks
				pb=(ProgressBar)findViewById(R.id.progressBar1);
				pb.setVisibility(View.INVISIBLE);

				tasks= new ArrayList<>();
			}
		})
		/*
		.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) { 
				// do nothing
			}
		})
		*/
		.setIcon(android.R.drawable.ic_dialog_alert)
		.show();
		//*********************************************
		
		//pb=(ProgressBar)findViewById(R.id.progressBar1);
		//pb.setVisibility(View.INVISIBLE);

		//tasks= new ArrayList<>();
	}

	public void callDetail(String title,String address,String city,String desc,int likes,int position){
		Intent i=new Intent(this,DetailActivity.class);
		i.putExtra("title", title);
		i.putExtra("address", address);
		i.putExtra("city", city);
		i.putExtra("desc", desc);
		i.putExtra("likes", likes);
		i.putExtra("position", position);
		startActivity(i);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	protected void updateDisplay(){
		//output.setText(message);

		if(hazardList != null){
			l=(ListView)findViewById(R.id.listview);
			adapter=new ArrayAdapter<Hazard>(this, android.R.layout.simple_list_item_1, hazardList);
			//setListAdapter(adapter);
			l.setAdapter(adapter);
			l.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> adapter, View view, int position, long arg3) {
					// TODO Auto-generated method stub
					//String selection = adapter.getItemAtPosition(position).toString();
					String title=hazardList.get(position).getTitle();
					String address=hazardList.get(position).getAddress();
					String city=hazardList.get(position).getCity();
					String desc=hazardList.get(position).getDesc();
					int likes=hazardList.get(position).getLikes();
					int pos=findPos(title);
					//Toast.makeText(this, position, Toast.LENGTH_SHORT).show();

					callDetail(title,address,city,desc,likes,pos);
				}
			});
		}

	}

	public int findPos(String title){
		int pos=1;
		for (int i=0;i<hazardList.size();i++) {
			Hazard h=hazardList.get(i);
			if(h.getTitle().equals(title)){
				pos=i;
			}
		}
		return pos;
	}

	public void onClick(View view){
		if(isOnline()){
			requestData("http://apphost.byethost7.com/");
		}
		else{
			Toast.makeText(this, "Network Error", Toast.LENGTH_SHORT).show();
		}
	}


	private void requestData(String uri) {
		MyTask task01=new MyTask();
		task01.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, uri);
	}

	protected boolean isOnline(){
		ConnectivityManager cm= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netinfo=cm.getActiveNetworkInfo();
		if(netinfo!=null && netinfo.isConnectedOrConnecting()){
			return true;
		}
		else {
			return false;
		}
	}


	private class MyTask extends AsyncTask<String , String , String >{

		@Override
		protected void onPreExecute() {
			//updateDisplay("Starting Task");
			if(tasks.size()==0){
				pb.setVisibility(View.VISIBLE);
			}
			tasks.add(this);
		}

		@Override
		protected String doInBackground(String... params) {

			String content = HttpManager.getData(params[0]);
			//return "Task Complete";
			hazardList=JSONParser.parseFeed(content);

			for (Hazard h : hazardList) {
				String imageurl=IMG_BASE_URL+h.getImagename();
				InputStream in;
				try {
					in = (InputStream) new URL(imageurl).getContent();
					Bitmap bitmap=BitmapFactory.decodeStream(in);
					h.setBitmap(bitmap);
					in.close();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			return content;
		}

		@Override
		protected void onPostExecute(String result) {
			updateDisplay();
			tasks.remove(this);
			if(tasks.size()==0){
				pb.setVisibility(View.INVISIBLE);
			}
		}
		@Override
		protected void onProgressUpdate(String... values) {
			//updateDisplay(values[0]);
		}

	}
}
