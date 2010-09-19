package com.goodhope.thsale.utils;

import junit.framework.Assert;

import org.junit.Test;

public class NumberUtilTest {

	private NumberUtil numberUtil = new NumberUtil();

	@Test
	public void testParseNumber() {
		Assert.assertEquals("100000", numberUtil.parseAmount("100000 Gold"));
		Assert.assertEquals("100000.0", numberUtil.parseAmount("100000.0 Gold"));
	}

	@Test
	public void test() throws Exception {
		Assert.assertEquals(numberUtil.parsePrice("$ 393.76^? 303.97^? 253.12")[1], "393.76");
		Assert.assertEquals(numberUtil.parsePrice("$ 393.76^? 303.97^? 253.12")[2], "303.97");
		Assert.assertEquals(numberUtil.parsePrice("$ 393.76^? 303.97^? 253.12")[3], "253.12");

	}
}
