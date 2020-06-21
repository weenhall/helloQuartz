package org.example.simpletrigger;

import org.example.SimpleJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import static org.quartz.DateBuilder.futureDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * 简单触发器（适用于一些简单的任务）
 */
public class SimpleTriggerApp {
    public static void main(String[] args) throws Exception {
        Logger log = LoggerFactory.getLogger(SimpleTriggerApp.class);
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler scheduler = sf.getScheduler();

        //获取一个随机开始时间
        Date startTime = DateBuilder.nextGivenSecondDate(null, 15);

        //任务1
        JobDetail job1 = newJob(SimpleJob.class).withIdentity("job1", "group1").build();
        SimpleTrigger trigger = (SimpleTrigger) newTrigger().withIdentity("trigger1", "group1").startAt(startTime).build();
        Date ft = scheduler.scheduleJob(job1, trigger);
        log.info(job1.getKey().getName() + " will run at: " + ft + " and repeat: " + trigger.getRepeatCount() + " times,every " + trigger.getRepeatInterval() / 1000 + " seconds");

        //任务2
        JobDetail job2 = newJob(SimpleJob.class).withIdentity("job2", "group1").build();
        trigger = (SimpleTrigger) newTrigger().withIdentity("trigger2", "group1").startAt(startTime).build();
        ft = scheduler.scheduleJob(job2, trigger);
        log.info(job2.getKey().getName() + " will run at: " + ft + " and repeat: " + trigger.getRepeatCount() + " times,every " + trigger.getRepeatInterval() / 1000 + " seconds");

        //任务3
        JobDetail job3 = newJob(SimpleJob.class).withIdentity("job3", "group1").build();
        //每10秒执行一次，总共执行5次
        trigger = (SimpleTrigger) newTrigger().withIdentity("trigger3", "group1").startAt(startTime)
                .withSchedule(simpleSchedule().withIntervalInSeconds(10).withRepeatCount(5)).build();
        ft = scheduler.scheduleJob(job3, trigger);
        log.info(job3.getKey().getName() + " will run at: " + ft + " and repeat: " + trigger.getRepeatCount() + " times,every " + trigger.getRepeatInterval() / 1000 + " seconds");

        //任务4
        JobDetail job4 = newJob(SimpleJob.class).withIdentity("job4", "group1").build();
        //2分钟之后执行
        trigger = (SimpleTrigger) newTrigger().withIdentity("trigger4", "group1").startAt(futureDate(2, DateBuilder.IntervalUnit.MINUTE)).build();
        ft = scheduler.scheduleJob(job4, trigger);
        log.info(job4.getKey().getName() + " will run at: " + ft + " and repeat: " + trigger.getRepeatCount() + " times,every " + trigger.getRepeatInterval() / 1000 + " seconds");

        //任务5
        JobDetail job5 = newJob(SimpleJob.class).withIdentity("job5", "group1").build();
        //每2秒执行，直到服务停止
        trigger = (SimpleTrigger) newTrigger().withIdentity("trigger5", "group1").startAt(startTime).withSchedule(simpleSchedule().withIntervalInSeconds(2).repeatForever()).build();
        ft = scheduler.scheduleJob(job5, trigger);
        log.info(job5.getKey().getName() + " will run at: " + ft + " and repeat: " + trigger.getRepeatCount() + " times,every " + trigger.getRepeatInterval() / 1000 + " seconds");

        scheduler.start();
    }
}
