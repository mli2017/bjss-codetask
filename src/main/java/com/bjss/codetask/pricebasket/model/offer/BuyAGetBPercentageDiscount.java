package com.bjss.codetask.pricebasket.model.offer;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.bjss.codetask.pricebasket.model.Discount;
import com.bjss.codetask.pricebasket.model.OrderedItem;
import com.bjss.codetask.pricebasket.model.Discount.DiscountType;

import java.util.Set;

/**
 * 
 * This offer is to implement in requirement as 'Buy 2 tins of soup and get a
 * loaf of bread for half price '. However, it is not fully comply as what this
 * implementation does is as long as some one bought 2 tins of soup, all bread
 * will apply the 50% discount.
 * 
 * Although it is possible, to fully comply this requirement, the algorithm will
 * be more complex, it will require a discount type take account in Percentage
 * and Quantity together.
 * 
 * As this is for Protype/Demo purpose only, I decide not to go into that.
 * 
 * @author Min Li
 *
 */
public class BuyAGetBPercentageDiscount extends AbstractOffer {

	private Map<String, String> matchProducts;
	private int quantityRequired;
	private int targetPercentDiscount;

	public BuyAGetBPercentageDiscount(int typeId, Date startDate, Date endDate, int quantityRequired,
			int targetPercentDiscount, Map<String, String> matchProducts) {
		super(typeId, startDate, endDate);
		this.quantityRequired = quantityRequired;
		this.targetPercentDiscount = targetPercentDiscount;
		this.matchProducts = new HashMap<>(matchProducts);
	}

	Discount getDiscountInner(OrderedItem orderItem) {
		Set<String> discountItemNames = matchProducts.keySet();

		String productName = orderItem.getName();

		if (discountItemNames.contains(productName) && orderItem.getQuantity() >= quantityRequired) {
			String targetProductName = matchProducts.get(productName);
			return new Discount(targetProductName, getOfferName(productName, targetProductName), targetPercentDiscount,
					DiscountType.PERCENTAGE);
		}
		return null;
	}

	private String getOfferName(String srcItem, String targetItem) {
		StringBuilder sb = new StringBuilder();

		sb.append(" Buy " + quantityRequired);
		sb.append(" " + srcItem);
		sb.append(" Get " + targetPercentDiscount + "% Off");
		sb.append(" On " + targetItem);

		return sb.toString();
	}

	@Override
	public String toString() {
		return super.toString() + " [matchProducts=" + matchProducts + ", quantityRequired=" + quantityRequired
				+ ", targetPercentDiscount=" + targetPercentDiscount + "]";
	}

}
