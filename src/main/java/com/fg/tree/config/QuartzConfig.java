package com.fg.tree.config;

import java.io.IOException;
import java.util.Properties;

import org.quartz.CronTrigger;
import org.quartz.Trigger;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.fg.tree.factory.QuartzJobFactory;

import com.fg.tree.jobs.ReportJob;

@Configuration
@PropertySource("classpath:/quartz.properties")
public class QuartzConfig {
	
	
	@Value("${org.quartz.scheduler.instanceName}")
	private String instanceName;

	@Value("${org.quartz.threadPool.threadCount}")
	private String threadCount;
	
	@Value("${apps.jobs.cronValue}")
	private String cronvalue;
	
	@Bean
	public JobFactory jobFactory(ApplicationContext applicationContext) {
		QuartzJobFactory jobFactory = new QuartzJobFactory();
		jobFactory.setApplicationContext(applicationContext);
		return jobFactory;
	}
	
	@Bean
	public SchedulerFactoryBean schedulerFactoryBean(ApplicationContext applicationContext ) throws IOException {
		SchedulerFactoryBean factory = new SchedulerFactoryBean();
		
		Trigger[]  trigger = {createReportsJobTrigger().getObject()};
		factory.setAutoStartup(true);
		factory.setOverwriteExistingJobs(true);
		factory.setJobFactory(jobFactory(applicationContext));
		Properties quartzProperties = new Properties();
		quartzProperties.setProperty("org.quartz.scheduler.instanceName", instanceName);
		quartzProperties.setProperty("org.quartz.threadPool.threadCount", threadCount);
		factory.setQuartzProperties(quartzProperties);
		
		factory.setTriggers(trigger);
		return factory;
	}
	@Bean(name = "createReportsJobTrigger")
	public CronTriggerFactoryBean createReportsJobTrigger() {
		CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
		factoryBean.setJobDetail(createReportJobDetails().getObject());
		factoryBean.setStartDelay(Long.parseLong("0"));	
		factoryBean.setCronExpression(cronvalue);
		factoryBean.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_FIRE_ONCE_NOW);
		return factoryBean;
	}

	@Bean(name = "createReportJobDetails")
	public JobDetailFactoryBean createReportJobDetails() {
		JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
		jobDetailFactoryBean.setJobClass(ReportJob.class);
		jobDetailFactoryBean.setDescription("Jobs are started..........");
		//jobDetailFactoryBean.setName(key);
		jobDetailFactoryBean.setDurability(true);
		return jobDetailFactoryBean;
	}

	

	
	
	
}
