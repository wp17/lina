package com.github.wp17.lina.zone.register;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**管理游戏服的注册信息*/
public class RegisterModule {
    private RegisterModule() {}
    private static final RegisterModule instance = new RegisterModule();
    public static RegisterModule getInstance() {
        return instance;
    }

    private Map<ServerTye, List<RegisterItem>> map = new ConcurrentHashMap<>();
    private Map<String, RegisterItem> itemMap = new ConcurrentHashMap<>();

    public void register(String host, int port, ServerTye serverTye) {
        RegisterItem item = new RegisterItem(serverTye, host, port);
        itemMap.put(item.genKey(), item);

        List<RegisterItem> items = map.get(serverTye);
        if (null == items) {
            items = Lists.newCopyOnWriteArrayList();
            map.put(serverTye, items);
        }
        items.add(item);
    }
}
