package com.example.dbtest;

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
  
  public DbAchieved()
  {
    
  }
  
  public DbAchieved(long achievementId, long playerId, long gameId)
  {
    setAchievementId(achievementId);
    setPlayerId(playerId);
    setGameId(gameId);
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
}
