package com.github.wp17.lina.game.auth;

import com.github.wp17.lina.game.logic.manager.login.PlatformType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthType {
    PlatformType value();
}
