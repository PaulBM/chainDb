package com.example.dbtest;

/**
 * 
 * @author PaulBM
 * @version 0.1
 */
public class DbGame {
  /**
   * game id primary key
   */
  private long game_id;

  /**
   * game start time
   */
  private String game_start_time;
  /**
   * game end time
   */
  private String game_end_time;
  /**
   * game duration
   */
  private long game_duration;
  
  /**
   * game score
   */
  private int game_score;
  
  /**
   * game difficulty
   */
  private int game_difficulty;
  
  /**
   * game level
   */
  private int game_level;
  
  /**
   * game player
   */
  private long game_playerId;
  
  
  public DbGame() {

  }

  public DbGame(String start, String end, long duration, int score, int difficulty, int level, long playerId)
  {
     setStart(start);
     setEnd(end);
     setDuration(duration);
     setScore(score);
     setDifficulty(difficulty);
     setLevel(level);
     setPlayerId(playerId);
  }

  public DbGame(long id, String start, String end, long duration, int score, int difficulty, int level, long playerId)
  {
    setId(id);
    setStart(start);
    setEnd(end);
    setDuration(duration);
    setScore(score);
    setDifficulty(difficulty);
    setLevel(level);
    setPlayerId(playerId);

  }

  // getters

  /**
   * Returns the id of the game
   * 
   * @return long game_id
   */
  public long getId() {
    return game_id;
  }

  /**
   * Returns the start time
   * 
   * @return Date
   */
  public String getStart() {
    return game_start_time;
  }

  /**
   * Returns the end time
   * 
   * @return Date
   */
  public String getEnd() {
    return game_end_time;
  }
  
  /**
   * Returns the game duration
   * 
   * @return long
   */
  public long getDuration() {
    return game_duration;
  }
  
  /**
   * Returns the game score
   * 
   * @return int
   */
  public int getScore() {
    return game_score;
  }
  
  /**
   * Returns the game difficulty
   * 
   * @return int
   */
  public int getDifficulty() {
    return game_difficulty;
  }

  /**
   * Returns the game level
   * 
   * @return int
   */
  public int getLevel() {
    return game_level;
  }
  
  /**
   * Returns the game player id
   * 
   * @return long
   */
  public long getPlayerId() {
    return game_playerId;
  }
  
  
  // setters

  /**
   * sets the id of the game
   */
  public void setId(long id)
  {
    this.game_id=id;
  }

  /**
   * sets the game start time
   */
  public void setStart(String start) {
    this.game_start_time=start;
  }

  /**
   * sets the game end time
   * 
   */
  public void setEnd(String end) {
    this.game_end_time=end;
  }
  
  /**
   * sets the game duration
   */
  public void setDuration(long duration) {
    this.game_duration=duration;
  }
  
  /**
   * sets the game score
   */
  public void setScore(int score) {
    this.game_score=score;
  }
  
  /**
   * sets the game difficulty
   */
  public void setDifficulty(int difficulty) {
    this.game_difficulty=difficulty;
  }

  /**
   * sets the game level
   */
  public void setLevel(int level) {
    this.game_level=level;
  }
  
  /**
   * sets the game player id
   */
  public void setPlayerId(long id) {
    this.game_playerId=id;
  }
}
