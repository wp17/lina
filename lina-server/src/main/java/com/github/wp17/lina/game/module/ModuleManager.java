package com.github.wp17.lina.game.module;

import java.util.ArrayList;
import java.util.List;

import com.github.wp17.lina.game.auth.AuthModule;
import com.github.wp17.lina.common.interfaces.IModule;
import com.github.wp17.lina.game.module.cache.CacheModule;
import com.github.wp17.lina.game.config.ConfigLoadModule;
import com.github.wp17.lina.game.module.event.EventModule;
import com.github.wp17.lina.game.module.execute.ExecutorModule;
import com.github.wp17.lina.game.module.id.IDModule;
import com.github.wp17.lina.game.module.log.LogModule;
import com.github.wp17.lina.game.module.mq.GameMQModule;
import com.github.wp17.lina.game.module.msg.GameMessageModule;
import com.github.wp17.lina.game.module.rpc.RpcModule;
import com.github.wp17.lina.game.module.db.DBModule;
import com.github.wp17.lina.game.module.line.LineServerModule;
import com.github.wp17.lina.game.module.timer.TimeTaskModule;

import static java.util.Comparator.*;

public class ModuleManager {
    private ModuleManager() {
    }

    private static final ModuleManager instance = new ModuleManager();

    public static ModuleManager getInstance() {
        return instance;
    }

    private final List<AbsModule> modules = new ArrayList<>();

    public void init() {
        register(LineServerModule.getInstance());
        register(DBModule.getInstance());
        register(ConfigLoadModule.getInstance());
        register(LogModule.getInstance());
        register(ExecutorModule.getInstance());
        register(TimeTaskModule.getInstance());
        register(GameMessageModule.getInstance());
        register(AuthModule.getInstance());
        register(EventModule.getInstance());
        register(CacheModule.getInstance());
        register(IDModule.getInstance());
        register(GameMQModule.getInstance());
        register(TimeTaskModule.getInstance());
        register(RpcModule.getInstance());

        modules.sort(comparingInt(AbsModule::order));

        for (IModule iModule : modules) iModule.init();
    }

    private void register(AbsModule module) {
        modules.add(module);
    }

    public void shutdown() {
        modules.sort((o1, o2) -> o2.order() - o1.order());
        for (IModule iModule : modules) iModule.shutdown();
    }
}
