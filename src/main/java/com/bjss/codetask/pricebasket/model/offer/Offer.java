package com.bjss.codetask.pricebasket.model.offer;

import com.bjss.codetask.pricebasket.model.Discount;
import com.bjss.codetask.pricebasket.model.OrderedItem;
import com.bjss.codetask.pricebasket.utils.PriceUtils.OfferType;

/**
 * 
 * Offer interface
 * 
 * 
 * @author Min Li
 *
 */
public interface Offer {
	/**
	 * 
	 * Pass in an OrderedItem and see if a Discount apply
	 * 
	 * 
	 * @param orderedItem
	 * @return
	 */
	Discount getDiscount(OrderedItem orderedItem);

	/**
	 * Get Offer type
	 * 
	 * @return
	 */
	OfferType getType();

}
