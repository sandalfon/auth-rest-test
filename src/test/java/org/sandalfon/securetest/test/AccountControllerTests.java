package org.sandalfon.securetest.test;


import static org.assertj.core.api.Assertions.*;

import java.nio.charset.Charset;
import java.util.Base64;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.sandalfon.securetest.Application;
import org.sandalfon.securetest.entity.Account;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class AccountControllerTests {





	@Test
	public void test() {
		RestTemplate restTemplate = new RestTemplate();   
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + Base64.getUrlEncoder().encodeToString("bgb:tgtg".getBytes(Charset.forName("UTF-8"))));
		HttpEntity<String> myRequest = new HttpEntity<>( headers);

		ResponseEntity<Account> response = restTemplate.exchange(
				"http://localhost:8080/netheos/account/8",
				HttpMethod.GET,
				myRequest,
				Account.class);

		Account returnedAccount = response.getBody();

		assertThat(returnedAccount.getUsername()).isEqualTo("bgb");

	}

	@Test
	public void test2() {
		RestTemplate restTemplate = new RestTemplate();   
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + Base64.getUrlEncoder().encodeToString("bgb:tgtg".getBytes(Charset.forName("UTF-8"))));
		HttpEntity<String> myRequest = new HttpEntity<>( headers);
		int code =200;
		//MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
		try{	restTemplate.exchange(
				"http://localhost:8080/netheos/account/1",
				HttpMethod.GET,
				myRequest,
				Account.class).getStatusCode();
		}catch(HttpClientErrorException e) {
			code = e.getRawStatusCode();
		}
			assertThat(code).isEqualTo(403);
	}


}