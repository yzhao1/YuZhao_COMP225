package com.example.myproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener{
	
	private static final String TAG = "TechNews";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        View engadgetButton = findViewById(R.id.Engadget);
        engadgetButton.setOnClickListener(this);
        View allthingsdButton = findViewById(R.id.AllThingsD);
        allthingsdButton.setOnClickListener(this);
        View cnbetaButton = findViewById(R.id.cnBeta);
        cnbetaButton.setOnClickListener(this);
        View aboutButton = findViewById(R.id.about_button);
        aboutButton.setOnClickListener(this);
        View exitButton = findViewById(R.id.exit_button);
        exitButton.setOnClickListener(this);
        
    }
    
    @Override
    protected void onResume() {
       super.onResume();
       Music.play(this, R.raw.main);
    }

    @Override
    protected void onPause() {
       super.onPause();
       Music.stop(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.settings:
			startActivity(new Intent(this, prefs.class));
			return true;
		case R.id.choosefeed:
			Log.i(TAG, "Set RSS Feed");
			return true;
		case R.id.refresh:
			Log.i(TAG, "Refreshing RSS Feed");
			return true;
		}
		return false;
	}
    
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.Engadget:
			startRead(0);
			break;
		case R.id.AllThingsD:
			startRead(1);
			break;
		case R.id.cnBeta:
			startRead(2);
			break;
		case R.id.about_button:
			Intent i = new Intent(this, about.class);
			startActivity(i);
			break;
		case R.id.exit_button:
			finish();
			break;
		}
	}
	
	private void startRead(int id) {
		Intent intent = new Intent(this, RSSReader.class);
		intent.putExtra(RSSReader.Source, id);
		startActivity(intent);
	}
	
}
