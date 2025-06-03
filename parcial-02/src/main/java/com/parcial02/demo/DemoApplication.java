package com.parcial02.demo;

import com.parcial02.demo.entities.Book;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		Book book = new Book();
		book.setTitle("Test");
		System.out.println(book.getTitle());
	}

}
