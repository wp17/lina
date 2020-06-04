package com.github.wp17.lina.game.module.line;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.github.wp17.lina.common.log.LoggerProvider;
import com.github.wp17.lina.config.template.LineTemplate;
import com.github.wp17.lina.game.module.AbsModule;
import com.github.wp17.lina.game.module.ModuleInitOrder;
import com.github.wp17.lina.game.config.provider.LineDataProvider;
import com.github.wp17.lina.game.logic.Role;
import com.google.common.collect.Lists;

public class LineServerModule implements AbsModule {
    private LineServerModule() {
    }

    private static LineServerModule instance = new LineServerModule();

    public static final LineServerModule getInstance() {
        return instance;
    }

    private final AtomicInteger num = new AtomicInteger(0);
    private final Map<Long, Role> roles = new ConcurrentHashMap<Long, Role>();
    private final Map<Integer, LineServer> lineServers = new HashMap<Integer, LineServer>();

    @Override
    public void init() {
        List<LineTemplate> lineList =
                Lists.newArrayList(LineDataProvider.getInstance().getTemplates())
                .stream()
//                .filter(data -> ServerHolder.server().getId() == data.getServer_id())
                .collect(Collectors.toList());

        if (num.compareAndSet(0, lineList.size())) {
            CountDownLatch latch = new CountDownLatch(num.intValue());
            for (LineTemplate data : lineList) {
                LineServer lineServer = new LineServer();
                lineServer.setCountDownLatch(latch);
                lineServer.setLineTemplate(data);
                lineServer.startup();
                lineServers.put(lineServer.getId(), lineServer);
            }
        }else {
            LoggerProvider.addExceptionLog("初始化分线服务器失败");
            System.exit(0);
        }
    }

    @Override
    public void shutdown() {
        for (LineServer lineServer : lineServers.values()) {
            lineServer.close();
        }
    }

    public void addRole(Role role) {
        roles.put(role.getRoleUuid(), role);
        LineServer lineServer = selectServer(role.getRoleUuid());
        lineServer.addRole(role);
        role.setLineServer(lineServer);
    }

    public void roleDownLine(Role role) {
        if (Objects.nonNull(role)) {
            roles.remove(role.getRoleUuid());
            role.getLineServer().remRole(role);
            role.setLineServer(null);
        }
    }
//
//    public void remRole(Role role) {
//        if (Objects.nonNull(role)) {
//            roles.remove(role.getRoleUuid());
//            role.getLineServer().remRole(role);
//            role.setLineServer(null);
//        }
//    }

    public Role getRole(Long roleUuId) {
        return roles.get(roleUuId);
    }

    private LineServer selectServer(Long roleUuId) {
        int index = (int) (roleUuId % num.get());
        return lineServers.get(index);
    }

    @Override
    public int order() {
        return ModuleInitOrder.lowest;
    }
}
