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
    private static final String TABLE_UNLOCKED = "unlocked";

    // Achievements Table - column names
    private static final String ACH_ID = "ach_id";
    private static final String ACH_NAME = "ach_name";
    private static final String ACH_DESC = "ach_description";
    private static final String ACH_DIFF = "ach_difficulty";
    
     
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
    private static final String UNLOCKED_ACHIEVEMENT ="achievement";
    private static final String UNLOCKED_PLAYER ="player";
    private static final String UNLOCKED_GAME ="game";
    private static final String UNLOCKED_GOOGLED ="googled";
    private static final String UNLOCKED_DATETIME ="datetimeAch";
    
    // Table Create Statements
    // Achievements table create statement
    private static final String CREATE_TABLE_ACHIEVEMENTS = "CREATE TABLE " + TABLE_ACHIEVEMENTS + "(" 
    + ACH_ID + " INTEGER PRIMARY KEY,"
    + ACH_NAME + " TEXT,"
    + ACH_DESC + " TEXT,"
    + ACH_DIFF + " INTEGER"
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
    private static final String CREATE_TABLE_UNLOCKED = "CREATE TABLE " + TABLE_UNLOCKED + "("
    + UNLOCKED_ACHIEVEMENT + " INTEGER NOT NULL PRIMARY KEY,"
    + UNLOCKED_PLAYER + " INTEGER NOT NULL PRIMARY KEY,"
    + UNLOCKED_GAME + " INTEGER NOT NULL PRIMARY KEY,"
    + UNLOCKED_GOOGLED + " INTEGER"
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
        db.execSQL(CREATE_TABLE_UNLOCKED);
    }    

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACHIEVEMENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAMES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UNLOCKED);
 
        // create new tables
        onCreate(db);
    }
    
    //****************** Achievements ************************************
    
    /**
     * Insert an achievement into database
     * @param achievement
     * @return id of new achievement record or error code from SQLite
     */
    public long insertAchievement(DbAchievement achievement) {
        SQLiteDatabase db = this.getWritableDatabase();
     
        ContentValues values = new ContentValues();
        values.put(ACH_NAME, achievement.getName());
        values.put(ACH_DESC, achievement.getAchDesc());
        values.put(ACH_DIFF, achievement.getAchDiff());
     
        // insert row
        long id = db.insert(TABLE_ACHIEVEMENTS, null, values);
        return id;
    }
    
    /**
     * Read an achievement from the database
     * @param id
     * @return DbAchievement object
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
        		c.getString(c.getColumnIndex(ACH_DESC)),
        		c.getInt(c.getColumnIndex(ACH_DIFF)));

        return obj;
    }    

    /**
     * Get all achievements    
     * @return List of DbAchievement objects
     */
    public List<DbAchievement> getAllAchievements() {
        List<DbAchievement> achs = new ArrayList<DbAchievement>();
        String selectQuery = "SELECT  * FROM " + TABLE_ACHIEVEMENTS ;
     
//        Log.e(LOG, selectQuery);
     
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
     
                // adding to achieved list
                achs.add(ach);
            } while (c.moveToNext());
        }
     
        return achs;
    }    
    
    //****************** Players **********************************
    
    /**
     * Insert player into database
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
     * @param id
     * @return a player object
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

        return obj;
    }   
    
    /**
     * Update a player in the database 
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
    
    
    
// ***************** Games **********************************    
    
    /**
     * Insert a new game database entry
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

        return obj;
    }     
    
    // *********** Unlocked achievements *********************
    
    /**
     * Insert an unlocked achievement into database
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
     * @return List of DbUnlocked objects
     */
    public List<DbUnlocked> getPlayerUnlocked(long id) {
        List<DbUnlocked> unlockeds = new ArrayList<DbUnlocked>();
        String selectQuery = "SELECT  * FROM " + TABLE_UNLOCKED + " WHERE player_id=" + id;
     
//        Log.e(LOG, selectQuery);
     
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
     
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                DbUnlocked unlock = new DbUnlocked();
                unlock.setPlayerId(id);
                unlock.setGameId((c.getLong(c.getColumnIndex(UNLOCKED_GAME))));
                unlock.setAchievementId(c.getLong(c.getColumnIndex(UNLOCKED_ACHIEVEMENT)));
                unlock.setGoogled(c.getInt(c.getColumnIndex(UNLOCKED_GOOGLED)));
                unlock.setDatetime(c.getString(c.getColumnIndex(UNLOCKED_DATETIME)));
     
                // adding to unlocked list
                unlockeds.add(unlock);
            } while (c.moveToNext());
        }
     
        return unlockeds;
    }
}