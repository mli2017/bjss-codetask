package com.bjss.codetask.pricebasket.model.offer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bjss.codetask.pricebasket.model.Discount;
import com.bjss.codetask.pricebasket.model.OrderedItem;
import com.bjss.codetask.pricebasket.model.Discount.DiscountType;

import static com.bjss.codetask.pricebasket.utils.PriceUtils.*;

/**
 * 
 * This offer is to implement in requirement as 'Apples have a 10% discount off
 * their normal price this week'. by change the initialise parameter, you can
 * easily create same Offer Type for different products at different discount
 * percentage;
 * 
 * @author Min Li
 *
 */
public class PercentageDiscount extends AbstractOffer {

	private int discountPercent;
	private List<String> names;

	public PercentageDiscount(int typeId, Date startDate, Date endDate, int discountPercent, List<String> names) {
		super(typeId, startDate, endDate);
		this.discountPercent = discountPercent;
		this.names = new ArrayList<>(names);
	}

	Discount getDiscountInner(OrderedItem orderItem) {
		String productName = orderItem.getName();

		if (names.contains(productName)) {
			return new Discount(productName, getOfferName(productName), discountPercent, DiscountType.PERCENTAGE);
		}
		return null;
	}

	private String getOfferName(String name) {

		return name + " is " + discountPercent + "% Off";
	}

	@Override
	public String toString() {
		return super.toString() + "[names=" + mkString(names) + ", discountPercent=" + discountPercent + "]";
	}
}
