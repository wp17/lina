package com.github.wp17.lina.common.msg;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.google.protobuf.MessageLite;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MessageClazz {
	public Class<? extends MessageLite> value();
	public int delay() default -1;
}
