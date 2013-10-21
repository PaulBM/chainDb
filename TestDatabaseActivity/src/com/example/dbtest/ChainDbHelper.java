/**
 * 
 */
package com.example.dbtest;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author PaulBM
 * @version 0.1
 */
public class ChainDbHelper extends SQLiteOpenHelper {
	
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "chain.db";
 
    // Table Names
    private static final String TABLE_ACHIEVEMENTS = "achievements";
    private static final String TABLE_GAMES = "games";
    private static final String TABLE_PLAYERS = "players";
    private static final String TABLE_ACHIEVED = "achieved";

    // Achievements Table - column names
    private static final String ACH_ID = "ach_id";
    private static final String ACH_NAME = "ach_name";
    private static final String ACH_DESC = "ach_description";
     
    //Games Table - column names
    private static final String GAME_ID ="game_id";
    private static final String GAME_START ="game_start";
    private static final String GAME_END ="game_end";
    private static final String GAME_DUR ="game_duration";
    private static final String GAME_SCORE ="game_score";
    private static final String GAME_DIFF ="game_difficulty";
    private static final String GAME_LEVEL ="game_level";
    private static final String GAME_PLAYER ="game_player_id";
    
    //Players Table - column names
    private static final String PLAYER_ID ="player_id";
    private static final String PLAYER_NAME ="player_name";
    
    //Achieved Table - column names
    private static final String ACHIEVED_ACHIEVEMENT ="achievement";
    private static final String ACHIEVED_PLAYER ="player";
    private static final String ACHIEVED_GAME ="game";
    
    // Table Create Statements
    // Achievements table create statement
    private static final String CREATE_TABLE_ACHIEVEMENTS = "CREATE TABLE " + TABLE_ACHIEVEMENTS + "(" 
    + ACH_ID + " INTEGER PRIMARY KEY,"
    + ACH_NAME + " TEXT,"
    + ACH_DESC + " TEXT"
    + ")";    
    
    // Games table create statement
    private static final String CREATE_TABLE_GAMES = "CREATE TABLE " + TABLE_GAMES + "(" 
    + GAME_ID + " INTEGER PRIMARY KEY,"
    + GAME_START + " DATETIME,"
    + GAME_END + " DATETIME,"
    + GAME_DUR + " LONGINT,"
    + GAME_SCORE + " LONGINT,"
    + GAME_DIFF + " INT,"
    + GAME_LEVEL + " INT,"
    + GAME_PLAYER + " INT"
    + ")";    
    
    //Players table create statement
    private static final String CREATE_TABLE_PLAYERS = "CREATE TABLE " + TABLE_PLAYERS + "("
    + PLAYER_ID + " INTEGER PRIMARY KEY,"
    + PLAYER_NAME + " TEXT,"
    + ")";    
    		
    //Achieved table create statement
    private static final String CREATE_TABLE_ACHIEVED = "CREATE TABLE " + TABLE_ACHIEVED + "("
    + ACHIEVED_ACHIEVEMENT + " INTEGER NOT NULL PRIMARY KEY,"
    + ACHIEVED_PLAYER + " INTEGER NOT NULL PRIMARY KEY,"
    + ACHIEVED_GAME + " INTEGER NOT NULL PRIMARY KEY,"
    + ")";    
    
    
    public ChainDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
 
        // creating required tables
        db.execSQL(CREATE_TABLE_ACHIEVEMENTS);
        db.execSQL(CREATE_TABLE_GAMES);
        db.execSQL(CREATE_TABLE_PLAYERS);
        db.execSQL(CREATE_TABLE_ACHIEVED);
    }    

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACHIEVEMENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAMES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACHIEVED);
 
        // create new tables
        onCreate(db);
    }
    
    
    /*
     * Creating an achievement
     */
    public long createAchievement(DbAchievement achievement) {
        SQLiteDatabase db = this.getWritableDatabase();
     
        ContentValues values = new ContentValues();
        values.put(ACH_NAME, achievement.getName());
        values.put(ACH_DESC, achievement.getAchDesc());
     
        // insert row
        long id = db.insert(TABLE_ACHIEVEMENTS, null, values);
        return id;
    }
    
    /*
     * get an achievement
     */
    public DbAchievement getAchievement(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
     
        String selectQuery = "SELECT  * FROM " + TABLE_ACHIEVEMENTS + " WHERE " + ACH_ID + " = " + id;
     
//        Log.e(LOG, selectQuery);
     
        Cursor c = db.rawQuery(selectQuery, null);
     
        if (c != null)
            c.moveToFirst();
     
        DbAchievement obj = new DbAchievement(c.getInt(c.getColumnIndex(ACH_ID)),
        		c.getString(c.getColumnIndex(ACH_NAME)), 
        		c.getString(c.getColumnIndex(ACH_DESC)));
        /*
        td.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        td.setNote((c.getString(c.getColumnIndex(KEY_TODO))));
        td.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));
     */
        return obj;
    }    
    
    /*
     * Creating a player
     */
    public long createPlayer(DbPlayer player) {
        SQLiteDatabase db = this.getWritableDatabase();
     
        ContentValues values = new ContentValues();
        values.put(PLAYER_NAME, player.getName());
     
        // insert row
        long id = db.insert(TABLE_PLAYERS, null, values);
        return id;
    }  
    
    /*
     * get a player
     */
    public DbPlayer getPlayer(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
     
        String selectQuery = "SELECT  * FROM " + TABLE_PLAYERS + " WHERE " + PLAYER_ID + " = " + id;
     
//        Log.e(LOG, selectQuery);
     
        Cursor c = db.rawQuery(selectQuery, null);
     
        if (c != null)
            c.moveToFirst();
     
        DbPlayer obj = new DbPlayer(c.getInt(c.getColumnIndex(PLAYER_ID)),
        		c.getString(c.getColumnIndex(PLAYER_NAME)));
        /*
        td.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        td.setNote((c.getString(c.getColumnIndex(KEY_TODO))));
        td.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));
     */
        return obj;
    }   
    
    /*
     * Updating a player
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
     * Create a new game database entry
     * @param game
     * @return id of new game
     */
    public long createGame(DbGame game) {
        SQLiteDatabase db = this.getWritableDatabase();
     
        ContentValues values = new ContentValues();
        values.put(GAME_START, game.getStart());
        values.put(GAME_END, game.getEnd());
        values.put(GAME_DUR, game.getDuration());
        values.put(GAME_SCORE, game.getScore());
        values.put(GAME_DIFF, game.getDifficulty());
        values.put(GAME_LEVEL, game.getLevel());
        values.put(GAME_PLAYER, game.getPlayerId());
        
        // insert row
        long id = db.insert(TABLE_GAMES, null, values);
        return id;
    }  
    
    /**
     * Read a game object from the database for the given id
     * @param id
     * @return a game object
     */
    public DbGame getGame(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
     
        String selectQuery = "SELECT  * FROM " + TABLE_GAMES + " WHERE " + GAME_ID + " = " + id;
     
//        Log.e(LOG, selectQuery);
     
        Cursor c = db.rawQuery(selectQuery, null);
     
        if (c != null)
            c.moveToFirst();
     
        DbGame obj = new DbGame(c.getInt(c.getColumnIndex(GAME_ID)),
        		c.getString(c.getColumnIndex(GAME_START)),
        		c.getString(c.getColumnIndex(GAME_END)),
        		c.getLong(c.getColumnIndex(GAME_DUR)),
        		c.getInt(c.getColumnIndex(GAME_SCORE)),
        		c.getInt(c.getColumnIndex(GAME_DIFF)),
        		c.getInt(c.getColumnIndex(GAME_LEVEL)),
        		c.getLong(c.getColumnIndex(GAME_PLAYER))        		
        		);
        /*
        td.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        td.setNote((c.getString(c.getColumnIndex(KEY_TODO))));
        td.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));
     */
        return obj;
    }     
    
    /**
     * Create an achieved achievement
     * @param achieved
     * @return array of the primary keys
     */
    public long createAchieved(DbAchieved achieved) {
        SQLiteDatabase db = this.getWritableDatabase();
     
        ContentValues values = new ContentValues();
        values.put(ACHIEVED_GAME, achieved.getGameId());
        values.put(ACHIEVED_ACHIEVEMENT, achieved.getAchievementId());
        values.put(ACHIEVED_PLAYER, achieved.getPlayerId());
        
        // insert row
        long id = db.insert(TABLE_ACHIEVED, null, values);
        return id;
    }     
    
    /**
     * Get all achieved by player id    
     * @return
     */
    public List<DbAchieved> getPlayerAchieved(long id) {
        List<DbAchieved> achieveds = new ArrayList<DbAchieved>();
        String selectQuery = "SELECT  * FROM " + TABLE_ACHIEVED + " WHERE player_id=" + id;
     
//        Log.e(LOG, selectQuery);
     
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
     
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                DbAchieved ached = new DbAchieved();
                ached.setPlayerId(id);
                ached.setGameId((c.getLong(c.getColumnIndex(ACHIEVED_GAME))));
                ached.setAchievementId(c.getLong(c.getColumnIndex(ACHIEVED_ACHIEVEMENT)));
     
                // adding to achieved list
                achieveds.add(ached);
            } while (c.moveToNext());
        }
     
        return achieveds;
    }
}