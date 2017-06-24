package com.bjss.codetask.pricebasket.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bjss.codetask.pricebasket.model.OrderedItem;

/**
 * Utility
 * 
 * @author Min Li
 *
 */
public class PriceUtils {
	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	public static enum OfferType {
		Percentage(1), BuyXForYQuantity(2), BuyAGetBPercentage(3);

		private final int typeId;

		private static Map<Integer, OfferType> map = new HashMap<>();

		static {
			for (OfferType ot : OfferType.values()) {
				map.put(ot.typeId, ot);
			}
		}

		OfferType(int id) {
			this.typeId = id;
		}

		public int getValue() {
			return typeId;
		}

		public static OfferType valueOf(int id) {
			return map.get(id);
		}
	}

	public static final String LINE_SEPARATOR = "\n";
	public static final String TAB_SEPARATOR = "\t";
	public static final String PRODUCT_NAME_SEPARATOR = ",";
	public static final String KEY_VALUE_SEPARATOR = ":";
	public static final String OUTPUT_SEPARATOR = "-";

	public static final int LINE_TEXT_LENGTH = 36;

	public static String formatOutput(double dub) {
		return String.format("%.2f", dub);
	}

	public static String mkString(List<String> list) {
		return Arrays.toString(list.toArray());
	}

	public static String repeat(String s, int n) {
		return String.join("", Collections.nCopies(n, s));
	}

	public static String padding(String s) {
		return String.format("%" + LINE_TEXT_LENGTH + "s", s);

	}

	public static List<OrderedItem> getOrderedItems(String[] args) {
		List<OrderedItem> orderedItems = new ArrayList<>();
		for (String arg : args) {
			String[] nameQuantity = arg.split(KEY_VALUE_SEPARATOR);

			orderedItems.add(new OrderedItem(nameQuantity[0], Integer.parseInt(nameQuantity[1])));

		}
		return orderedItems;
	}

	public static Date parseDate(String str) {

		try {
			return df.parse(str);
		} catch (ParseException e) {
			// Ignore
			return null;
		}

	}

}
