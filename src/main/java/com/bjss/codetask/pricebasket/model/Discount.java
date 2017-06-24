package com.bjss.codetask.pricebasket.model;

/**
 * 
 * Model a Discount, can either based on Percentage or Quantity but not both
 * 
 * 
 * @author Min Li
 *
 */
public class Discount {
	public static enum DiscountType {
		PERCENTAGE, QUANTITY
	}

	private String productName;

	private String offerDetail;
	private int discountValue;
	private DiscountType discountType;

	public Discount(String itemName, String offerName, int discountValue, DiscountType discountType) {
		this.productName = itemName;
		this.offerDetail = offerName;
		this.discountValue = discountValue;
		this.discountType = discountType;
	}

	public String getProductName() {
		return productName;
	}

	@Override
	public String toString() {
		return offerDetail;
	}

	public int getDiscountValue() {
		return discountValue;
	}

	public DiscountType getDiscountType() {
		return discountType;
	}

}
