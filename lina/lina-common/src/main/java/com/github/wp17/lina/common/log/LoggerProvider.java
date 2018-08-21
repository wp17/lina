package com.github.wp17.lina.common.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerProvider {
	
	private class ExceptionLog{}
	private final static Logger exceptionLogger = getLogger(ExceptionLog.class);
	public static void addExceptionLog(String message, Throwable e){
		if (exceptionLogger.isErrorEnabled()) {
			exceptionLogger.error(message, e);
		}
	}
	
	public static void addExceptionLog(Throwable e){
		if (exceptionLogger.isErrorEnabled()) {
			exceptionLogger.error("", e);
		}
	}
	
	public static Logger getLogger(Class<?> clazz) {
		return LoggerFactory.getLogger(clazz);
	}
}