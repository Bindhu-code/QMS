package com.fg.tree.jobs;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.time.LocalDateTime;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.fg.tree.service.CreateReportService;

public class ReportJob implements Job {

	@Autowired
	private CreateReportService createreport;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		
		LocalDateTime time = LocalDateTime.now();
		
		
		System.out.println("Scheduler Time:::::---->"+time);

		//System.out.println("Inside:jobExecutemethod()--Start:");

		
		try {
			try {
				createreport.generateReport();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
	}

}
