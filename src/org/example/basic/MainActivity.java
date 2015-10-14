package org.example.basic;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity{
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hazard);
	}
	public void onClick(View view){
		Intent i=new Intent(this,HazardActivity.class);
		startActivity(i);
	}
	
	public void onClick2(View view){
		Intent i=new Intent(this,PostActivity.class);
		startActivity(i);
	}
}
