package com.bjss.codetask.pricebasket.service;

import static com.bjss.codetask.pricebasket.utils.PriceUtils.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.bjss.codetask.pricebasket.model.Discount;
import com.bjss.codetask.pricebasket.model.OrderedItem;
import com.bjss.codetask.pricebasket.model.PricedItem;
import com.bjss.codetask.pricebasket.model.ShoppingBasket;
import com.bjss.codetask.pricebasket.model.Discount.DiscountType;
import com.bjss.codetask.pricebasket.model.offer.Offer;

/**
 * 
 * Price engine implementation
 * 
 * @author Min Li
 *
 */

public class PriceEngineImpl implements PriceEngine {
	private OfferService offerService;
	private PriceService priceService;

	public PriceEngineImpl(OfferService offerService, PriceService priceService) {
		super();
		this.offerService = offerService;
		this.priceService = priceService;
	}

	@Override
	public ShoppingBasket gePricedBasket(List<OrderedItem> orderedItems) {
		List<Discount> discounts = getAppliedDiscounts(orderedItems);
		Map<String, List<Discount>> discountsByProduct = groupDiscountsByName(discounts);

		List<PricedItem> pricedItems = orderedItems.stream().map(orderItem -> price(orderItem, discountsByProduct))
				.collect(Collectors.toList());

		return new ShoppingBasket(pricedItems);

	}

	/**
	 * 
	 * Find all discounts apply to the list of items
	 * 
	 * @param orderedItems
	 * @return
	 */
	private List<Discount> getAppliedDiscounts(List<OrderedItem> orderedItems) {

		return orderedItems.stream().map(oi -> applyOffers(oi)).flatMap(l -> l.stream()).collect(Collectors.toList());

	}

	/**
	 * 
	 * Find discount apply to single item. Multiple discount apply to same item
	 * supported.
	 * 
	 * @param orderedItem
	 * @return
	 */
	private List<Discount> applyOffers(OrderedItem orderedItem) {
		List<Offer> offers = offerService.getAvailableOffers();
		List<Discount> discounts = new ArrayList<>();

		for (Offer offer : offers) {
			Discount discount = offer.getDiscount(orderedItem);

			if (discount != null)
				discounts.add(discount);
		}

		return discounts;

	}

	/**
	 * 
	 * Group discounts by product
	 * 
	 * @param discounts
	 * @return
	 */
	private Map<String, List<Discount>> groupDiscountsByName(List<Discount> discounts) {

		return discounts.stream().collect(Collectors.groupingBy(Discount::getProductName));

	}

	/**
	 * 
	 * Calculate subtotal after discounts applied.
	 * 
	 * Here assume discount is accumulated, offers can apply on top of another.
	 * 
	 * @param orderedItem
	 * @param discountsByProduct
	 * @return
	 */
	private PricedItem price(OrderedItem orderedItem, Map<String, List<Discount>> discountsByProduct) {
		String productName = orderedItem.getName();
		int quanity = orderedItem.getQuantity();
		float originPrice = priceService.getPrice(orderedItem.getName());

		List<Discount> discounts = discountsByProduct.get(productName);

		float targetPrice = originPrice;

		int minusQuantity = 0;

		StringBuilder discountDetail = new StringBuilder();

		if (discounts != null && discounts.size() > 0) {
			for (Discount discount : discounts) {
				if (discount.getDiscountType().equals(DiscountType.PERCENTAGE)) {
					targetPrice = targetPrice * (100 - discount.getDiscountValue()) / 100.00F;
				} else {
					minusQuantity = minusQuantity + discount.getDiscountValue();
				}
				discountDetail.append(padding(discount.toString()) + LINE_SEPARATOR);

			}
		}

		double totalToPay = targetPrice * (quanity - minusQuantity);

		return new PricedItem(orderedItem, originPrice, totalToPay, discountDetail.toString());

	}

	public void setOfferService(OfferService offerService) {
		this.offerService = offerService;
	}

	public void setPriceService(PriceService priceService) {
		this.priceService = priceService;
	}
}
