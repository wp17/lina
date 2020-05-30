package com.github.wp17.lina.game.module;

import com.github.wp17.lina.common.interfaces.IModule;

public interface AbsModule extends IModule {
    default int order() {
        return ModuleInitOrder.middle;
    }

}
