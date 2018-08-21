package com.github.wp17.lina.common.message;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AMsgProcessor {
	
	public Class<? extends IMsgProcessor> processor();
	
	public int delay() default -1;
}
