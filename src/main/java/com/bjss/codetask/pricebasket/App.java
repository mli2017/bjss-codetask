package com.bjss.codetask.pricebasket;

import static com.bjss.codetask.pricebasket.utils.PriceUtils.*;

import com.bjss.codetask.pricebasket.model.ShoppingBasket;
import com.bjss.codetask.pricebasket.service.OfferRepository;
import com.bjss.codetask.pricebasket.service.OfferService;
import com.bjss.codetask.pricebasket.service.PriceEngine;
import com.bjss.codetask.pricebasket.service.PriceEngineImpl;
import com.bjss.codetask.pricebasket.service.PriceRepository;
import com.bjss.codetask.pricebasket.service.PriceService;

/**
 * Main class
 * 
 * @author Min Li
 *
 */
public class App {

	public static void main(String[] args) {

		if (args.length == 0)
			usage();
		else
			priceBasket(args);

	}

	protected static void usage() {
		System.out.println("Usage: java -jar ... shopitems");
		System.out.println("  where shopitems is 'ProductName:Quantity' repeat multiple times ");
		System.out.println("  eg. 'Apples:2 Soup:2 Bread:1 Milk:6'");
	}

	private static void priceBasket(String[] args) {
		OfferService offerService = OfferRepository.getInstance();
		PriceService priceService = PriceRepository.getInstance();

		PriceEngine priceEngine = new PriceEngineImpl(offerService, priceService);

		ShoppingBasket basket = priceEngine.gePricedBasket(getOrderedItems(args));

		System.out.println(basket.toString());
	}
}
