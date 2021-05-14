/*
  The MultiValueDictionary program implements command line
  app that stores a multi-value string dictionary in memory

				#####################################
				#####################################
				###   @author  Abhinay R Avuthu   ###
				###   @version 1.0                ###
				###   @since   2021-05-12         ###
				#####################################
				#####################################
 */
package com.spreetail.multivaluedictionary;

import com.spreetail.service.Service;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@SpringBootApplication
public class MultiValueDictionaryApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(MultiValueDictionaryApplication.class, args);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Service serviceClass = new Service();
		String line;
		while((line = br.readLine()) != null){
			List<String> result = serviceClass.processArguments(line);
			// Prints each value from the List
			result.stream().forEach(System.out::println);
		}
		System.out.println("This is the end of the program !!!");
	}

}
