package com.example.dbtest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PlayerActivity extends Activity implements OnClickListener  {

	ChainDbHelper db;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
//		TextView tv;
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player);

		db = new ChainDbHelper(getApplicationContext());
		
		/*
		tv = (TextView) findViewById(R.id.tvPlayersTitle);
		tv.setText("Players");
		*/

		Button btnAddPlayer = (Button) findViewById(R.id.btnAddPlayer);
        btnAddPlayer.setOnClickListener(this);
		
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch ( v.getId()) {
			case  R.id.btnAddPlayer :
				Log.i("addPlayer","onlick");
				addPlayer();
				break;
		
		}
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
		String whereClause=null;

		objPlayers=db.getPlayersList(whereClause);
		
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
				Log.i("readPlayers", "Ready to add new Player TextView");
				layout.addView(tv);

				Log.d("Info", "TextView added " + id);

				// keep a count of list size
				playersCount++;
			}
			Log.i("readPlayers", "Found " + playersCount + " players");
		} else {
			tv = new TextView(this);
			tv.setText("No Players in database.");
			tv.setLayoutParams(new LayoutParams(
						LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT));
			layout.addView(tv);
			Log.i("readPlayers", "Default textview added. (Players 0)");
		}

	}
	
	/**
	 * Adds a player to the datase, using the textview text for the player's name
	 */
	public void addPlayer()
	{
		long newId=0;
		String name=null;
		EditText et = new EditText(this);
		DbPlayer player = new DbPlayer();
		
		Log.i("addPlayer","ready to read EditText");
		
		et=(EditText) findViewById(R.id.etNewPlayer);
		//strange syntax to get the string value.
		name = et.getText().toString();
		
		if (name != null) {
			Log.i("addPlayer","Player name " + name);
			
			player.setName(name);
			
			newId = db.insertPlayer(player);
			
			Log.i("addPlayer","new Player id " + newId);

		}
		
	}



}
