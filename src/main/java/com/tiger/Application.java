package com.tiger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
public class Application {
	
	@Value("${book.author}")
	private String author;
	
	@Value("${book.price}")
	private int price;

	@RequestMapping("/hi")
	public String firstBoot() {
		return "hello Spring-Boot";
	}
	
	@RequestMapping("/")
	public String book() {
		return author+":"+price;
	}
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
