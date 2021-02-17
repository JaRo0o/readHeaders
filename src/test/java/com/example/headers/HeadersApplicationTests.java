package com.example.headers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.headers.utils.ReadHeaders;


@SpringBootTest
class HeadersApplicationTests {
	
	static final Logger log = LoggerFactory.getLogger(HeadersApplicationTests.class);	

	@Mock
	private HttpServletRequest httpRequest;
	
	@Autowired
	ReadHeaders rHeaders;
	
	/**
	    * Mock that simulates the sending headers in a request
	    */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void fillHeaders() {
		Enumeration headers = new StringTokenizer("header1 header2 header3", " ");
		when(httpRequest.getHeaderNames()).thenReturn(headers);

		Enumeration oneValue = new StringTokenizer("value1", " ");
		when(httpRequest.getHeaders("header1")).thenReturn(oneValue);

		Enumeration twoValues = new StringTokenizer("value1 value2", " ");
		when(httpRequest.getHeaders("header2")).thenReturn(twoValues);
		
		Enumeration threeValues = new StringTokenizer("value1 value2 value3", " ");
		when(httpRequest.getHeaders("header3")).thenReturn(threeValues);
	}

	
	/**
	    * HttpServletRequest test for the method listHeaders
	    */
	
	@Test
	public void listAllHeaders() {
		
		log.info("START TEST");

		fillHeaders();
		
		log.info("READING ALL HEADERS...");
		Map<String, List<String>> headersMap = rHeaders.listHeaders(httpRequest);
		
		log.info("All HEADER LIST: " + headersMap.toString());
		
		assertThat(headersMap.get("header1")).containsOnly("value1");
		assertThat(headersMap.get("header2")).contains("value1");
		assertThat(headersMap.get("header2")).containsOnly("value1", "value2");
		assertThat(headersMap.get("header3")).containsOnly("value1", "value2", "value3");
		
		log.info("END TEST");
	}
	
	
	/**
	    * HttpServletRequest test 1 for the method listHeadersByKey (one header)
	    */
	@Test
	public void listHeader1() {
		
		log.info("START TEST 2");

		fillHeaders();
		
		log.info("READING ONE HEADER...");
		Map<String, List<String>> headersMap1 = rHeaders.listHeaders(new ArrayList<String>(Arrays.asList("header1")), httpRequest);
		
		log.info("HEADER BY KEY LIST 1: " + headersMap1.toString());		
		assertThat(headersMap1.get("header1")).containsOnly("value1");
		
		log.info("END TEST 2");

	}
	
	
	/**
	    * HttpServletRequest test 2 for the method listHeadersByKey (two headers)
	    */
	@Test
	public void listHeader2() {
		
		log.info("START TEST 3");

		fillHeaders();
		
		log.info("READING TWO HEADERS...");
		Map<String, List<String>> headersMap = rHeaders.listHeaders(new ArrayList<String>(Arrays.asList("header1", "header2")), httpRequest);
		
		log.info("HEADER BY KEY LIST 2: " + headersMap.toString());		
		assertThat(headersMap.get("header1")).containsOnly("value1");
		assertThat(headersMap.get("header2")).contains("value1");
		assertThat(headersMap.get("header2")).containsOnly("value1", "value2");
		
		log.info("END TEST 3");
	}
	
	/**
	    * HttpServletRequest test 3 for the method listHeadersByKey (three headers)
	    */
	@Test
	public void listHeader3() {
		
		log.info("START TEST 3");

		fillHeaders();
		
		log.info("READING THREE HEADERS...");
		Map<String, List<String>> headersMap = rHeaders.listHeaders(new ArrayList<String>(Arrays.asList("header1", "header2", "header3")), httpRequest);
		
		log.info("HEADER BY KEY LIST 3: " + headersMap.toString());		
		assertThat(headersMap.get("header1")).containsOnly("value1");
		assertThat(headersMap.get("header2")).contains("value1");
		assertThat(headersMap.get("header2")).containsOnly("value1", "value2");
		assertThat(headersMap.get("header3")).containsOnly("value1", "value2", "value3");
		
		log.info("END TEST 3");
	}

}
