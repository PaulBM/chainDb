package com.example.dbtest;

import java.util.Date;
import java.util.List;
import java.util.Random;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Unlock an Achievement for the given game ID.
 * 
 * @author PaulBM
 * 
 */
public class UnlockActivity extends Activity implements OnClickListener {

	ChainDbHelper db;
	DbGame currentGame = new DbGame();
	LinearLayout layoutUnlocks = null;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		TextView tv = new TextView(this);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_unlock);

		layoutUnlocks = (LinearLayout) findViewById(R.id.layoutUnlockeds);
		
		db = new ChainDbHelper(getApplicationContext());

		Bundle b = getIntent().getExtras();
		long gameId = b.getLong("gameId");

		currentGame = db.getGame(gameId);

		// set up Random Unlock button function

		if (gameId > 0) {
			tv = (TextView) findViewById(R.id.tvGameId);
			tv.setText("for game ID : " + gameId);

			Log.i("UnlockActivity", "about to display Unlocked");
			
			//read Unlocked achievements
			displayUnlocked();

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.unlock, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.btnUnlockRandom:
			Log.i("btnUnlockRandom", "onlick");
			unlockRandom();
			break;

		}
	}

	/**
	 * Add textviews for unlocked achievements
	 */
	public void displayUnlocked() {
		TextView tv = new TextView(this);
		long gameId = currentGame.getId();
		int unlockedCount = 0;

		List<DbUnlocked> unlockeds = db.getGameUnlocked(gameId);

		if (gameId > 0) {
			if (unlockeds.size() > 0) {
				for (int i = 0; i < unlockeds.size(); i++) {
					Log.i("displayUnlocked", "loop " + i);
					
					tv = new TextView(this);
					tv.setText("Unlocked ["
							+ unlockeds.get(i).getAchievementId() + "] "
							+ unlockeds.get(i).getDatetime());
					tv.setId((int) i + 1);
					tv.setLayoutParams(new LayoutParams(
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));

					layoutUnlocks.addView(tv);

					Log.d("Info", "TextView added " + i);

					unlockedCount++;
				}
				Log.d("Info", "Found " + unlockedCount
						+ " unlocked achievemnts");
			} else {
				tv = new TextView(this);
				tv.setText("No unlocked achievemnts for this Game.");

				tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT));
				layoutUnlocks.addView(tv);

				Log.d("Info", "Default textview added. (unlocked 0)");
			}
		} else {
			Log.d("UnlockActivity", "Invalid game id passed.");
		}
	}

	public void unlockRandom() {
		boolean done = false;
		long numAchs = 0;
		int numUnlocked = 0;
		Random rand = new Random();
		DbUnlocked unlock = new DbUnlocked();
		long gameId = currentGame.getId();

		if (gameId > 0) {
			Log.d("randomUnlock", "Game Id = " + gameId);

			// pick a random achievement
			numAchs = db.countTableRows("achievements");
			Log.i("randomUnlock", numAchs + " achievements found.");
			// method requires two strings, cast long Id to string
			numUnlocked = db.countUnlocked("game", currentGame.getId());
			Log.i("randomUnlock", numAchs + " unlocked achievements found.");

			if (numUnlocked != numAchs) {
				// still some achievements to unlock

				while (done == false) {
					// randomly choose an achievement id
					long achId = rand.nextInt((int) numAchs) + 1;

					if (!db.isAchUnlocked(achId, currentGame.getPlayerId())) {
						unlock = new DbUnlocked(achId,
								currentGame.getPlayerId(), gameId, 0,
								new Date().toString());
						db.insertUnlocked(unlock);

						/*
						LayoutParams lparams = new LayoutParams(
								LayoutParams.WRAP_CONTENT,
								LayoutParams.WRAP_CONTENT);
						tv = Utils.makeTextView(this, "Ach [" + achId
								+ "] for game [" + currentGame.getId() + "]",
								lparams);
						layout.addView(tv);
*/
						Log.i("randomUnlock", "Game [" + gameId + "] score="
								+ currentGame.getScore());
						done = true;
					}
				}
			}
		} else {
			Log.d("UnlockActivity", "Invalid game id passed.");
		}

	}

}