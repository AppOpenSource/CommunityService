package com.askviky.communityservice.db.mysql.bean;

import java.util.Collection;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "cookbook")
public class Cookbook {

	@DatabaseField(generatedId = true)
	private int cookbook_id;
	@DatabaseField
	private String profile;
	@DatabaseField
	private String style;
	@ForeignCollectionField
	private Collection<Menu> menus;
	
	public Cookbook() {

	}

	public Cookbook(int cookbook_id, String profile, String style) {
		this.cookbook_id = cookbook_id;
		this.profile = profile;
		this.style = style;
	}

	public int getCookbook_id() {
		return cookbook_id;
	}

	public void setCookbook_id(int cookbook_id) {
		this.cookbook_id = cookbook_id;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}
	
	public Collection<Menu> getMenus() {
		return menus;
	}

	public void setStyle(Collection<Menu> menus) {
		this.menus = menus;
	}

	@Override
	public String toString() {
		return "Cookbook [cookbook_id=" + cookbook_id + ", profile=" + profile
				+ ", style=" + style + "]";
	}
}
