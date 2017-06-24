package com.bjss.codetask.pricebasket.service;

import static com.bjss.codetask.pricebasket.utils.PriceUtils.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.bjss.codetask.pricebasket.exceptions.OfferTypeNotFoundException;
import com.bjss.codetask.pricebasket.model.OfferDefinition;
import com.bjss.codetask.pricebasket.model.offer.BuyAGetBPercentageDiscount;
import com.bjss.codetask.pricebasket.model.offer.BuyXForYQuantityDiscount;
import com.bjss.codetask.pricebasket.model.offer.Offer;
import com.bjss.codetask.pricebasket.model.offer.PercentageDiscount;

/**
 * 
 * A singleton service which is the repository keep offers definition
 * 
 * Currently load from offers.tsv file
 * 
 * @author Min Li
 *
 */

public class OfferRepository implements OfferService {
	private static final OfferRepository INSTANCE = new OfferRepository();

	List<Offer> offers;

	private OfferRepository() {
		offers = initOffers("offers.tsv");
	}

	private List<Offer> initOffers(String filename) {
		List<OfferDefinition> offerDefs = new ArrayList<>();

		InputStream in = this.getClass().getClassLoader().getResourceAsStream(filename);
		Scanner scanner = new Scanner(in);
		scanner.useDelimiter(LINE_SEPARATOR);

		while (scanner.hasNext()) {
			String line = scanner.next();
			String[] offerdata = line.split(TAB_SEPARATOR);

			OfferDefinition offerDef = new OfferDefinition(Integer.parseInt(offerdata[0]),
					Integer.parseInt(offerdata[1]), Integer.parseInt(offerdata[2]), offerdata[3], offerdata[4],
					offerdata[5].trim());

			offerDefs.add(offerDef);

		}
		scanner.close();

		return offerDefs.stream().map(od -> getOfferByDeinition(od)).collect(Collectors.toList());

	}

	private Offer getOfferByDeinition(OfferDefinition offerDef) throws OfferTypeNotFoundException {
		OfferType offerType = OfferType.valueOf(offerDef.getType());

		switch (offerType) {
		case Percentage:
			return new PercentageDiscount(offerDef.getType(), parseDate(offerDef.getStartDate()),
					parseDate(offerDef.getEndDate()), offerDef.getValue1(),
					Arrays.asList(offerDef.getApplyProducts().split(PRODUCT_NAME_SEPARATOR)));
		case BuyXForYQuantity:
			return new BuyXForYQuantityDiscount(offerDef.getType(), parseDate(offerDef.getStartDate()),
					parseDate(offerDef.getEndDate()), offerDef.getValue1(), offerDef.getValue2(),
					Arrays.asList(offerDef.getApplyProducts().split(PRODUCT_NAME_SEPARATOR)));
		case BuyAGetBPercentage:
			return new BuyAGetBPercentageDiscount(offerDef.getType(), parseDate(offerDef.getStartDate()),
					parseDate(offerDef.getEndDate()), offerDef.getValue1(), offerDef.getValue2(),
					getMatchProductMap(offerDef.getApplyProducts()));
		default:
			throw new OfferTypeNotFoundException(offerDef.getType());
		}
	}

	private Map<String, String> getMatchProductMap(String matchProductString) {
		List<String> productPairStrs = Arrays.asList(matchProductString.split(PRODUCT_NAME_SEPARATOR));
		Map<String, String> productPairs = new HashMap<>();

		for (String str : productPairStrs) {
			String[] productPair = str.split(KEY_VALUE_SEPARATOR);
			productPairs.put(productPair[0], productPair[1]);
		}

		return productPairs;

	}

	public List<Offer> getAvailableOffers() {
		return offers;
	}

	public static OfferRepository getInstance() {
		return INSTANCE;
	}

}
