package com.example.dbtest;

import java.io.IOException;
import java.util.ArrayList;
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
		TextView tv;

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

		tv = (TextView) findViewById(R.id.tvSecond);
		tv.setText(metrics.toString());

		Log.d("ScreenData", "Screen : width=" + sWidth + " height=" + sHeight
				+ " Density=" + sDensity + " ScaledDensity=" + sScaledDensity);
		Log.d("ScreenData", "XDpi=" + sXDpi + " YDpi=" + sYDpi + " DPI=" + sDpi);

		db = new ChainDbHelper(getApplicationContext());

		tv = (TextView) findViewById(R.id.tvDbName);
		tv.setText(db.getDatabaseName());

		try {
			err = readAchievements();
			if (err) {
				Log.d("Error!", "Error reading achievements csv file.");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		db.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test_database, menu);
		return true;
	}

	public boolean readAchievements() throws IOException {
		boolean err = false;
		List<String> achs;
		String[] ach;
		List<DbAchievement> objAchs = new ArrayList<DbAchievement>();
		int achsCount = 0;

		achs = Utils.readLinesFromFile(getAssets().open("achievements.csv"));

		if (achs.size() > 0) {
			// split the achievement strings, into their parts for db
			for (int i = 0; i < achs.size(); i++) {
				// split the string from file into it's 4 parts
				ach = achs.get(i).split(",");
				if (ach.length == 4) {

					/*
					 * log the split (used once to test)
					 * for (int a = 0; a <ach.length; a++) {
					 * 		Log.d("Info", a + ": " + ach[a]);
					 * }
					 */

					objAchs.add(new DbAchievement(Long.parseLong(ach[0]),ach[1], ach[2], Integer.parseInt(ach[3])));
					Log.d("Info", objAchs.get(achsCount).toString());
					
					//check to see if the achievement exists in the local db
					db.storeAchievement(objAchs.get(achsCount));
					
					//keep a count of list size
					achsCount++;
				} else {
					Log.d("Error","Achievement string didn't split to 4 parts!");
				}
				Log.d("Info", "Found " + achsCount + " achievements");
				
			}
		} else {
			err = true;
		}

		return err;
	}
}
