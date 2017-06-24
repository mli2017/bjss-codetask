package com.bjss.codetask.pricebasket.service;

import static com.bjss.codetask.pricebasket.utils.PriceUtils.LINE_SEPARATOR;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.bjss.codetask.pricebasket.utils.PriceUtils;

/**
 * 
 * Simple price repository simulator.
 * 
 * Load prices from prices.tsv
 * 
 * @author Min Li
 *
 */

public class PriceRepository implements PriceService {
	private static final PriceRepository INSTANCE = new PriceRepository();

	Map<String, Float> priceTable;

	private PriceRepository() {
		priceTable = new HashMap<>();
		initPrice("prices.tsv");
	}

	void initPrice(String filename) {
		InputStream in = this.getClass().getClassLoader().getResourceAsStream(filename);
		Scanner scanner = new Scanner(in);
		scanner.useDelimiter(LINE_SEPARATOR);

		while (scanner.hasNext()) {
			String[] nameprice = scanner.next().split(PriceUtils.TAB_SEPARATOR);
			priceTable.put(nameprice[0], Float.parseFloat(nameprice[1]));
		}
		scanner.close();
	}

	public static PriceRepository getInstance() {
		return INSTANCE;
	}

	public float getPrice(String name) {
		return priceTable.get(name);
	}
}
