package com.test;

import java.util.Arrays;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.mcnc.domain.PushMessage;

public class PushTest {
	final String URL = "https://android.googleapis.com/gcm/send";
	final String API_KEY = "AIzaSyBRR_XDPKBwktu778NH06CNn0HEBCzWAJk";
	
	@Test
	public void testPush(){
		String deviceId = "fynvPwL07iE:APA91bGaedCjdo2-08cjdTdtZRDwhHWMKDhVpFzttr5aU8mbs6GqMw1HdkDGmShFPNSRe4EYkOXxtmv1FzSor37IR58AIg_1VD-D3HRP1pNI5x9-2jUctm947GItY3g9qZKCsFsm5aH4";
		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "key=" + API_KEY);
		headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		
		PushMessage pushMessage = new PushMessage();
		pushMessage.setRegistration_ids(Arrays.asList(deviceId));

		HttpEntity<PushMessage> entity = new HttpEntity<PushMessage>(pushMessage, headers);
		ResponseEntity<String> result = restTemplate.exchange(URL, HttpMethod.POST, entity, String.class);
		Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);
	}

}
