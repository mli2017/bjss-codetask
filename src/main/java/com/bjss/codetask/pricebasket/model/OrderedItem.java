package com.bjss.codetask.pricebasket.model;

import static com.bjss.codetask.pricebasket.utils.PriceUtils.TAB_SEPARATOR;

/**
 * 
 * An non-priced product item in the basket
 * 
 * @author Min Li
 *
 */

public class OrderedItem {

	final String name;
	final int quantity;

	public OrderedItem(String name, int quantity) {
		super();
		this.name = name;
		this.quantity = quantity;
	}

	public String getName() {
		return name;
	}

	public int getQuantity() {
		return quantity;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(name);
		sb.append(TAB_SEPARATOR + quantity);

		return sb.toString();
	}

}
