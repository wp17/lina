package com.github.wp17.lina.game.config.provider;

import com.github.wp17.lina.config.reader.ExcelReader;
import com.github.wp17.lina.config.template.LotteryTemplate;
import gnu.trove.impl.sync.TSynchronizedIntObjectMap;

import java.io.File;

public class LotteryDataProvider {
    private LotteryDataProvider() {}
    private static final LotteryDataProvider instance = new LotteryDataProvider();
    private static LotteryDataProvider getInstance() {
        return instance;
    }

    TSynchronizedIntObjectMap<LotteryTemplate> map;

    public void init() throws Exception {
        File baseDir = new File("D:\\project\\lina\\config\\excel");
        TSynchronizedIntObjectMap map = ExcelReader.load(LotteryTemplate.class, baseDir);
    }

    public LotteryTemplate getTemplate(int id) {
        return map.get(id);
    }

}
