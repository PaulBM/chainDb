package com.example.dbtest;

/**
 * Class for the Achievements database table
 * 
 * @author PaulBM
 * @version 0.2
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

	/**
	 * achievement difficulty level : 0 any diffculty, 1 Easy, 2 Medium, 3 Hard
	 */
	private int ach_difficulty;

	/**
	 * achievement hidden flag, 1 = hidden
	 */
	private int ach_hidden;

	public DbAchievement() {

	}
/*
	public DbAchievement(String name, String description, int difficulty, int hidden) {
		if (name != null && description != null) {
			setName(name);
			setAchDesc(description);
			setAchDiff(difficulty);
			setAchHidden(hidden);
		}
	}
*/
	public DbAchievement(long id, String name, String description, int difficulty, int hidden) {
		if (id > 0 && name != null && description != null) {
			setId(id);
			setName(name);
			setAchDesc(description);
			setAchDiff(difficulty);
			setAchHidden(hidden);
		}
	}

	public String toString() {
		return "Achievement : ID='" + this.ach_id + "' Name='" + this.ach_name
				+ "' Desc='" + this.ach_description + "' Diff="
				+ this.ach_difficulty + " Hidden=" + this.ach_hidden;
	}

	// getters

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

	/**
	 * Returns the achievement difficulty
	 * 
	 * @return int
	 */
	public int getAchDiff() {
		return ach_difficulty;
	}

	/**
	 * Returns the hidden flag
	 * 
	 * @return int
	 */
	public int getAchHidden() {
		return ach_hidden;
	}

	// setters

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

	/**
	 * Set the difficulty level
	 * 
	 * @param diff
	 */
	public void setAchDiff(int diff) {
		this.ach_difficulty = diff;
	}
	
	/**
	 * Set the hidden flag for this achievement
	 * 
	 * @param hidden
	 */
	public void setAchHidden(int hidden) {
		this.ach_hidden = hidden;
	}

}
