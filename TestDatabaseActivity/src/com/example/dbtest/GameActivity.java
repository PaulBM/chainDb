package com.example.dbtest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GameActivity extends Activity {

	ChainDbHelper db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		
		db = new ChainDbHelper(getApplicationContext());
		
		try {
			readGames((LinearLayout) findViewById(R.id.layoutGames));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// set up Add Game button
		final Button launchNewGame = (Button) findViewById(R.id.addGame);

		launchNewGame.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				// set up next screen Intent
				Intent gameIntent = new Intent(GameActivity.this, NewGameActivity.class);
				// start the Activity
				GameActivity.this.startActivity(gameIntent);
			}
		});		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_game, menu);
		return true;
	}

	/**
	 * Read Game details into given layout
	 * @param layout
	 * @return 
	 * @throws IOException
	 */
	public void readGames(LinearLayout layout) throws IOException {
		List<DbGame> objGames = new ArrayList<DbGame>();
		int gamesCount = 0;
		TextView tv = new TextView(this);
		long id = 0;

		objGames=db.getAllGames();
		
		Log.d("Info", "Read Games... building textview strings.");

		if (objGames.size() > 0) {
			for (int i=0; i < objGames.size(); i++) {
				tv = new TextView(this);
				id = objGames.get(i).getId();
				tv.setText(id + " " + objGames.get(i).getStart() + 
						" " + objGames.get(i).getScore() + " " + objGames.get(i).getLevel());
				tv.setId((int) id);
				tv.setLayoutParams(new LayoutParams(
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));
				Log.d("Info", "Ready to add new Game TextView");
				layout.addView(tv);

				Log.d("Info", "TextView added " + id);

				// keep a count of list size
				gamesCount++;
			}
			Log.d("Info", "Found " + gamesCount + " games");
		} else {
			tv = new TextView(this);
			tv.setText("No Games in database.");
			tv.setLayoutParams(new LayoutParams(
						LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT));
			layout.addView(tv);
			Log.d("Info", "Default textview added. (Games 0)");
		}

	}

}
