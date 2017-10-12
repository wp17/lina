package com.github.wp17.lina.timer.task;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;

import com.github.wp17.lina.config.ConfigLoadModule;
import com.github.wp17.lina.log.LoggerProvider;

@DisallowConcurrentExecution
public class EveryMinuteTask implements Job {
	private static final Logger logger = LoggerProvider.getLogger(EveryMinuteTask.class);
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try {
			ConfigLoadModule.getInstance().checkReload();
		} catch (Exception e) {
			LoggerProvider.addExceptionLog("EveryMinuteTask", e);
			throw new JobExecutionException(e);
		}
		
		logger.info("everyMinuteTask Executed");
	}
}
