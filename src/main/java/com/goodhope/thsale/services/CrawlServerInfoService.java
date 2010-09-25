package com.goodhope.thsale.services;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

import com.goodhope.thsale.domain.ThsaleServer;
import com.goodhope.thsale.runner.CrawlPriceRunner;

public class CrawlServerInfoService {

	public List<ThsaleServer> crawlGameServersInfo(URL usthsaleUrl) throws IOException, InterruptedException {
		Source s = new Source(usthsaleUrl);
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

		ExecutorService executorService = Executors.newFixedThreadPool(2);
		for (ThsaleServer ts : thsaleServers) {
			executorService.execute(new CrawlPriceRunner(ts));
		}
		executorService.shutdown();
		executorService.awaitTermination(2, TimeUnit.HOURS);
		return thsaleServers;
	}
}
