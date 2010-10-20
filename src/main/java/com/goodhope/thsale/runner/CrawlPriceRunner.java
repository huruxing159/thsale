package com.goodhope.thsale.runner;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import net.htmlparser.jericho.Source;

import org.springframework.util.StringUtils;

import com.goodhope.thsale.domain.PriceItem;
import com.goodhope.thsale.domain.ThsaleServer;
import com.goodhope.thsale.utils.NumberUtil;

public class CrawlPriceRunner implements Runnable {

	private ThsaleServer ts;
	private String priceUrl = new String("http://www.thsale.com/GoldDataDispose.aspx?GameServerCode=");
	private Source priceList;
	private NumberUtil numberUtil;

	public CrawlPriceRunner(ThsaleServer ts) {
		this.ts = ts;
		numberUtil = new NumberUtil();
	}

	@Override
	public void run() {
		try {
			TimeUnit.SECONDS.sleep(10);
			priceList = new Source(new URL(priceUrl + ts.getThsaleValue()));
			String priceListString = priceList.getTextExtractor().toString();
			if (!StringUtils.hasText(priceListString) || !priceListString.contains("|")) {
				return;
			}
			String[] split = priceListString.split("\\|");
			for (String item : split) {
				/*
				 * item content 572196e2-16a9-42c1-a9fa-9d58fe5333fb@100000 Gold@$ 393.76^? 303.97^? 253.12
				 */
				if (!item.contains("@")) {
					continue;
				}
				PriceItem priceItem = new PriceItem();
				String[] itemDetail = item.split("@");

				int goleAmount = Integer.parseInt(numberUtil.parseAmount(itemDetail[1]));
				String[] price = numberUtil.parsePrice(itemDetail[2]);
				priceItem.setGoldAmount(goleAmount);
				priceItem.setUsd(Float.parseFloat(price[1]));
				priceItem.setEur(Float.parseFloat(price[2]));
				priceItem.setGbp(Float.parseFloat(price[3]));
				ts.getPriceList().add(priceItem);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
