package com.goodhope.thsale;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

import com.goodhope.thsale.domain.PriceItem;
import com.goodhope.thsale.domain.ThsaleServer;
import com.goodhope.thsale.factory.SpringBeanFactory;
import com.goodhope.thsale.runner.CrawlPriceRunner;
import com.goodhope.thsale.services.SpringMailService;

public class ThsaleCrawler {

	public static void main(String[] args) throws IOException, InterruptedException {
		URL thsaleUrl = new URL("http://www.thsale.com/world-of-warcraft/");
		// URL thsaleUrl = new URL("http://www.thsale.com/world-of-warcraft-eu/");
		URLConnection thsale = thsaleUrl.openConnection();
		Source s = new Source(thsale);
		Element gameServers = s.getElementById("selectGameServer");
		List<Element> servers = gameServers.getChildElements();
		List<ThsaleServer> thsaleServers = new ArrayList<ThsaleServer>();
		for (Element server : servers) {
			if (server.getAttributeValue("value") != null && !server.getAttributeValue("value").equals("")) {
				ThsaleServer ts = new ThsaleServer();
				ts.setServerName(server.getTextExtractor().toString());
				ts.setThsaleValue(server.getAttributeValue("value"));
				thsaleServers.add(ts);
			}
		}

		ExecutorService executorService = Executors.newFixedThreadPool(20);
		for (ThsaleServer ts : thsaleServers) {
			executorService.execute(new CrawlPriceRunner(ts));
		}
		executorService.shutdown();
		executorService.awaitTermination(1, TimeUnit.HOURS);

		SpringMailService mailService = (SpringMailService) SpringBeanFactory.getBean("springMailService");
		StringBuilder mailContent = new StringBuilder();
		for (ThsaleServer thsaleServer : thsaleServers) {
			for (PriceItem priceItem : thsaleServer.getPriceList()) {
				mailContent.append(thsaleServer.getServerName() + " " + priceItem.getGoldAmount() + " gold  $" + priceItem.getUsd() + "\n");
			}
		}

		mailService.send("zeng.hengxing@goodhope.net", "thsale", mailContent.toString());

	}
}
