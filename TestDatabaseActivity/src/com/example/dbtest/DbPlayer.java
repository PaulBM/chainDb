package com.example.dbtest;

/**
 * 
 * @author PaulBM
 * @version 0.1
 */
public class DbPlayer {
  /**
   * player id primary key
   */
  private long player_id;

  /**
   * player name
   */
  private String player_name;

  public DbPlayer() {

  }

  public DbPlayer(String name) {
    if (name != null) {
      setName(name);
    }
  }

  public DbPlayer(long id, String name) {
    if (id > 0 && name != null) {
      setId(id);
      setName(name);
    }
  }

  //getters
  
  /**
   * Returns the id of the player
   * 
   * @return long player_id
   */
  public long getId() {
    return player_id;
  }

  /**
   * Returns the name of the player
   * 
   * @return String
   */
  public String getName() {
    return player_name;
  }

  // setters
  
  /**
   * Sets the id of the player
   * 
   * @param long id
   */
  public void setId(long id) {
    this.player_id = id;
  }


  /**
   * Set the player name
   * 
   * @param name
   */
  public void setName(String name) {
    if (name != null) {
      this.player_name = name;
    }
  }
}
