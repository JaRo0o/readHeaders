package com.example.headers.utils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

@Component
public class ReadHeaders {
	
	/**
	    * Method that reads all the Headers of an HttpServletRequest 
	    *
	    * @param  request   HttpServletRequest request
	    * @return         Map object with the list of headers
	    */
	public Map<String, List<String>> listHeaders(HttpServletRequest request) {
		return  Collections.list(request.getHeaderNames())
					.stream()
					.collect(Collectors.toMap(
							Function.identity(),
							p -> Collections.list(
									request.getHeaders(p)
									))
							);
	}
	
	
	/**
	    * Overloaded Method that reads the headers of an HttpServletRequest based on a list of String as an input parameter
	    *
	    * @param  headerKeys   list of headers
	    * @param  request   HttpServletRequest request
	    * @return         Map object with the list of specified headers
	    */
	public Map<String, List<String>> listHeaders(List<String> headerKeys, HttpServletRequest request) {
		
		List<String> list = Collections.list(request.getHeaderNames());
				
		return headerKeys.stream()
	            .filter(e -> list.contains(e))
	            .collect(Collectors.toMap(
						Function.identity(),
						p -> Collections.list(
								request.getHeaders(p)
								))
						);

	}

}
