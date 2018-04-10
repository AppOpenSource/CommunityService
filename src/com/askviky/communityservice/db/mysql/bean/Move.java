package com.askviky.communityservice.db.mysql.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "move")
public class Move {

	@DatabaseField(generatedId = true)
	private int move_id;
	@DatabaseField
	private String profile;
	@DatabaseField
	private String image;
    @DatabaseField(canBeNull = true, foreign = true, columnName = "dish_id")  
    private Dish dish;  
	
	public Move() {

	}

	public int getMove_id() {
		return move_id;
	}

	public void setMove_id(int move_id) {
		this.move_id = move_id;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	public Dish getDish() {
		return dish;
	}

	public void setDish(Dish dish) {
		this.dish = dish;
	}

	@Override
	public String toString() {
		return "Move [move_id=" + move_id + ", profile=" + profile + ", image="
				+ image + ", dish=" + dish + "]";
	}
	
}
