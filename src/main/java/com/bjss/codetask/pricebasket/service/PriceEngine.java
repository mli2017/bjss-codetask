package com.bjss.codetask.pricebasket.service;

import java.util.List;

import com.bjss.codetask.pricebasket.model.OrderedItem;
import com.bjss.codetask.pricebasket.model.ShoppingBasket;

/**
 * 
 * Price engine, depend on OfferService and PriceService
 * 
 * @author Min Li
 *
 */

public interface PriceEngine {

	/**
	 * Price a list of unpriced items
	 * 
	 * @param orderedItems
	 * @return
	 */
	ShoppingBasket gePricedBasket(List<OrderedItem> orderedItems);

	/**
	 * 
	 * Point to an offerService 
	 * 
	 * @param offerService
	 */
	void setOfferService(OfferService offerService);
	
	/**
	 * 
	 * Point to a Price service
	 * 
	 * @param priceService
	 */

	void setPriceService(PriceService priceService);

}
