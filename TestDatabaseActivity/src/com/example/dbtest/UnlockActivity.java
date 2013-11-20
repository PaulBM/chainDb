package com.example.dbtest;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class UnlockActivity extends Activity {

	ChainDbHelper db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		DbGame game = new DbGame();
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_unlock);
		
		db = new ChainDbHelper(getApplicationContext());
		
		Bundle b = getIntent().getExtras();
		long gameId = b.getLong("gameId");
		
		Log.d("UnlockActivity", "Game Id = " + gameId);
		
		if (gameId>0) {
			game = db.getGame(gameId);
		
			if (game.getId() == gameId) {
				Log.i("UnlockActivity", "Game [" +  gameId + "] score=" + game.getScore());
			}
		}
		else {
			Log.d("UnlockActivity", "Invalid game id passed.");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.unlock, menu);
		return true;
	}

}
