package org.example;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import static org.quartz.DateBuilder.evenMinuteDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws SchedulerException {
        Logger log= LoggerFactory.getLogger(App.class);
        log.info("---初始化---");
        //创建调度工厂
        SchedulerFactory sf=new StdSchedulerFactory();
        Scheduler schduler=sf.getScheduler();
        log.info("---初始化完成---");

        Date runTime=evenMinuteDate(new Date());
        log.info("---Scheduling Job---");
        //定义一个任务并绑定HelloJob.class
        JobDetail jobDetail=newJob(HelloJob.class).withIdentity("job1","group1").build();
        //定义触发器
        Trigger trigger=newTrigger().withIdentity("trigger1","group1").startAt(runTime).build();
        //绑定任务和触发器到调度
        schduler.scheduleJob(jobDetail,trigger);

        log.info(jobDetail.getKey()+"will run at:"+runTime);
        schduler.start();;

        log.info("---Started Scheduler---");
        log.info("---Waiting 65 seconds--");
        try{
            Thread.sleep(65*1000L);
        }catch (Exception e){
            e.printStackTrace();
        }
        log.info("---Shutting Down---");
        schduler.shutdown(true);
        log.info("---Shutdown Complete---");
    }
}
