package com.springproj.shop.ShoppingApp;

import com.springproj.shop.ShoppingApp.services.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@RequiredArgsConstructor
@SpringBootApplication
public class ShoppingAppApplication implements CommandLineRunner {
	@Value("${my.variable}")
	private String value;
	private final DataService dataService;
	public static void main(String[] args) {
		SpringApplication.run(ShoppingAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("My Variable is: "+value);
		System.out.println("The data is: "+dataService.getData());
	}
}
