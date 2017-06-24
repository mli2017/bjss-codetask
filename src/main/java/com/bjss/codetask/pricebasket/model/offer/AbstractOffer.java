package com.bjss.codetask.pricebasket.model.offer;

import java.util.Date;

import com.bjss.codetask.pricebasket.model.Discount;
import com.bjss.codetask.pricebasket.model.OrderedItem;
import com.bjss.codetask.pricebasket.utils.PriceUtils.OfferType;

/**
 * Base class for all offers.
 * 
 * Function common to offer can be extended here in future
 * 
 * @author Min Li
 *
 */

public abstract class AbstractOffer implements Offer {
	private int typeId;
	private Date startDate;
	private Date endDate;

	public AbstractOffer(int typeId, Date startDate, Date endDate) {
		this.typeId = typeId;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	abstract Discount getDiscountInner(OrderedItem orderItem);

	public Discount getDiscount(OrderedItem orderItem) {
		Date now = new Date();

		if (now.after(startDate) && now.before(endDate)) {
			return getDiscountInner(orderItem);
		} else
			return null;

	}

	public OfferType getType() {
		return OfferType.valueOf(typeId);
	}

	@Override
	public String toString() {
		return "Offer [" + getType() + ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}

}
