package org.example;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleJob implements Job {
    private static Logger _log= LoggerFactory.getLogger(SimpleJob.class);

    ThreadLocal<SimpleDateFormat> tl=new ThreadLocal<SimpleDateFormat>(){
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    public SimpleJob(){

    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobKey jobKey=context.getJobDetail().getKey();
        //打印当前任务名称
        _log.info("SimpleJob says: "+jobKey.getName()+" executing at "+tl.get().format(new Date()));
    }
}
