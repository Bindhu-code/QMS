package com.fg.tree.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fg.tree.constant.CommonConstant;





@Configuration 
public class MailConfig extends WebMvcConfigurerAdapter{
	
  

    @Bean
    public JavaMailSender javaMailService() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setHost(CommonConstant.EMAIL_HOST_NAME);
        javaMailSender.setPort(Integer.parseInt(CommonConstant.EMAIL_PORT));
        javaMailSender.setUsername(CommonConstant.EMAIL_USER_NAME);
        javaMailSender.setUsername(CommonConstant.EMAIL_PASSWORD);
        javaMailSender.setJavaMailProperties(getMailProperties());

        return javaMailSender;
    }

    private Properties getMailProperties() {
        Properties properties = new Properties();
    
        properties.setProperty("mail.transport.protocol", CommonConstant.EMAIL_PROTOCOL);
    
        properties.setProperty("mail.smtp.auth", CommonConstant.EMAIL_SMTP_AUTH);
    
        properties.setProperty("mail.smtp.starttls.enable", CommonConstant.EMAIL_SMTP_SSL_ENABLE);
        
        /*Start : Harshil Added*/
        properties.setProperty("mail.smtps.ssl.trust", "*");
        /*End : Harshil Added*/

        // properties.setProperty("mail.debug", "false");
        return properties;
    }
}


