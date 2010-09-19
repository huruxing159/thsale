package com.goodhope.thsale.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberUtil {
	private Pattern pa = Pattern.compile("(\\D*)(\\d+\\.?\\d+)(\\D*)");
	private Matcher matcher;

	public String parseAmount(String string) {
		matcher = pa.matcher(string);
		if (matcher.find()) {
			return matcher.group(2);
		}
		return "";
	}

	public String[] parsePrice(String string) throws Exception {
		String[] allPrice = string.split(" ");
		if (allPrice == null) {
			throw new Exception();
		}
		for (int i = 0; i < allPrice.length; i++) {
			allPrice[i] = parseAmount(allPrice[i]);
		}
		return allPrice;

	}
}
