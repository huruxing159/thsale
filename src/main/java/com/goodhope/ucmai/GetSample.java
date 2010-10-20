package com.goodhope.ucmai;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import net.htmlparser.jericho.Source;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;

public class GetSample {
	public static void main(String[] args) throws ClientProtocolException, IOException {

		HttpClient httpclient = new DefaultHttpClient();

		// List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		// formparams.add(new BasicNameValuePair("gameid", "2"));
		// formparams.add(new BasicNameValuePair("zonechar", ""));
		// UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");
		HttpPost httppost = new HttpPost("http://www.ucmai.com/SearchPrice.asmx/ZoneList");
		// httppost.addHeader("Host", "www.ucmai.com");
		// httppost.addHeader("Accept", "application/json, text/javascript, */*");
		httppost.addHeader("Content-Type", "application/json; charset=UTF-8;utf-8");
		httppost.addHeader("X-Requested-With", "XMLHttpRequest");

		StringEntity entity = null;
		entity = new StringEntity("{gameid:'3', zonechar:''}");
		httppost.setEntity(entity);

		HttpResponse response = httpclient.execute(httppost);
		HttpEntity resEntity = response.getEntity();
		StringBuffer sb = new StringBuffer();
		if (resEntity != null) {
			InputStream instream = resEntity.getContent();
			int l;
			byte[] tmp = new byte[2048];
			while ((l = instream.read(tmp)) != -1) {
				sb.append(new String(tmp, "utf-8"));
			}
		}
		System.out.println(sb.toString());

	}
}
