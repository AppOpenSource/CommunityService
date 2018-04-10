package com.askviky.communityservice.db.mysql.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "material")
public class Material {

	@DatabaseField(generatedId = true)
	private int material_id;
	@DatabaseField
	private String name;
	@DatabaseField
	private String weight;
	@DatabaseField
	private double price;
	@DatabaseField(canBeNull = true, foreign = true, columnName = "dish_id")
	private Dish dish;
	@DatabaseField
	private String image_name;

	public Material() {

	}

	public int getMaterial_id() {
		return material_id;
	}

	public void setMaterial_id(int material_id) {
		this.material_id = material_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Dish getDish() {
		return dish;
	}

	public void setDish(Dish dish) {
		this.dish = dish;
	}

	public String getImageName() {
		return image_name;
	}

	public void setImageName(String image_name) {
		this.image_name = image_name;
	}

	@Override
	public String toString() {
		return "Material [material_id=" + material_id + ", name=" + name
				+ ", weight=" + weight + ", price=" + price + ", dish=" + dish
				+ ", image_name=" + image_name + "]";
	}

}
