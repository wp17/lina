package com.github.wp17.lina.server.timer;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.wp17.lina.common.log.LoggerProvider;
import com.github.wp17.lina.server.config.ConfigLoadModule;

@DisallowConcurrentExecution
public class EveryMinuteTask implements Job {
	private static final Logger logger = LoggerProvider.getLogger(EveryMinuteTask.class);
	@Autowired private ConfigLoadModule configLoadModule;
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try {
			configLoadModule.checkReload();
			logger.info("everyMinuteTask Executed");
		} catch (Exception e) {
			LoggerProvider.addExceptionLog("EveryMinuteTask", e);
			throw new JobExecutionException(e);
		}
	}
}
