package com.github.wp17.lina.zone;

import com.github.wp17.lina.zone.msg.ZoneMsgModule;

public class ZoneModuleManager {
    private ZoneModuleManager(){}
    private static final ZoneModuleManager instance = new ZoneModuleManager();
    public static final ZoneModuleManager getInstance() {
        return instance;
    }

    public void init() {
        ZoneMsgModule.getInstance().init();
    }

    public void shutdown() {
    }
}
