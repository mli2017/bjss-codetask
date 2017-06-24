package com.bjss.codetask.pricebasket;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.bjss.codetask.pricebasket.model.ShoppingBasket;
import com.bjss.codetask.pricebasket.model.offer.BuyAGetBPercentageDiscount;
import com.bjss.codetask.pricebasket.model.offer.BuyXForYQuantityDiscount;
import com.bjss.codetask.pricebasket.model.offer.Offer;
import com.bjss.codetask.pricebasket.model.offer.PercentageDiscount;
import com.bjss.codetask.pricebasket.service.OfferService;
import com.bjss.codetask.pricebasket.service.PriceEngine;
import com.bjss.codetask.pricebasket.service.PriceEngineImpl;
import com.bjss.codetask.pricebasket.service.PriceService;

import static com.bjss.codetask.pricebasket.utils.PriceUtils.getOrderedItems;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Unit test for simple App.
 */
public class AppTest {

	@Mock
	private OfferService offerService;

	@Mock
	private PriceService priceService;

	private PriceEngine priceEngine;

	private Date startDate;
	private Date endDate;

	@Before
	public void setUp() {
		/*
		 * Soup 0.65 Bread 0.80 Milk 1.30 Apples 1.00
		 */

		priceService = mock(PriceService.class);

		when(priceService.getPrice("Soup")).thenReturn(0.65F);
		when(priceService.getPrice("Bread")).thenReturn(0.80F);
		when(priceService.getPrice("Milk")).thenReturn(1.30F);
		when(priceService.getPrice("Apples")).thenReturn(1.00F);

		offerService = mock(OfferService.class);

		priceEngine = new PriceEngineImpl(offerService, priceService);

		Calendar cal = Calendar.getInstance();
		// yesterday
		cal.add(Calendar.DATE, -1);
		startDate = cal.getTime();

		// tomorrow
		cal.add(Calendar.DATE, 2);
		endDate = cal.getTime();

	}

	@Test
	public void testWhenNoOffers() {

		when(offerService.getAvailableOffers()).thenReturn(new ArrayList<Offer>());

		String[] items = { "Apples:1", "Soup:2", "Bread:1", "Milk:2" };

		ShoppingBasket basket = priceEngine.gePricedBasket(getOrderedItems(items));

		assertEquals(basket.getOriginalTotal(), basket.getSubTotal(), 0.01);
		assertEquals(basket.getOriginalTotal(), 5.70, 0.1);

	}

	@Test
	public void testWhenOnePercentOffer() {

		String[] products = { "Apples" };

		Offer[] offers = new Offer[1];
		offers[0] = new PercentageDiscount(1, startDate, endDate, 10, Arrays.asList(products));

		when(offerService.getAvailableOffers()).thenReturn(Arrays.asList(offers));

		String[] items = { "Apples:1", "Bread:1", "Milk:1" };

		ShoppingBasket basket = priceEngine.gePricedBasket(getOrderedItems(items));

		assertEquals(basket.getOriginalTotal(), 3.10, 0.01);
		assertEquals(basket.getSubTotal(), 3.00, 0.01);

	}

	@Test
	public void testWhenBuyAGetBPercentOffOffer() {

		Map<String, String> matchProducts = new HashMap<>();

		matchProducts.put("Soup", "Bread");

		Offer[] offers = new Offer[1];
		offers[0] = new BuyAGetBPercentageDiscount(3, startDate, endDate, 2, 50, matchProducts);

		when(offerService.getAvailableOffers()).thenReturn(Arrays.asList(offers));

		String[] items = { "Apples:1", "Bread:1", "Soup:2" };

		ShoppingBasket basket = priceEngine.gePricedBasket(getOrderedItems(items));

		assertEquals(basket.getOriginalTotal(), 3.10, 0.01);
		assertEquals(basket.getSubTotal(), 2.70, 0.01);

	}

	@Test
	public void testWhenBuyTwoGetOneForFreeOffer() {

		String[] products = { "Milk" };

		Offer[] offers = new Offer[1];
		offers[0] = new BuyXForYQuantityDiscount(2, startDate, endDate, 2, 1, Arrays.asList(products));

		when(offerService.getAvailableOffers()).thenReturn(Arrays.asList(offers));

		String[] items = { "Apples:1", "Bread:1", "Milk:2" };

		ShoppingBasket basket = priceEngine.gePricedBasket(getOrderedItems(items));

		assertEquals(basket.getOriginalTotal(), 4.40, 0.01);
		assertEquals(basket.getSubTotal(), 3.10, 0.01);

	}

	@Test
	public void testAllOfferCombination() {

		String[] products1 = { "Apples" };

		Map<String, String> matchProducts = new HashMap<>();
		matchProducts.put("Soup", "Bread");

		String[] products2 = { "Milk" };

		Offer[] offers = new Offer[3];
		offers[0] = new PercentageDiscount(1, startDate, endDate, 10, Arrays.asList(products1));
		offers[1] = new BuyXForYQuantityDiscount(2, startDate, endDate, 2, 1, Arrays.asList(products2));
		offers[2] = new BuyAGetBPercentageDiscount(3, startDate, endDate, 2, 50, matchProducts);

		when(offerService.getAvailableOffers()).thenReturn(Arrays.asList(offers));

		String[] items = { "Apples:2", "Bread:1", "Milk:6", "Soup:2" };

		ShoppingBasket basket = priceEngine.gePricedBasket(getOrderedItems(items));

		assertEquals(basket.getOriginalTotal(), 11.90, 0.01);
		assertEquals(basket.getSubTotal(), 7.40, 0.01);

	}
}
