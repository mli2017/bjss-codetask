package com.bjss.codetask.pricebasket.model;

/**
 * 
 * An offer definition read from tsv loading file
 * 
 * @author Min Li
 * 
 */

public class OfferDefinition {
	
	private int type;
	private int value1;
	private int value2;
	
	private String applyProducts;
	
	private String startDate;
	private String endDate;
	
	
	public OfferDefinition(int type, int value1, int value2, String applyProducts,
			String startDate,
			String endDate) {
		super();
		this.type = type;
		this.value1 = value1;
		this.value2 = value2;
		this.applyProducts = applyProducts;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public int getType() {
		return type;
	}

	public int getValue1() {
		return value1;
	}

	public int getValue2() {
		return value2;
	}

	public String getApplyProducts() {
		return applyProducts;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	@Override
	public String toString() {
		return "OfferDefinition [type=" + type + ", value1=" + value1 + ", value2=" + value2 + ", applyProducts="
				+ applyProducts + ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}

	
	

}
