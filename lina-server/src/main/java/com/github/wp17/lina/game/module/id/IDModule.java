package com.github.wp17.lina.game.module.id;

import com.github.wp17.lina.common.id.SnowFlakeIDService;
import com.github.wp17.lina.game.module.AbsModule;
import com.github.wp17.lina.game.module.ModuleInitOrder;

public class IDModule implements AbsModule {
    private IDModule() {}
    private static final IDModule instance = new IDModule();
    public static IDModule getInstance() {
        return instance;
    }

    private SnowFlakeIDService roleIdService;

    @Override
    public void init() {
        roleIdService = new SnowFlakeIDService(1, 1);
        roleIdService.init();
    }

    @Override
    public void shutdown() {

    }

    @Override
    public int order() {
        return ModuleInitOrder.highest;
    }

    public long genRoleId() {
        return roleIdService.genId();
    }
}
