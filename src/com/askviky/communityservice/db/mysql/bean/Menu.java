package com.askviky.communityservice.db.mysql.bean;

import java.util.Collection;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "menu")
public class Menu {

	@DatabaseField(generatedId = true)
	private int menu_id;
	@DatabaseField
	private String name;
	@DatabaseField
	private String people;
	@DatabaseField
	private String price;
	@DatabaseField
	private String style;
	@DatabaseField
	private String avoid;
	@ForeignCollectionField
	private Collection<Dish> dishs;
    @DatabaseField(canBeNull = true, foreign = true, columnName = "cookbook_id")  
    private Cookbook cookbook;  

	public Menu() {

	}

	public int getMenu_id() {
		return menu_id;
	}

	public void setMenu_id(int menu_id) {
		this.menu_id = menu_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPeople() {
		return people;
	}

	public void setPeople(String people) {
		this.people = people;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getAvoid() {
		return avoid;
	}

	public void setAvoid(String avoid) {
		this.avoid = avoid;
	}

	public Cookbook getCookbook() {
		return cookbook;
	}

	public void setCookbook(Cookbook cookbook) {
		this.cookbook = cookbook;
	}

	@Override
	public String toString() {
		return "Menu [menu_id=" + menu_id + ", name=" + name + ", people="
				+ people + ", price=" + price + ", style=" + style + ", avoid="
				+ avoid + ", dish_1=" + ", cookbook=" + cookbook + "]";
	}

}
