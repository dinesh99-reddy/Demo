package com.example.demo;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.DemoApplication;
import com.example.demo.model.Customer;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DemoApplicationTests {

	@Test
	public void contextLoads() {
	}
	@Autowired
	private TestRestTemplate restTemplate;
	
	@LocalServerPort
	private int port;
	
	
	private String getRootUrl() {
	return "http://localhost:" + port;
	}

@Test
public void testGetUserById() {
	Customer customer = restTemplate.getForObject(getRootUrl() + "/customer/1101", Customer.class);
   Assert.assertNotNull(customer);
}

@Test
public void testCreateUser() {
	Customer customer = new Customer();
	customer.setEmail("admin@gmail.com");
	customer.setFirstName("admin");
	customer.setLastName("admin");
	customer.setPassword("12345");
     ResponseEntity<Customer> postResponse = restTemplate.postForEntity(getRootUrl() + "/customer", customer, Customer.class);
     Assert.assertEquals(postResponse.getStatusCode(),HttpStatus.BAD_REQUEST);
     Assert.assertNull(postResponse.getBody());
     customer.setPassword("Dinesh123");
     customer.setDob(new Date(1996,06,04));
      postResponse = restTemplate.postForEntity(getRootUrl() + "/customer", customer, Customer.class);
      Assert.assertEquals(postResponse.getStatusCode(),HttpStatus.CREATED);
     Assert.assertNotNull(postResponse.getBody());
}
}
