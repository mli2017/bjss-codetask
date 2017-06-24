package com.bjss.codetask.pricebasket.model;

import static com.bjss.codetask.pricebasket.utils.PriceUtils.LINE_SEPARATOR;
import static com.bjss.codetask.pricebasket.utils.PriceUtils.TAB_SEPARATOR;
import static com.bjss.codetask.pricebasket.utils.PriceUtils.*;

/**
 * 
 * A priced product item
 * 
 * @author Min Li
 *
 */
public class PricedItem extends OrderedItem {
	private final float price;
	private final String offers;
	private final double subTotal;

	public PricedItem(OrderedItem orderedItem, float price, double subTotal, String offers) {
		super(orderedItem.getName(), orderedItem.getQuantity());
		this.price = price;
		this.offers = offers;
		this.subTotal = subTotal;

	}

	public float getPrice() {
		return price;
	}

	public String getOffers() {
		return offers;
	}

	public double getSubTotal() {
		return subTotal;
	}

	public double getOriginTotal() {
		return getQuantity() * price;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(name);
		sb.append(TAB_SEPARATOR + formatOutput(price));
		sb.append(TAB_SEPARATOR + quantity);
		sb.append(repeat(TAB_SEPARATOR, 2) + formatOutput(getOriginTotal()));
		sb.append(LINE_SEPARATOR);

		if (offers != null && !offers.isEmpty()) {
			sb.append(offers);
			sb.append(repeat(TAB_SEPARATOR, 4) + formatOutput(subTotal));
			sb.append(LINE_SEPARATOR);
		}

		return sb.toString();
	}

}
