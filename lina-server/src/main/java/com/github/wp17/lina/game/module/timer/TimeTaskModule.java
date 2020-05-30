package com.github.wp17.lina.game.module.timer;

import java.util.HashMap;
import java.util.Map;

import com.github.wp17.lina.game.module.AbsModule;
import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;

import com.github.wp17.lina.common.log.LoggerProvider;
import com.github.wp17.lina.util.StringUtil;

public class TimeTaskModule implements AbsModule {
	private TimeTaskModule(){}
	private static final TimeTaskModule instance = new TimeTaskModule();
	public static TimeTaskModule getInstance() {
		return instance;
	}
	
	private Scheduler scheduler;
	public Scheduler getScheduler() {
		if (null == scheduler) {
			synchronized (this) {
				if (null == scheduler) {
					try {
						System.setProperty("org.terracotta.quartz.skipUpdateCheck","true");
						scheduler = StdSchedulerFactory.getDefaultScheduler();
						scheduler.start();
					} catch (SchedulerException e) {
						LoggerProvider.addExceptionLog(e);
					}
				}
			}
		}
		return scheduler;
	}
	
	private Map<String, CronTrigger> triggers = new HashMap<String, CronTrigger>();
	
	@Override
	public void init() {
		createJob("0 0/1 * * * ?", EveryMinuteTask.class);
		createJob("0 0/5 * * * ?", Every5MinutesTask.class);
	}

	@Override
	public void shutdown() {
		try {
			if (null != scheduler)
				scheduler.shutdown();
		} catch (Exception e) {
			LoggerProvider.addExceptionLog(e);
		}
	}

	public void createJob(String cronExpression, Class<? extends Job> clazz) {
		if (null != clazz && StringUtil.isNotEmpty(cronExpression)) {
			String jobName = clazz.getName();
			JobDetail detail = createJobDetail(jobName, Scheduler.DEFAULT_GROUP, clazz);
			CronTrigger trigger = createTrigger(jobName, Scheduler.DEFAULT_GROUP, cronExpression);
			try {
				Scheduler scheduler = getScheduler();
				if (scheduler.getJobDetail(detail.getKey()) != null) {
					scheduler.deleteJob(detail.getKey());
				}
				scheduler.scheduleJob(detail, trigger);
				triggers.put(jobName, trigger);

			} catch (Exception e) {
				LoggerProvider.addExceptionLog(e);
			}
		}
	}


	private JobDetail createJobDetail(String jobName, String jobGroup, Class<? extends Job> clazz) {
		JobKey jobKey = new JobKey(jobName, jobGroup);
		JobDetail job = JobBuilder.newJob(clazz).withIdentity(jobKey).storeDurably(false).requestRecovery(true).build();
		return job;
	}
	
	private CronTrigger createTrigger(String triggerName, String triggerGroup, String cronExpression) {
		boolean flag = CronExpression.isValidExpression(cronExpression);
		if (!flag) {
			return null;
		}
		
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
		TriggerKey triggerKey = new TriggerKey(triggerName, triggerGroup);
		
		CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).startNow().withSchedule(scheduleBuilder).withPriority(5).build();
		return trigger;
	}
}
