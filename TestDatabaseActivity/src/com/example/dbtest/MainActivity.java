package com.example.dbtest;

import java.io.IOException;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

	static float sDensity;
	static float sScaledDensity;
	static int sXDpi;
	static int sYDpi;
	static int sDpi;
	static int sWidth;
	static int sHeight;

	ChainDbHelper db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		boolean err;
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Read all screen metric attributes and store them
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		sDensity = metrics.scaledDensity;
		sScaledDensity = metrics.scaledDensity;
		sXDpi = (int) metrics.xdpi;
		sYDpi = (int) metrics.ydpi;
		sDpi = metrics.densityDpi;
		sWidth = metrics.widthPixels;
		sHeight = metrics.heightPixels;

		TextView tv = (TextView) findViewById(R.id.tvSecond);
		tv.setText(metrics.toString());

		Log.d("ScreenData", "Screen : width=" + sWidth + " height=" + sHeight
				+ " Density=" + sDensity + " ScaledDensity=" + sScaledDensity);
		Log.d("ScreenData", "XDpi=" + sXDpi + " YDpi=" + sYDpi + " DPI=" + sDpi);

		db = new ChainDbHelper(getApplicationContext());
		
		try {
			err = readAchievements();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test_database, menu);
		return true;
	}
	
	public boolean readAchievements() throws IOException
	{
		boolean err=false;
		List<String> achs;
		
		achs=Utils.readLinesFromFile(getAssets().open("achievements.csv"));
		
		
		
		
		return err;
	}
}
