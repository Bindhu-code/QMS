package com.fg.tree.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
@Configuration
@ComponentScan(basePackages = "com.fg.tree.config")
public class WebConfig extends WebMvcConfigurerAdapter {
	 @Bean
	   public HibernateJpaSessionFactoryBean sessionFactory() {
	       return new HibernateJpaSessionFactoryBean();
	   }
	}
