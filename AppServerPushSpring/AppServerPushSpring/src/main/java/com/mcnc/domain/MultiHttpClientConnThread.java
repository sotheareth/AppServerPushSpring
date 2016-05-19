package com.mcnc.domain;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

public class MultiHttpClientConnThread extends Thread {
	private CloseableHttpClient client;
	private HttpGet get;

	public MultiHttpClientConnThread(CloseableHttpClient client, HttpGet get) {
		this.client = client;
		this.get = get;
	}

	public void run() {
		try {
			HttpResponse response = client.execute(get);
			byte[] result = IOUtils.toByteArray(response.getEntity().getContent());
			File file = new File("file://D:\\test.txt");
			file.createNewFile();
			FileUtils.writeByteArrayToFile(file, result);
			
			EntityUtils.consume(response.getEntity());
		} catch (ClientProtocolException ex) {
		} catch (IOException ex) {
		}
	}
}