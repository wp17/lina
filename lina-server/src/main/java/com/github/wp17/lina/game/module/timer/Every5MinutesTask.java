package com.github.wp17.lina.game.module.timer;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.github.wp17.lina.common.log.LoggerProvider;

public class Every5MinutesTask implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try {
			
		} catch (Exception e) {
			LoggerProvider.addExceptionLog("Every5MinutesTask", e);
			throw new JobExecutionException(e);
		}
	}

}
