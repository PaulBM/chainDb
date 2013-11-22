package com.example.dbtest;

import java.util.Date;
import java.util.Random;

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
		boolean done = false;
		TextView tv = new TextView(this);
		LinearLayout layout = new LinearLayout(this);
		int numAchs = 0;
		int numUnlocked = 0;
		Random rand = new Random();
		DbUnlocked unlock = new DbUnlocked();
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_unlock);
		
		db = new ChainDbHelper(getApplicationContext());

		layout = (LinearLayout) findViewById(R.id.layoutUnlockeds);

		Bundle b = getIntent().getExtras();
		long gameId = b.getLong("gameId");
		
		Log.d("UnlockActivity", "Game Id = " + gameId);
		
		if (gameId>0) {
			game = db.getGame(gameId);
		
			if (game.getId() == gameId) {
				Log.i("UnlockActivity", "Game [" +  gameId + "] score=" + game.getScore());

				tv = (TextView) findViewById(R.id.tvGameId);
				tv.setText("for game ID : " + gameId);
				
				//pick a random achievement
				numAchs=db.countTableRows("achievements");
				// method requires two strings, cast long Id to string
				numUnlocked=db.countUnlocked("game", gameId);
				
				if (numUnlocked != numAchs) {
					//still some achievements to unlock
					
					while (done==false) {
						//randomly choose an achievement id
						int achId = rand.nextInt(numAchs)+1;
						
						if (!db.isAchUnlocked(achId, game.getPlayerId())) {
							unlock = new DbUnlocked(
									achId,
									game.getPlayerId(),
									game.getId(),
									0, new Date().toString());
							db.insertUnlocked(unlock);

							LayoutParams lparams=new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
							tv = Utils.makeTextView(this, "Ach [" + achId + "] for game [" + gameId + "]" , lparams);
							layout.addView(tv);
							
							done=true;
						}
					}
				}
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
