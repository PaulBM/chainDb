package com.example.dbtest;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Unlock an Achievement for the given game ID.
 * 
 * @author PaulBM
 *
 */
public class UnlockActivity extends Activity {

	ChainDbHelper db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		DbGame game = new DbGame();
		TextView tv = new TextView(this);
		LinearLayout layout = new LinearLayout(this);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_unlock);
		
		db = new ChainDbHelper(getApplicationContext());

		layout = (LinearLayout) findViewById(R.id.layoutUnlock);

		Bundle b = getIntent().getExtras();
		long gameId = b.getLong("gameId");
		
		Log.d("UnlockActivity", "Game Id = " + gameId);
		
		if (gameId>0) {
			game = db.getGame(gameId);
		
			if (game.getId() == gameId) {
				Log.i("UnlockActivity", "Game [" +  gameId + "] score=" + game.getScore());

				tv = (TextView) findViewById(R.id.tvGameId);
				tv.setText("for game ID : " + gameId);
				/*
				LayoutParams lparams=new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
				tv = Utils.makeTextView(this, "for game ID : " + gameId , lparams);
				layout.addView(tv);
				*/
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
