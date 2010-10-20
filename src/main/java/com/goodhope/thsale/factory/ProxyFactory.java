package com.goodhope.thsale.factory;

import java.net.InetSocketAddress;
import java.net.Proxy;

public class ProxyFactory {

	private static final Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("172.16.1.31", 808));

	public static Proxy getProxy() {
		return proxy;
	}

}
