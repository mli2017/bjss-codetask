package com.bjss.codetask.pricebasket.model;

import static com.bjss.codetask.pricebasket.utils.PriceUtils.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * A priced basket.
 * 
 * @author Min Li
 *
 */

public class ShoppingBasket {
	private List<PricedItem> pricedItems;
	private double originalTotal;
	private double subTotal;

	public ShoppingBasket(List<PricedItem> pricedItems) {
		this.pricedItems = new ArrayList<>(pricedItems);
		this.subTotal = caculateSubTotal(pricedItems);
		this.originalTotal = caculateOriginTotal(pricedItems);
	}

	private double caculateSubTotal(List<PricedItem> pricedItems) {
		return pricedItems.stream().mapToDouble(it -> it.getSubTotal()).sum();
	}

	private double caculateOriginTotal(List<PricedItem> pricedItems) {
		return pricedItems.stream().mapToDouble(it -> it.getOriginTotal()).sum();
	}

	public double getOriginalTotal() {
		return originalTotal;
	}

	public double getSubTotal() {
		return subTotal;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(LINE_SEPARATOR);
		for (PricedItem pricedItem : pricedItems)
			sb.append(pricedItem);
		sb.append(repeat(OUTPUT_SEPARATOR, LINE_TEXT_LENGTH));
		sb.append(LINE_SEPARATOR);
		sb.append(padding("Total:" + formatOutput(originalTotal)));
		sb.append(LINE_SEPARATOR);
		sb.append(padding("You pay:" + formatOutput(subTotal)));
		sb.append(LINE_SEPARATOR);

		sb.append(padding("Save:" + formatOutput(originalTotal - subTotal)));

		return sb.toString();
	}

}
