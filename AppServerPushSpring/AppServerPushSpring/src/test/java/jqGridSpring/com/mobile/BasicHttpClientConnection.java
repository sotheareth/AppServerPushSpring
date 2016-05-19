package jqGridSpring.com.mobile;

import static org.junit.Assert.*;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectionRequest;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.junit.Test;
import org.slf4j.spi.LocationAwareLogger;

import com.mcnc.domain.MultiHttpClientConnThread;

public class BasicHttpClientConnection {
	//http://www.baeldung.com/httpclient-timeout
	@Test
	public void testBasicHttpClientConnection(){
		BasicHttpClientConnectionManager connManager = new BasicHttpClientConnectionManager();
		HttpRoute route = new HttpRoute(new HttpHost("www.baeldung.com", 80));
		ConnectionRequest connRequest = connManager.requestConnection(route, null);
	}
	
	@Test
	public void testPoolingHttpClientConnectionManager() throws ClientProtocolException, IOException{
		PoolingHttpClientConnectionManager poolingConnManager = new PoolingHttpClientConnectionManager();
		CloseableHttpClient client = HttpClients.custom().setConnectionManager(poolingConnManager).build();
		client.execute(new HttpGet("http://www.baeldung.com/"));
		assertTrue(poolingConnManager.getTotalStats().getLeased() == 1);
	}
	
	@Test
	public void test1() throws ClientProtocolException, IOException{
		HttpGet get1 = new HttpGet("http://www.baeldung.com/");
		HttpGet get2 = new HttpGet("http://google.com"); 
		
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
		
		CloseableHttpClient client1 = HttpClients.custom().setConnectionManager(connManager).build();
		CloseableHttpClient client2 = HttpClients.custom().setConnectionManager(connManager).build();
		
		MultiHttpClientConnThread thread1 = new MultiHttpClientConnThread(client1, get1); 
		MultiHttpClientConnThread thread2 = new MultiHttpClientConnThread(client2, get2);
		
		thread1.start();
		thread2.start();
		try {
			thread1.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			thread2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
	}
	
	@Test
	public void test2(){
		try {
			
			Request.Get("http://www.google.com")
			        .execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
				
//		System.out.println(content);
	}
}
