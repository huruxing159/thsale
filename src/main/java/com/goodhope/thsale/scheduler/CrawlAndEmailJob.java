package com.goodhope.thsale.scheduler;

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
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.goodhope.thsale.domain.PriceItem;
import com.goodhope.thsale.domain.ThsaleServer;
import com.goodhope.thsale.factory.ProxyFactory;
import com.goodhope.thsale.services.CrawlServerInfoService;
import com.goodhope.thsale.services.SpringMailService;

public class CrawlAndEmailJob extends QuartzJobBean {

	private static final Logger LOG = Logger.getLogger(CrawlAndEmailJob.class);
	private CrawlServerInfoService crawlServerInfoService;
	private SpringMailService springMailService;
	private String mailTo;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		try {
			URL us_thsaleUrl = new URL("http://www.thsale.com/world-of-warcraft/");
			URL eu_thsaleUrl = new URL("http://www.thsale.com/world-of-warcraft-eu/");

			List<ThsaleServer> usThsaleServers = crawlServerInfoService.crawlGameServersInfo(us_thsaleUrl.openConnection(ProxyFactory.getProxy()));
			LOG.debug("us complete.......................");
			List<ThsaleServer> euThsaleServers = crawlServerInfoService.crawlGameServersInfo(eu_thsaleUrl.openConnection(ProxyFactory.getProxy()));
			LOG.debug("eu complete.......................");
			Map<String, List<ThsaleServer>> serverMap = new HashMap<String, List<ThsaleServer>>();
			serverMap.put("us", usThsaleServers);
			serverMap.put("eu", euThsaleServers);

			createExcel(serverMap);
			LOG.debug("create excel file complete....................");

			String fileFullName = System.getProperty("user.dir") + System.getProperty("file.separator") + "workbook.xls";

			springMailService.sendMimeMail(mailTo, "thsale", "world-of-warcraft", (new String[] { fileFullName }));
			LOG.info("send email complete..........................");
		} catch (Exception e) {
			LOG.error(e);
			springMailService.sendSimpleMail("hu.ruxing@goodhope.net", "thsale cralw error", e.getMessage());
		}

	}

	private void createExcel(Map<String, List<ThsaleServer>> serverMap) throws FileNotFoundException, IOException {
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

	public void setCrawlServerInfoService(CrawlServerInfoService crawlServerInfoService) {
		this.crawlServerInfoService = crawlServerInfoService;
	}

	public void setSpringMailService(SpringMailService springMailService) {
		this.springMailService = springMailService;
	}

	public void setMailTo(String mailTo) {
		LOG.error(mailTo);
		this.mailTo = mailTo;
	}

}
