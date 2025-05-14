package com.springproj.shop.ShoppingApp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShoppingAppApplication implements CommandLineRunner {
	@Value("${my.variable}")
	private String value;
	public static void main(String[] args) {
		SpringApplication.run(ShoppingAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("My Variable is: "+value);
	}
}
