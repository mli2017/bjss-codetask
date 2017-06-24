package com.bjss.codetask.pricebasket.service;

/**
 * 
 * Price service
 * 
 * @author Min Li
 *
 */
public interface PriceService {

	/**
	 * Get price for given product name
	 * 
	 * @param name
	 * @return
	 */
	float getPrice(String name);
}
