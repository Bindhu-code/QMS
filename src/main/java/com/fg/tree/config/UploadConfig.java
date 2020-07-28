package com.fg.tree.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

public class UploadConfig {
	
	   @Bean
	   public CommonsMultipartResolver multipartConfigElement() {
	      CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
	      commonsMultipartResolver.setDefaultEncoding("utf-8");
	      commonsMultipartResolver.setMaxUploadSizePerFile(400000002);
	      return commonsMultipartResolver;
	   }
	
	   @Bean
	   public MultipartResolver mulitpartResolver() {
	      CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
	      commonsMultipartResolver.setDefaultEncoding("utf-8");
	      commonsMultipartResolver.setMaxUploadSizePerFile(400000002);
	      return commonsMultipartResolver;
	   }

	   @Bean
	   public TemplateResolver templateResolver() {
	      TemplateResolver resolver = new ServletContextTemplateResolver();
	      resolver.setPrefix("/WEB-INF/view/");
	      resolver.setSuffix(".html");
	      resolver.setTemplateMode("HTML5");
	      return resolver;
	   }

	   @Bean
	   @Autowired
	   public SpringTemplateEngine templateEngine(TemplateResolver resolver) {
	      SpringTemplateEngine engine = new SpringTemplateEngine();
	      engine.setTemplateResolver(resolver);
	      return engine;
	   }

	   @Bean
	   @Autowired
	   public ViewResolver viewResolver(SpringTemplateEngine engine) {
	      ThymeleafViewResolver resolver = new ThymeleafViewResolver();
	      resolver.setTemplateEngine(engine);
	      return resolver;
	   }

}
