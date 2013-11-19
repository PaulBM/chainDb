package com.example.dbtest;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

public class NewGameActivity extends Activity implements OnClickListener {

	ChainDbHelper db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_game);
		
		db = new ChainDbHelper(getApplicationContext());
		
		Button btnAddNewGame = (Button) findViewById(R.id.btnAddNewGame);
        btnAddNewGame.setOnClickListener(this);		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_game, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch ( v.getId()) {
			case  R.id.btnAddNewGame :
				Log.i("addGame","onlick");
				addGame();
				break;
		
		}
	}
	
	/**
	 * 
	 */
	public void addGame()
	{
		boolean error=false;
		long newId=0;
		DbGame game = new DbGame();
		int diff = 0;
		String startTime = null;
		String startDate = null;
		String endTime = null;
		String endDate = null;
		String score = null;
		String level = null;
		String playerName = null;
		long playerId=0;
		List<DbPlayer> objPlayers = new ArrayList<DbPlayer>();
		
		Log.i("addGame","ready to read form data");
		
		startTime = (String) ((TextView) findViewById(R.id.etStartTime)).getText().toString();
		startDate = (String) ((TextView) findViewById(R.id.etStartDate)).getText().toString();
		endTime = (String) ((TextView) findViewById(R.id.etEndTime)).getText().toString();
		endDate = (String) ((TextView) findViewById(R.id.etEndDate)).getText().toString();
		
		if (((RadioButton) findViewById(R.id.rbtnEasy)).isChecked()) {
			diff=1;
		}
		if (((RadioButton) findViewById(R.id.rbtnMedium)).isChecked()) {
			diff=2;
		}
		if (((RadioButton) findViewById(R.id.rbtnHard)).isChecked()) {
			diff=3;
		}

		score = (String) ((TextView) findViewById(R.id.etScore)).getText().toString();
		level = (String) ((TextView) findViewById(R.id.etLevel)).getText().toString();
		
		playerName = (String) ((TextView) findViewById(R.id.ac_tvChoosePlayer)).getText().toString();

		Log.d("addGame","startTime " + startTime);
		Log.d("addGame","startDate " + startDate);
		Log.d("addGame","endTime " + endTime);
		Log.d("addGame","endDate " + endDate);
		Log.d("addGame","diff " + diff);
		Log.d("addGame","score " + score);
		Log.d("addGame","level " + level);

		Log.d("addGame","playerName search" + playerName);
		
		objPlayers=db.getPlayersList(playerName);
		// if we have 1 match that's our player
		if (objPlayers.size() == 1) {
			playerId=objPlayers.get(0).getId();
			Log.d("addGame","Found  " + objPlayers.get(0).getName() + " Id = " + playerId);
		}
		else {
			Log.d("addGame","Found  " + objPlayers.size() + " players");
			error=true;
		}

		
		//temp flag error to avoid the db insert
		error=true;
		
		if (!error) {
			Log.i("addGame","Game data OK");
			
			
			newId = db.insertGame(game);
			
			Log.i("addGame","new Game id " + newId);

		}
		
	}	
}
