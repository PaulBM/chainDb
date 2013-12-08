/**
 * 
 */
package com.example.dbtest;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author PaulBM
 * @version 0.1
 */
public class ChainDbHelper extends SQLiteOpenHelper {

	// Database Version
	private static final int DATABASE_VERSION = 3;

	// Database Name
	private static final String DATABASE_NAME = "chain.db";

	private static final String ANON = "Anonymous";
	// Table Names
	private static final String TABLE_ACHIEVEMENTS = "achievements";
	private static final String TABLE_GAMES = "games";
	private static final String TABLE_PLAYERS = "players";
	private static final String TABLE_UNLOCKED = "unlocked";

	// Achievements Table - column names
	private static final String ACH_ID = "ach_id";
	private static final String ACH_NAME = "ach_name";
	private static final String ACH_DESC = "ach_description";
	private static final String ACH_DIFF = "ach_difficulty";
	private static final String ACH_HIDDEN = "ach_hidden";

	// Games Table - column names
	private static final String GAME_ID = "game_id";
	private static final String GAME_START = "game_start";
	private static final String GAME_END = "game_end";
	private static final String GAME_DUR = "game_duration";
	private static final String GAME_SCORE = "game_score";
	private static final String GAME_DIFF = "game_difficulty";
	private static final String GAME_LEVEL = "game_level";
	private static final String GAME_TYPE = "game_type";
	private static final String GAME_BLOCKS = "game_blocks";
	private static final String GAME_PLAYER = "game_player_id";

	// Players Table - column names
	private static final String PLAYER_ID = "player_id";
	private static final String PLAYER_NAME = "player_name";

	// Achieved Table - column names
	private static final String UNLOCKED_ACHIEVEMENT = "achievement";
	private static final String UNLOCKED_PLAYER = "player";
	private static final String UNLOCKED_GAME = "game";
	private static final String UNLOCKED_GOOGLED = "googled";
	private static final String UNLOCKED_DATETIME = "datetimeUn";

	// Table Create Statements
	// Achievements table create statement
	private static final String CREATE_TABLE_ACHIEVEMENTS = "CREATE TABLE if not exists "
			+ TABLE_ACHIEVEMENTS + "(" + 
			ACH_ID + " INTEGER PRIMARY KEY," + 
			ACH_NAME + " TEXT," + 
			ACH_DESC + " TEXT," + 
			ACH_DIFF + " INTEGER," + 
			ACH_HIDDEN + " INTEGER" +
			")";

	// Games table create statement
	private static final String CREATE_TABLE_GAMES = "CREATE TABLE if not exists "
			+ TABLE_GAMES + "(" + 
			GAME_ID + " INTEGER PRIMARY KEY," +
			GAME_START + " DATETIME," + 
			GAME_END + " DATETIME," + 
			GAME_DUR + " LONGINT," + 
			GAME_SCORE + " LONGINT," + 
			GAME_DIFF + " INTEGER,"	+ 
			GAME_LEVEL + " INTEGER," + 
			GAME_TYPE + " INTEGER," +
			GAME_BLOCKS + " INTEGER," +
			GAME_PLAYER + " INTEGER" + 
			")";

	// Players table create statement
	private static final String CREATE_TABLE_PLAYERS = "CREATE TABLE if not exists "
			+ TABLE_PLAYERS + "(" + 
			PLAYER_ID + " INTEGER PRIMARY KEY,"	+ 
			PLAYER_NAME + " TEXT" + 
			")";

	// Achieved table create statement
	private static final String CREATE_TABLE_UNLOCKED = "CREATE TABLE if not exists "
			+ TABLE_UNLOCKED + "(" + 
			UNLOCKED_ACHIEVEMENT + " INTEGER NOT NULL," +
			UNLOCKED_PLAYER + " INTEGER NOT NULL," +
			UNLOCKED_GAME + " INTEGER NOT NULL," +
			UNLOCKED_GOOGLED + " INTEGER DEFAULT 0, " +
			UNLOCKED_DATETIME + " timestamp default CURRENT_TIMESTAMP" +
			")";

	public ChainDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// creating required tables

		Log.i("onCreate", "Creating table(s).");
		
		db.execSQL(CREATE_TABLE_ACHIEVEMENTS);
		Log.i("onCreate", "Achievements table created");

		db.execSQL(CREATE_TABLE_GAMES);
		db.execSQL(CREATE_TABLE_PLAYERS);
		db.execSQL(CREATE_TABLE_UNLOCKED);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// on upgrade drop older tables
//		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACHIEVEMENTS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAMES);
//		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYERS);
		
		
//		db.execSQL("DROP TABLE IF EXISTS " + TABLE_UNLOCKED);

		Log.i("onUpgrade", "Dropped the table(s).");
		// create new tables
		onCreate(db);
	}

	@Override
	public void onOpen(SQLiteDatabase db)
	{
		List<DbPlayer> players = new ArrayList<DbPlayer>();		
		if (!db.isReadOnly()) {
			players = getPlayersList(ANON); 
			if (players.size()==0) {
				// no anonymous player found.
				
			}
		}
	}
	
	// ******* General methods ****************/

	public long countTableRows(String table)
	{
		SQLiteDatabase db = this.getReadableDatabase();
		return DatabaseUtils.queryNumEntries(db, table);
	}

	// ****************** Achievements ************************************

	/**
	 * Check if Achievement is in db, insert if not or update.
	 * 
	 * @param ach
	 */
	public void storeAchievement(DbAchievement ach) {
		DbAchievement tempAch = new DbAchievement();

		Log.d("Info", "Ready to getAchievement " + ach.getId());
		tempAch = getAchievement(ach.getId());

		if (tempAch.getId() > 0) {
			Log.d("Info", "Update required.");
			updateAchievement(ach);
			Log.d("Info", "Achievement updated.");
		} else {
			Log.d("Info", "Insert required.");
			insertAchievement(ach);
			Log.d("Info", "Achievement inserted.");
		}
	}

	/**
	 * Insert an achievement into database
	 * 
	 * @param achievement
	 * @return id of new achievement record or error code from SQLite
	 */
	public long insertAchievement(DbAchievement achievement) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(ACH_ID, achievement.getId());
		values.put(ACH_NAME, achievement.getName());
		values.put(ACH_DESC, achievement.getAchDesc());
		values.put(ACH_DIFF, achievement.getAchDiff());
		values.put(ACH_DIFF, achievement.getAchHidden());

		// insert row
		long id = db.insert(TABLE_ACHIEVEMENTS, null, values);
		return id;
	}

	/**
	 * Update an achievement into database
	 * 
	 * @param achievement
	 * @return rows number of affected rows or error code from SQLite
	 */
	public int updateAchievement(DbAchievement achievement) {
		int rows = 0;
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(ACH_NAME, achievement.getName());
		values.put(ACH_DESC, achievement.getAchDesc());
		values.put(ACH_DIFF, achievement.getAchDiff());
		values.put(ACH_DIFF, achievement.getAchHidden());

		// update row - table, values, where clause, where args
		rows = db.update(TABLE_ACHIEVEMENTS, values, ACH_ID + "= ?",
				new String[] { String.valueOf(achievement.getId()) });
		return rows;
	}

	/**
	 * Read an achievement from the database
	 * 
	 * @param id
	 * @return DbAchievement object
	 */
	public DbAchievement getAchievement(long id) {
		SQLiteDatabase db = this.getReadableDatabase();
		DbAchievement obj = new DbAchievement();

		if (id > 0) {
			String selectQuery = "SELECT * FROM " + TABLE_ACHIEVEMENTS
					+ " WHERE " + ACH_ID + " = " + id;

			// Log.e(LOG, selectQuery);

			Log.d("Info", "Reading achievement from db. " + selectQuery);

			Cursor c = db.rawQuery(selectQuery, null);

			if (c != null && c.moveToFirst()) {
				obj = new DbAchievement(c.getInt(c.getColumnIndex(ACH_ID)),
						c.getString(c.getColumnIndex(ACH_NAME)), c.getString(c
								.getColumnIndex(ACH_DESC)), c.getInt(c
								.getColumnIndex(ACH_DIFF)), c.getInt(c
								.getColumnIndex(ACH_HIDDEN)));
			} else {
				Log.d("Info", "Cursor == null (nothing found).");
			}
		} else {
			Log.d("Error!", "Id of 0 passed to getAchievement()");
		}
		return obj;
	}

	/**
	 * Get all achievements
	 * 
	 * @return List of DbAchievement objects
	 */
	public List<DbAchievement> getAllAchievements() {
		List<DbAchievement> achs = new ArrayList<DbAchievement>();
		String selectQuery = "SELECT  * FROM " + TABLE_ACHIEVEMENTS;

		// Log.e(LOG, selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				DbAchievement ach = new DbAchievement();
				ach.setId(c.getLong(c.getColumnIndex(ACH_ID)));
				ach.setName(c.getString(c.getColumnIndex(ACH_NAME)));
				ach.setAchDesc(c.getString(c.getColumnIndex(ACH_DESC)));
				ach.setAchDiff(c.getInt(c.getColumnIndex(ACH_DIFF)));
				ach.setAchDiff(c.getInt(c.getColumnIndex(ACH_HIDDEN)));

				// adding to achieved list
				achs.add(ach);
			} while (c.moveToNext());
		}

		return achs;
	}

	/**
	 * Get all achievemnts not unlocked by a player
	 * @param playerId
	 * @return
	 */
	public List<DbAchievement> getAchNotUnlockedForPlayer(long playerId)
	{
		List<DbAchievement> notUnAchs = new ArrayList<DbAchievement>();
		String sql= "select * from " + TABLE_ACHIEVEMENTS + "where " + ACH_ID + 
				"not in (select " + UNLOCKED_ACHIEVEMENT + " from " + TABLE_UNLOCKED + 
				" where " + UNLOCKED_PLAYER + " = " + playerId +")";
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(sql, null);

		// looping through all rows and adding to list
		if (c != null && c.moveToFirst()) {
			do {
				DbAchievement ach = new DbAchievement();
				ach.setId(c.getLong(c.getColumnIndex(ACH_ID)));
				ach.setName(c.getString(c.getColumnIndex(ACH_NAME)));
				ach.setAchDesc(c.getString(c.getColumnIndex(ACH_DESC)));
				ach.setAchDiff(c.getInt(c.getColumnIndex(ACH_DIFF)));
				ach.setAchDiff(c.getInt(c.getColumnIndex(ACH_HIDDEN)));

				// adding to unlocked list
				notUnAchs.add(ach);
			} while (c.moveToNext());
		}
		return notUnAchs;
	}	
	
	
	// ****************** Players **********************************

	/**
	 * Insert player into database
	 * 
	 * @param player
	 * @return id of the new player
	 */
	public long insertPlayer(DbPlayer player) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(PLAYER_NAME, player.getName());

		// insert row
		long id = db.insert(TABLE_PLAYERS, null, values);
		return id;
	}

	/**
	 * Read player from database
	 * 
	 * @param id
	 * @return a player object
	 */
	public DbPlayer getPlayer(long id) {
		SQLiteDatabase db = this.getReadableDatabase();
		DbPlayer obj = new DbPlayer();

		String selectQuery = "SELECT  * FROM " + TABLE_PLAYERS + " WHERE "
				+ PLAYER_ID + " = " + id;

		// Log.e(LOG, selectQuery);

		Cursor c = db.rawQuery(selectQuery, null);

		if (c != null && c.moveToFirst()) {

			obj = new DbPlayer(c.getInt(c.getColumnIndex(PLAYER_ID)),
					c.getString(c.getColumnIndex(PLAYER_NAME)));
		}

		return obj;
	}

	/**
	 * Update a player in the database
	 * 
	 * @param player
	 * @return update return code
	 */
	public int updatePlayer(DbPlayer player) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(PLAYER_NAME, player.getName());

		// updating row
		return db.update(TABLE_PLAYERS, values, PLAYER_ID + " = ?",
				new String[] { String.valueOf(player.getId()) });
	}

	/**
	 * Get all Players or players with name like the string passed
	 * 
	 * @return List of DbPlayer objects
	 */
	public List<DbPlayer> getPlayersList(String name) {
		List<DbPlayer> objs = new ArrayList<DbPlayer>();
		String selectQuery = "SELECT  * FROM " + TABLE_PLAYERS;

		if (name != null) {
			selectQuery += " where player_name like '%" + name + "%'";
		}
		// Log.e(LOG, selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c != null && c.moveToFirst()) {
			do {
				DbPlayer obj = new DbPlayer();
				obj.setId(c.getLong(c.getColumnIndex(PLAYER_ID)));
				obj.setName(c.getString(c.getColumnIndex(PLAYER_NAME)));

				// adding to player list
				objs.add(obj);
			} while (c.moveToNext());
		}

		return objs;
	}

	// ***************** Games **********************************

	/**
	 * Insert a new game database entry
	 * 
	 * @param game
	 * @return id of new game
	 */
	public long insertGame(DbGame game) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(GAME_START, game.getStart());
		values.put(GAME_END, game.getEnd());
		values.put(GAME_DUR, game.getDuration());
		values.put(GAME_SCORE, game.getScore());
		values.put(GAME_DIFF, game.getDifficulty());
		values.put(GAME_LEVEL, game.getLevel());
		values.put(GAME_TYPE, game.getType());
		values.put(GAME_BLOCKS, game.getBlocks());
		values.put(GAME_PLAYER, game.getPlayerId());

		// insert row
		long id = db.insert(TABLE_GAMES, null, values);
		return id;
	}

	/**
	 * Read a game object from the database for the given id
	 * 
	 * @param id
	 * @return a game object
	 */
	public DbGame getGame(long id) {
		SQLiteDatabase db = this.getReadableDatabase();
		DbGame obj = new DbGame();

		String selectQuery = "SELECT  * FROM " + TABLE_GAMES + " WHERE "
				+ GAME_ID + " = " + id;

		// Log.e(LOG, selectQuery);

		Cursor c = db.rawQuery(selectQuery, null);

		if (c != null && c.moveToFirst()) {

			obj = new DbGame(c.getInt(c.getColumnIndex(GAME_ID)), 
					c.getString(c.getColumnIndex(GAME_START)),
					c.getString(c.getColumnIndex(GAME_END)),
					c.getLong(c.getColumnIndex(GAME_DUR)), 
					c.getInt(c.getColumnIndex(GAME_SCORE)), 
					c.getInt(c.getColumnIndex(GAME_DIFF)), 
					c.getInt(c.getColumnIndex(GAME_LEVEL)),
					c.getInt(c.getColumnIndex(GAME_TYPE)),
					c.getInt(c.getColumnIndex(GAME_BLOCKS)),
					c.getLong(c.getColumnIndex(GAME_PLAYER)));
		} else {
			Log.d("getGame", "unable to find game id " + id);
		}

		return obj;
	}

	/**
	 * Get all games
	 * 
	 * @return List of DbGame objects
	 */
	public List<DbGame> getAllGames() {
		List<DbGame> objs = new ArrayList<DbGame>();
		String selectQuery = "SELECT  * FROM " + TABLE_GAMES;

		// Log.e(LOG, selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c != null && c.moveToFirst()) {
			do {
				DbGame game = new DbGame();
				game.setId(c.getLong(c.getColumnIndex(GAME_ID)));

				game.setStart(c.getString(c.getColumnIndex(GAME_START)));
				game.setEnd(c.getString(c.getColumnIndex(GAME_END)));
				game.setDuration(c.getLong(c.getColumnIndex(GAME_DUR)));
				game.setScore(c.getInt(c.getColumnIndex(GAME_SCORE)));
				game.setDifficulty(c.getInt(c.getColumnIndex(GAME_DIFF)));
				game.setLevel(c.getInt(c.getColumnIndex(GAME_LEVEL)));
				game.setType(c.getInt(c.getColumnIndex(GAME_TYPE)));
				game.setBlocks(c.getInt(c.getColumnIndex(GAME_BLOCKS)));
				game.setPlayerId(c.getLong(c.getColumnIndex(GAME_PLAYER)));

				// adding to games list
				objs.add(game);
			} while (c.moveToNext());
		}

		return objs;
	}

	
	
	// *********** Unlocked achievements *********************

	/**
	 * Insert an unlocked achievement into database
	 * 
	 * @param achieved
	 * @return array of the primary keys
	 */
	public long insertUnlocked(DbUnlocked achieved) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(UNLOCKED_GAME, achieved.getGameId());
		values.put(UNLOCKED_ACHIEVEMENT, achieved.getAchievementId());
		values.put(UNLOCKED_PLAYER, achieved.getPlayerId());
		values.put(UNLOCKED_GOOGLED, achieved.getGoogled());
		values.put(UNLOCKED_DATETIME, achieved.getDatetime());

		// insert row
		long id = db.insert(TABLE_UNLOCKED, null, values);
		return id;
	}

	/**
	 * Get all unlocked by player id
	 * 
	 * @return List of DbUnlocked objects
	 */
	public List<DbUnlocked> getPlayerUnlocked(long id) {
		List<DbUnlocked> unlockeds = new ArrayList<DbUnlocked>();
		String selectQuery = "SELECT  * FROM " + TABLE_UNLOCKED
				+ " WHERE player_id=" + id;

		// Log.e(LOG, selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c != null && c.moveToFirst()) {
			do {
				DbUnlocked unlock = new DbUnlocked();
				unlock.setPlayerId(id);
				unlock.setGameId((c.getLong(c.getColumnIndex(UNLOCKED_GAME))));
				unlock.setAchievementId(c.getLong(c
						.getColumnIndex(UNLOCKED_ACHIEVEMENT)));
				unlock.setGoogled(c.getInt(c.getColumnIndex(UNLOCKED_GOOGLED)));
				unlock.setDatetime(c.getString(c
						.getColumnIndex(UNLOCKED_DATETIME)));

				// adding to unlocked list
				unlockeds.add(unlock);
			} while (c.moveToNext());
		}

		return unlockeds;
	}
	
	/**
	 * Get all unlocked by game id
	 * 
	 * @return List of DbUnlocked objects
	 */
	public List<DbUnlocked> getGameUnlocked(long id) {
		List<DbUnlocked> unlockeds = new ArrayList<DbUnlocked>();
		String selectQuery = "SELECT * FROM " + TABLE_UNLOCKED
				+ " WHERE " + UNLOCKED_GAME + " = " + id;

		// Log.e(LOG, selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c != null && c.moveToFirst()) {
			do {
				DbUnlocked unlock = new DbUnlocked();
				unlock.setPlayerId((c.getLong(c.getColumnIndex(UNLOCKED_PLAYER))));
				unlock.setGameId(id);
				unlock.setAchievementId(c.getLong(c.getColumnIndex(UNLOCKED_ACHIEVEMENT)));
				unlock.setGoogled(c.getInt(c.getColumnIndex(UNLOCKED_GOOGLED)));
				unlock.setDatetime(c.getString(c.getColumnIndex(UNLOCKED_DATETIME)));

				// adding to unlocked list
				unlockeds.add(unlock);
			} while (c.moveToNext());
		}

		return unlockeds;
	}
	
	/**
	 * Check to see if an achievement is unlocked for a player
	 * @param achId
	 * @param playerId
	 * @return boolean 
	 */
	public boolean isAchUnlocked(long achId, long playerId)
	{
		boolean unlocked=false;
		String sql = null;
		
		sql = "select count(*) from " + TABLE_UNLOCKED + " where " + UNLOCKED_ACHIEVEMENT + "= ? and " + UNLOCKED_PLAYER + "= ?" ;
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor c = db.rawQuery(sql, new String[] {String.valueOf(achId), String.valueOf(playerId)} );		
		if (c != null && c.moveToFirst()) {
			if (c.getInt(0) != 0) {
				unlocked = true;
			}
		}
		return unlocked;
	}

	/**
	 * Count unlocked achievements for given field and Id
	 * @param field
	 * @param Id
	 * @return
	 */
	public int countUnlocked(String field, long Id)
	{
		int unlocked = 0;
		String sql = null;
		
		sql = "SELECT COUNT(*) FROM unlocked WHERE ? = ?";
		Log.i("countUnlocked", "where " + field + "=" + Id);

		SQLiteDatabase db = this.getReadableDatabase();
		
		Log.i("countUnlocked", "executing query...");

		Cursor c = db.rawQuery(sql, new String[] {field, String.valueOf(Id) } );

		if (c != null && c.moveToFirst()) {
			unlocked = c.getInt(0);
		}
		return unlocked;
	}
}