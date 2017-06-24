package com.bjss.codetask.pricebasket.service;

import java.util.List;

import com.bjss.codetask.pricebasket.model.offer.Offer;

/**
 * 
 * Offer service
 * 
 * @author Min Li
 *
 */

public interface OfferService {

	/**
	 * 
	 * Get all offers
	 * 
	 * @return
	 */
	List<Offer> getAvailableOffers();
}
