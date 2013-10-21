package com.example.dbtest;

/**
 * 
 * @author PaulBM
 * @version 0.1
 */
public class DbAchieved {

  /**
   * the id of the achieved achievement
   */
  private long achivementId;
  
  /**
   * the achieved achievement's owner player id  
   */
  private long playerId;
  
  /**
   * the game id that the achievement was achieved 
   */
  private long gameId;
  
  /**
   * flag to signify that the achieved achievement has been synced with Google Play Store 0=no, 1=yes
   */
  private int googled;
  
  public DbAchieved()
  {
    
  }
  
  public DbAchieved(long achievementId, long playerId, long gameId, int googled)
  {
    setAchievementId(achievementId);
    setPlayerId(playerId);
    setGameId(gameId);
    setGoogled(googled);
  }
  //getters
  
  /**
   * get achievement id
   * @return long
   */
  public long getAchievementId()
  {
    return this.achivementId;
  }
  
  /**
   * get player id
   * @return long
   */
  public long getPlayerId()
  {
    return playerId;
  }
  
  /**
   * get game id
   * @return long
   */
  public long getGameId()
  {
    return this.gameId;
  }
   
  public int getGoogled()
  {
	  return this.googled;
  }
  
  //setters
  
  /**
   * set the achieved achievement id
   * @param id
   */
  public void setAchievementId(long id)
  {
    this.achivementId=id;
  }
  
  /**
   * set the achieving player's id
   * @param id
   */
  public void setPlayerId(long id)
  {
    this.playerId=id;
  }
  
  /**
   * set the achieving games id
   * @param id
   */
  public void setGameId(long id)
  {
    this.gameId=id;
  }
  
  /**
   * set the Google synced flag 1=yes, 0=no
   * @param flag
   */
  public void setGoogled(int flag)
  {
	this.googled=flag;  
  }
}