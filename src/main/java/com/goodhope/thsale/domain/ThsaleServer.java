package com.goodhope.thsale.domain;

import java.util.ArrayList;
import java.util.List;

public class ThsaleServer {

	private String serverName;
	private String thsaleValue;
	private List<PriceItem> priceList = new ArrayList<PriceItem>();

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getServerName() {
		return serverName;
	}

	public void setThsaleValue(String thsaleValue) {
		this.thsaleValue = thsaleValue;
	}

	public String getThsaleValue() {
		return thsaleValue;
	}

	public void setPriceList(List<PriceItem> priceList) {
		this.priceList = priceList;
	}

	public List<PriceItem> getPriceList() {
		return priceList;
	}

}
