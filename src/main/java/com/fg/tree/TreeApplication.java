package com.fg.tree;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TreeApplication {


	public static void main(String[] args) {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(TreeApplication.class);
		builder.headless(false);

	    ConfigurableApplicationContext context = builder.run(args);
	    System.setProperty("java.awt.headless", "true");
		//SpringApplication.run(TreeApplication.class, args);	    
	}
}