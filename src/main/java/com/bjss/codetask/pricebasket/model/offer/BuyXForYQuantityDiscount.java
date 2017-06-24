package com.bjss.codetask.pricebasket.model.offer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bjss.codetask.pricebasket.model.Discount;
import com.bjss.codetask.pricebasket.model.OrderedItem;
import com.bjss.codetask.pricebasket.model.Discount.DiscountType;

/**
 * 
 * This offer is to implement what we often see in the shop of the 'Buy 2 Get 1
 * Free' type offer, by change the initialise parameter, you can easily create a
 * 'Buy 3 For Price of 2' type of offer.
 * 
 * This is not in the requirement, I created just to show the extensibility of
 * this design.
 * 
 * 
 * 
 * @author Min Li
 *
 */

public class BuyXForYQuantityDiscount extends AbstractOffer {

	private List<String> names;
	private int srcQuantity;
	private int targetQuantity;

	public BuyXForYQuantityDiscount(int typeId, Date startDate, Date endDate,int srcQuantity, int targetQuantity, List<String> names) {
		super(typeId, startDate, endDate);
		this.srcQuantity = srcQuantity;
		this.targetQuantity = targetQuantity;
		this.names = new ArrayList<>(names);
	}

	Discount getDiscountInner(OrderedItem orderItem) {
		if (names.contains(orderItem.getName())) {
			int quantityOff = getQuantityOff(orderItem);
			if (quantityOff > 0)
				return new Discount(orderItem.getName(), getOfferName(), quantityOff, DiscountType.QUANTITY);
		}
		return null;
	}

	private int getQuantityOff(OrderedItem shoppingItem) {

		if (shoppingItem.getQuantity() >= srcQuantity) {
			int newQuantity = shoppingItem.getQuantity() / srcQuantity * targetQuantity;

			return shoppingItem.getQuantity() - newQuantity;
		}

		return 0;
	}

	private String getOfferName() {

		return "Buy " + srcQuantity + " For Price of " + targetQuantity;
	}

	@Override
	public String toString() {
		return super.toString() + "[names=" + names + ", srcQuantity=" + srcQuantity + ", targetQuantity="
				+ targetQuantity + "]";
	}

}
