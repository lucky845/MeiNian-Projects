package com.atguigu;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

public class HelloQuartz implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        JobDetail detail = jobExecutionContext.getJobDetail();
        String name = detail.getJobDataMap().getString("name");
        System.out.println("my job name = " + name + "at" + new Date());

    }
}
