package com.example.dbtest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static final int ACH_FIELDS = 5;

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
		String versionName = null;

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// myIntent.putExtra("key", value); //Optional parameters

		// set up Games button
		final Button launchGame = (Button) findViewById(R.id.gotoGame);

		launchGame.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				// set up next screen Intent
				Intent gameIntent = new Intent(MainActivity.this,
						GameActivity.class);
				// start the Activity
				MainActivity.this.startActivity(gameIntent);
			}
		});

		// set up Players button
		final Button launchPlayer = (Button) findViewById(R.id.gotoPlayer);

		launchPlayer.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				// set up next screen Intent
				Intent playerIntent = new Intent(MainActivity.this,PlayerActivity.class);
				// start the Activity
				MainActivity.this.startActivity(playerIntent);
			}
		});

		// Read all screen metric attributes and store them
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		sDensity = metrics.scaledDensity;
		sScaledDensity = metrics.scaledDensity;
		sXDpi = (int) metrics.xdpi;
		sYDpi = (int) metrics.ydpi;
		sDpi = metrics.densityDpi;
		sWidth = metrics.widthPixels;
		sHeight = metrics.heightPixels;

		// tv = (TextView) findViewById(R.id.tvSecond);
		// tv.setText(metrics.toString());

		Log.d("ScreenData", "Screen : width=" + sWidth + " height=" + sHeight
				+ " Density=" + sDensity + " ScaledDensity=" + sScaledDensity);
		Log.d("ScreenData", "XDpi=" + sXDpi + " YDpi=" + sYDpi + " DPI=" + sDpi);

		db = new ChainDbHelper(getApplicationContext());

		tv = (TextView) findViewById(R.id.tvDbName);
		tv.setText(db.getDatabaseName());

		try {
			versionName = getPackageManager().getPackageInfo(getPackageName(),0).versionName;
		} catch (NameNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		tv = (TextView) findViewById(R.id.tvVersionName);
		tv.setText(versionName);

		try {
			err = readAchievements((LinearLayout) findViewById(R.id.layoutAchs));

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

	/**
	 * Read achievement details into given layout
	 * @param layout
	 * @return error flag
	 * @throws IOException
	 */
	public boolean readAchievements(LinearLayout layout) throws IOException {
		boolean err = false;
		String temp = null;
		List<String> achs;
		String[] ach;
		List<DbAchievement> objAchs = new ArrayList<DbAchievement>();
		int achsCount = 0;
		TextView tv = new TextView(this);
		long id = 0;

		achs = Utils.readLinesFromFile(getAssets().open("achievements.csv"));

		if (achs.size() > 0) {
			// split the achievement strings, into their parts for db
			for (int i = 0; i < achs.size(); i++) {
				// split the string from file into it's 5 parts
				ach = achs.get(i).split(",");
				if (ach.length == ACH_FIELDS) {

					/*
					 * log the split (used once to test) for (int a = 0; a
					 * <ach.length; a++) { Log.d("Info", a + ": " + ach[a]); }
					 */

					id = Long.parseLong(ach[0]);
					objAchs.add(new DbAchievement(id, ach[1], ach[2], Integer.parseInt(ach[3]), Integer.parseInt(ach[4])));
					Log.d("Info", objAchs.get(achsCount).toString());

					// check to see if the achievement exists in the local db
					db.storeAchievement(objAchs.get(achsCount));

					Log.d("Info", "Achievement stored... building textview string.");

					tv = new TextView(this);
					temp = id + " '" + objAchs.get(achsCount).getName() + "'";
					switch (objAchs.get(achsCount).getAchDiff()) {
					case 0:
						temp += " General";
						break;
					case 1:
						temp += " Easy";
						break;
					case 2:
						temp += " Medium";
						break;
					case 3:
						temp += " Hard";
						break;
					default:
						temp += " unknown!";
					}
					switch (objAchs.get(achsCount).getAchHidden()) {
					case 0:
						temp += " V";
						break;
					case 1:
						temp += " H";
						break;
					default:
						temp += " unknown!";
					}
					tv.setText(temp);
					tv.setId((int) id);
					tv.setLayoutParams(new LayoutParams(
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));
					Log.d("Info", "Ready to add new TextView");
					layout.addView(tv);

					Log.d("Info", "TextView added " + id);

					// keep a count of list size
					achsCount++;
				} else {
					Log.d("Error",
							"Achievement string didn't split to 5 parts!");
				}
				Log.d("Info", "Found " + achsCount + " achievements");

			}
		} else {
			err = true;
		}

		return err;
	}
}
