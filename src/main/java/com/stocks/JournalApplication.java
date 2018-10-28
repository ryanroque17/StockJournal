package com.stocks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan({"com.stocks.controller.*", "com.stocks.journal.*", "com.stocks.repository.*", "com.stocks.model.*", "com.stocks.service.*"})
public class JournalApplication {

	public static void main(String[] args) {
		System.setProperty("server.servlet.context-path", "/home");
		
		SpringApplication.run(JournalApplication.class, args);
	}
}
