package com.github.wp17.lina.game.config;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.github.wp17.lina.config.common.Loadable;
import com.github.wp17.lina.config.common.Reload;
import com.github.wp17.lina.game.module.AbsModule;
import com.github.wp17.lina.game.module.ModuleInitOrder;
import com.github.wp17.lina.game.config.provider.CommonDataProvider;
import com.github.wp17.lina.game.config.provider.LineDataProvider;
import com.github.wp17.lina.util.FileUtil;

public class ConfigLoadModule implements AbsModule {
    private ConfigLoadModule() {
    }

    private static ConfigLoadModule instance = new ConfigLoadModule();

    public static final ConfigLoadModule getInstance() {
        return instance;
    }

    public static final String configDir = "D:\\project\\lina\\config\\";

    private List<Loadable> loadList = new ArrayList<Loadable>();
    private List<ReloadItem> reloadList = new ArrayList<ReloadItem>();

    @Override
    public void init() {
        register();
        for (Loadable loadable : loadList) {
            loadable.init();
        }
    }

    public void register() {
        register0(CommonDataProvider.getInstance());
        register0(LineDataProvider.getInstance());
    }

    public void register0(Loadable loadable) {
        loadList.add(loadable);
        Annotation annotation = loadable.getClass().getAnnotation(Reload.class);
        if (Objects.nonNull(annotation)) {
            ReloadItem reloadable = new ReloadItem(loadable);
            reloadList.add(reloadable);
        }
    }

    @Override
    public void shutdown() {

    }

    @Override
    public int order() {
        return ModuleInitOrder.configModule;
    }

    private class ReloadItem {
        Loadable l;
        String path;
        long lastLoadTime = System.currentTimeMillis();

        ReloadItem(Loadable loadable) {
            this.l = loadable;
            if (l.path().contains("csv")) {
                this.path = configDir + "csv\\" + l.path();
            } else if (l.path().contains("xlsx")) {
                this.path = configDir + "excel\\" + l.path();
            }
        }
    }

    public void checkReload() {
        for (int i = 0; i < reloadList.size(); i++) {
            ReloadItem r = reloadList.get(i);
            long lastModifyTime = FileUtil.lastModifyTime(r.path);
            if (r.lastLoadTime < lastModifyTime) {
                r.lastLoadTime = lastModifyTime;
                r.l.init();
            }
        }
    }
}
