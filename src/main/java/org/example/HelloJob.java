package org.example;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class HelloJob implements Job {
    private static Logger _log= LoggerFactory.getLogger(HelloJob.class);

    public HelloJob(){

    }
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        _log.info("Hello Quartz!-"+new Date());
    }
}
