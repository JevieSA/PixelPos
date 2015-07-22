package com.icydevs.pixelpos;

public class ProductItem {

	private String description;
	private double priceBuy;
	private double priceSell;
	private int quantity;
	private String category;
	private String supplier;
	private String id;

	public ProductItem(String id, String name, double priceBuy, double priceSell,
			int quantity, String category, String supplier) {
		super();
		this.description = name;
		this.priceBuy = priceBuy;
		this.priceSell = priceSell;
		this.quantity = quantity;
		this.category = category;
		this.supplier = supplier;
		this.id = id;
	}

	public ProductItem(int quant, String name, double priceSell)
	{
		super();
		this.quantity = quant;
		this.description = name;
		this.priceSell = priceSell;
	}
	
	@Override
	public String toString() {
		return id + " " + description + " " + priceBuy + " " + priceSell + " "
				+ quantity + " " + category + " " + supplier + "\n";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPriceBuy() {
		return priceBuy;
	}

	public void setPriceBuy(double priceBuy) {
		this.priceBuy = priceBuy;
	}

	public double getPriceSell() {
		return priceSell;
	}

	public void setPriceSell(double priceSell) {
		this.priceSell = priceSell;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

}
