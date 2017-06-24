package com.bjss.codetask.pricebasket.exceptions;

/**
 * Offer Type not found exception
 * 
 * @author Min Li
 */
public class OfferTypeNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public OfferTypeNotFoundException(int offerType) {
		super("This offer type does not exist: " + offerType);
	}
}
