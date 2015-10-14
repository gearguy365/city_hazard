package org.example.basic;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class PostActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post);
	}
	public void onClick(View view){
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://apphost.byethost7.com/formsubmit.php"));
		startActivity(browserIntent);
	}
}
