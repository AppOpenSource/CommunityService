package com.askviky.communityservice.bean;

import java.util.LinkedList;
import java.util.List;

public class ShoppingCart {
	
	private boolean isTitle;
	private Shop shop;
	private Product product;
	private boolean isLastProductOfShop;
	private boolean isChecked;
	
	public boolean isTitle() {
		return isTitle;
	}

	public void setTitle(boolean isTitle) {
		this.isTitle = isTitle;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public boolean isLastProductOfShop() {
		return isLastProductOfShop;
	}

	public void setLastProductOfShop(boolean isLastProductOfShop) {
		this.isLastProductOfShop = isLastProductOfShop;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public ShoppingCart(boolean isTitle, Shop shop, Product product,
			boolean isLastProductOfShop) {
		super();
		this.isTitle = isTitle;
		this.shop = shop;
		this.product = product;
		this.isLastProductOfShop = isLastProductOfShop;
	}

	public static List<ShoppingCart> makeSampleList(List<Shop> shopList) {
		List<ShoppingCart> shoppingCartList = new LinkedList<ShoppingCart>();
		for (Shop shop : shopList) {
			ShoppingCart scShop = new ShoppingCart(true, shop, null, false);
			shoppingCartList.add(scShop);
			List<Product> productList = shop.getProductList();
			for (int i=0; i < productList.size(); i++) {
				ShoppingCart scProduct = new ShoppingCart(false, shop, productList.get(i), i==productList.size()-1 ? true : false);
				shoppingCartList.add(scProduct);
			}
		}
		return shoppingCartList;
	}
	
	public static List<ShoppingCart> makeSampleList() {
		List<Shop> shopList = makeSample();
		List<ShoppingCart> shoppingCartList = new LinkedList<ShoppingCart>();
		for (Shop shop : shopList) {
			ShoppingCart scShop = new ShoppingCart(true, shop, null, false);
			shoppingCartList.add(scShop);
			List<Product> productList = shop.getProductList();
			for (int i=0; i < productList.size(); i++) {
				ShoppingCart scProduct = new ShoppingCart(false, shop, productList.get(i), i==productList.size()-1 ? true : false);
				shoppingCartList.add(scProduct);
			}
		}
		return shoppingCartList;
	}

	private static List<Shop> makeSample() {
		List<Shop> shopList = new LinkedList<Shop>();

		return shopList;
	}

	public static int getShopCount(List<ShoppingCart> shoppingCartList) {
		int sectionCount = 0;
		for (ShoppingCart sc : shoppingCartList) {
			if(sc.isTitle()) sectionCount++;
		}
		return sectionCount;
	}

	public static ShoppingCart getShoppingCartParent(ShoppingCart shoppingCart,List<ShoppingCart> shoppingCartList) {
		ShoppingCart shoppingCartTitle = null;
		for (ShoppingCart sc : shoppingCartList) {
			if (sc.isTitle() && sc.getShop().getId() == shoppingCart.getShop().getId()) {
				shoppingCartTitle = sc;
			}
		}
		return shoppingCartTitle;
	}

	@Override
	public String toString() {
		if (isTitle) {
			return "shop: " + shop.getId();
		} else {
			return "product: " + product.getProductId();
		}
	}
}
