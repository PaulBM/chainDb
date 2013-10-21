package com.example.dbtest;

/**
 * Class for the Achievements database table
 * 
 * @author PaulBM
 * 2013
 */
public class DbAchievement {

  /**
   * achievement id
   */
  private long ach_id;
  /**
   * achievement name
   */
  private String ach_name;
  /**
   * achievement description
   */
  private String ach_description;

  public DbAchievement() {

  }

  public DbAchievement(String name, String description) {
    if (name != null && description != null) {
      setName(name);
      setAchDesc(description);
    }
  }

  public DbAchievement(long id, String name, String description) {
    if (id > 0 && name != null && description != null) {
      setId(id);
      setName(name);
      setAchDesc(description);
    }
  }

  //getters
  
  /**
   * Returns the id of the achievement
   * 
   * @return long ach_id
   */
  public long getId() {
    return ach_id;
  }

  /**
   * Returns the name of the achievement
   * 
   * @return String
   */
  public String getName() {
    return ach_name;
  }

  /**
   * Returns the achievement description
   * 
   * @return String
   */
  public String getAchDesc() {
    return ach_description;
  }
  
  //setters

  /**
   * Sets the id of the achievement
   * 
   * @param long id
   */
  public void setId(long id) {
    this.ach_id = id;
  }
  
  /**
   * Set the achievement name
   * 
   * @param name
   */
  public void setName(String name) {
    if (name != null) {
      this.ach_name = name;
    }
  }

  /**
   * Sets the achievement description
   * 
   * @param desc
   */
  public void setAchDesc(String desc) {
    if (desc != null) {
      this.ach_description = desc;
    }
  }
}
