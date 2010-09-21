package com.goodhope.thsale;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.goodhope.thsale.domain.PriceItem;
import com.goodhope.thsale.domain.ThsaleServer;
import com.goodhope.thsale.factory.SpringBeanFactory;
import com.goodhope.thsale.services.CrawlServerInfoService;
import com.goodhope.thsale.services.SpringMailService;

public class ThsaleCrawler {
	private static final Logger LOG = Logger.getLogger(ThsaleCrawler.class);

	public static void main(String[] args) throws IOException, InterruptedException {
		URL us_thsaleUrl = new URL("http://www.thsale.com/world-of-warcraft/");
		URL eu_thsaleUrl = new URL("http://www.thsale.com/world-of-warcraft-eu/");
		CrawlServerInfoService crawlServerInfoService = (CrawlServerInfoService) SpringBeanFactory.getBean("crawlServerInfoService");

		List<ThsaleServer> usThsaleServers = crawlServerInfoService.crawlGameServersInfo(us_thsaleUrl);
		LOG.debug("us complete.......................");
		List<ThsaleServer> euThsaleServers = crawlServerInfoService.crawlGameServersInfo(eu_thsaleUrl);
		LOG.debug("eu complete.......................");
		Map<String, List<ThsaleServer>> serverMap = new HashMap<String, List<ThsaleServer>>();
		serverMap.put("us", usThsaleServers);
		serverMap.put("eu", euThsaleServers);

		createExcel(serverMap);
		LOG.debug("create excel file complete....................");

		String fileFullName = System.getProperty("user.dir") + System.getProperty("file.separator") + "workbook.xls";
		sendEmail(new String[] { fileFullName });
		LOG.info("send email complete..........................");
		
	}

	private static void sendEmail(String[] attachments) {
		SpringMailService mailService = (SpringMailService) SpringBeanFactory.getBean("springMailService");
		mailService.sendMimeMail("hu.ruxing@goodhope.net", "thsale", "world-of-warcraft", attachments);
	}

	private static void createExcel(Map<String, List<ThsaleServer>> serverMap) throws FileNotFoundException, IOException {
		Workbook wb = new HSSFWorkbook();
		for (Entry<String, List<ThsaleServer>> server : serverMap.entrySet()) {
			Sheet sheet = wb.createSheet(server.getKey().toString());
			List<ThsaleServer> thsaleServers = server.getValue();
			for (int i = 0; i < thsaleServers.size(); i++) {
				ThsaleServer thsaleServer = thsaleServers.get(i);
				for (int j = 0; j < thsaleServer.getPriceList().size(); j++) {
					PriceItem priceItem = thsaleServer.getPriceList().get(j);
					Row row = sheet.createRow(i * thsaleServer.getPriceList().size() + j);
					row.createCell(0).setCellValue(thsaleServer.getServerName());
					row.createCell(1).setCellValue(priceItem.getGoldAmount());
					row.createCell(2).setCellValue(priceItem.getUsd());
				}
			}

		}

		FileOutputStream fileOut = new FileOutputStream("workbook.xls");
		wb.write(fileOut);
		fileOut.close();
	}

}
