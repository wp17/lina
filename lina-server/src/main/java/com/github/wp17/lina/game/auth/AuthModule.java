package com.github.wp17.lina.game.auth;

import com.alibaba.fastjson.JSONObject;
import com.github.wp17.lina.game.logic.manager.login.PlatformType;
import com.github.wp17.lina.game.module.AbsModule;
import com.github.wp17.lina.util.ClassUtil;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
public class AuthModule implements AbsModule {
    private AuthModule () {}
    private static final AuthModule instance = new AuthModule();
    public static AuthModule  getInstance() {
        return instance;
    }

    private Map<PlatformType, IAuth> authMap = Maps.newHashMap();

    @Override
    public void init() {
        try {
            List<Class<?>> classList = ClassUtil.getClassesByAnnotation(
                    "com.github.wp17.lina.game.auth.impl", false, AuthType.class);
            if (!classList.isEmpty()) {
                for (Class<?> aClass : classList) {
                    IAuth auth = (IAuth) aClass.newInstance();
                    PlatformType type = aClass.getAnnotation(AuthType.class).value();
                    authMap.put(type, auth);
                }
            }
        } catch (Exception e) {
            log.error("", e);
            System.exit(0);
        }
    }

    @Override
    public void shutdown() {

    }

    public IAuth getByType(PlatformType type) {
        return authMap.get(type);
    }

    public static void main(String[] args) {
        AuthModule.getInstance().init();
        IAuth auth = AuthModule.getInstance().getByType(PlatformType.Private);
        AuthResult result = auth.auth("111", "0e3edb756f7177cacc179fc8c08f7728");
        System.out.println(JSONObject.toJSONString(result));
    }
}
