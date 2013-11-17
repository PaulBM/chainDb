package com.example.dbtest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PlayerActivity extends Activity {

	ChainDbHelper db;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
//		TextView tv;
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player);

		/*
		tv = (TextView) findViewById(R.id.tvPlayersTitle);
		tv.setText("Players");
		*/
		db = new ChainDbHelper(getApplicationContext());
		
		try {
			readPlayers((LinearLayout) findViewById(R.id.layoutPlayers));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.player, menu);
		return true;
	}

	
	/**
	 * Read Player details into given layout
	 * @param layout
	 * @return 
	 * @return error flag
	 * @throws IOException
	 */
	public void readPlayers(LinearLayout layout) throws IOException {
		List<DbPlayer> objPlayers = new ArrayList<DbPlayer>();
		int playersCount = 0;
		TextView tv = new TextView(this);
		long id = 0;

		objPlayers=db.getAllPlayers();
		
		Log.d("Info", "Read Players... building textview strings.");

		if (objPlayers.size() > 0) {
			for (int i=0; i < objPlayers.size(); i++) {
				tv = new TextView(this);
				id = objPlayers.get(i).getId();
				tv.setText(id + " " + objPlayers.get(i).getName());
				tv.setId((int) id);
				tv.setLayoutParams(new LayoutParams(
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));
				Log.d("Info", "Ready to add new Player TextView");
				layout.addView(tv);

				Log.d("Info", "TextView added " + id);

				// keep a count of list size
				playersCount++;
			}
			Log.d("Info", "Found " + playersCount + " achievements");
		} else {
			tv = new TextView(this);
			tv.setText("No Players in database.");
			tv.setLayoutParams(new LayoutParams(
						LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT));
			layout.addView(tv);
			Log.d("Info", "Default textview added. (Players 0)");
		}

	}
}
