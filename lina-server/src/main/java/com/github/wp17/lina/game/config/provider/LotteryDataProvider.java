package com.github.wp17.lina.game.config.provider;

import com.github.wp17.lina.config.reader.ExcelReader;
import com.github.wp17.lina.config.template.LotteryTemplate;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

import java.io.File;

public class LotteryDataProvider {
    private LotteryDataProvider() {}
    private static final LotteryDataProvider instance = new LotteryDataProvider();
    private static LotteryDataProvider getInstance() {
        return instance;
    }

    TIntObjectMap<LotteryLogicTemplate> map = new TIntObjectHashMap<>();

    public void init() throws Exception {
        File baseDir = new File("D:\\project\\lina\\config\\excel");
        TIntObjectHashMap<LotteryTemplate> baseMap = ExcelReader.load(LotteryTemplate.class, baseDir);
        baseMap.valueCollection().forEach(base -> {
            LotteryLogicTemplate logicTemplate = new LotteryLogicTemplate(base.getId());
            logicTemplate.init(base);
            map.put(logicTemplate.getId(), logicTemplate);
        });
    }

    public LotteryLogicTemplate getTemplate(int id) {
        return map.get(id);
    }

}
