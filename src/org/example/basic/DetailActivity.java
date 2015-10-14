package org.example.basic;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		
		
		Bundle extras=getIntent().getExtras();
		String title=extras.getString("title");
		String address=extras.getString("address");
		String city=extras.getString("city");
		String desc=extras.getString("desc");
	    int likes=extras.getInt("likes");
	    int position=extras.getInt("pos");
	    
	    //Toast.makeText(this, ""+position, Toast.LENGTH_SHORT).show();
	    
	    Hazard hazard=null;
	    for (Hazard h : HazardActivity.hazardList) {
			if(h.getTitle().equals(title)){
				hazard=h;
			}
		}
	    
	    Bitmap bitmap=hazard.getBitmap();
	    
	    
	    TextView titleview=(TextView)findViewById(R.id.textView1);
	    TextView addressview=(TextView)findViewById(R.id.textView2);
	    TextView cityview=(TextView)findViewById(R.id.textView3);
	    TextView descview=(TextView)findViewById(R.id.textView4);
	    TextView likeview=(TextView)findViewById(R.id.textView5);
	    ImageView image=(ImageView)findViewById(R.id.imageView1);
	    
	    titleview.setText(title);
	    addressview.setText(address);
	    cityview.setText(city);
	    descview.setText(desc);
	    likeview.setText(""+likes);
	    image.setImageBitmap(bitmap);
	    
	}
	
	public void onClick(View view){
		Toast.makeText(this, "Like feature not yet available", Toast.LENGTH_LONG).show();
	}
}
