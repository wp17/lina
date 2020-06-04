package com.github.wp17.lina.game.logic.manager.lottery;

import com.github.wp17.lina.util.RandomUtil;
import com.github.wp17.lina.util.StringUtil;
import com.google.common.collect.Lists;
import lombok.Getter;
import org.apache.commons.collections4.IterableUtils;

import java.util.*;

@Getter
public class LootListLogicTemplate extends LootListTemplate {
    private static final Random RANDOM = new Random();

    private FixedDrop fixedDrop = new FixedDrop();//固定掉落组
    private WeightDrop weightDrop = new WeightDrop();//权重掉落组
    private ProbabilisticDrop probabilisticDrop = new ProbabilisticDrop();//概率掉落组

    public void init() {
        if (FixedSW == 1) fixedDrop.onOff = true;
        fixedDrop.times = FixedTime;
        if (Objects.nonNull(FixedLoot)) {
            for (String temp : FixedLoot) {
                if (StringUtil.isNotEmpty(temp) && !temp.equals("0")) {
                    String[] values = temp.split("_");
                    int itemId = Integer.valueOf(values[0]);
                    int min = Integer.valueOf(values[1]);
                    int max = Integer.valueOf(values[2]);
                    FixedDropEntry entry = new FixedDropEntry(itemId, min, max);
                    fixedDrop.entries.add(entry);
                }
            }
        }

        if (WeightSW == 1) weightDrop.onOff = true;
        weightDrop.times = WeightTime;
        if (Objects.nonNull(WeightLoot)) {
            int weightCounter = 0;
            for (String temp : WeightLoot) {
                if (StringUtil.isNotEmpty(temp) && !temp.equals("0")) {
                    String[] values = temp.split("_");
                    int itemId = Integer.valueOf(values[0]);
                    int min = Integer.valueOf(values[1]);
                    int max = Integer.valueOf(values[2]);
                    int weight = Integer.valueOf(values[3]);

                    WeightDropEntry entry = new WeightDropEntry(itemId, min, max, weight, weightCounter);
                    weightDrop.entries.add(entry);
                    weightCounter += weight;
                }
            }
            weightDrop.totalWeight = weightCounter;
        }

        if (RandomSW == 1) probabilisticDrop.onOff = true;
        probabilisticDrop.times = RandomTime;
        probabilisticDrop.maxNum = 100;
        if (Objects.nonNull(RandomLoot)) {
            for (String temp : RandomLoot) {
                if (StringUtil.isNotEmpty(temp) && !temp.equals("0")) {
                    String[] values = temp.split("_");
                    int itemId = Integer.valueOf(values[0]);
                    int min = Integer.valueOf(values[1]);
                    int max = Integer.valueOf(values[2]);
                    int probability = Integer.valueOf(values[3]);
                    ProbabilisticDropEntry entry = new ProbabilisticDropEntry(itemId, min, max, probability);
                    probabilisticDrop.entries.add(entry);
                }
            }
        }
    }

    public List<DropItem> drop() {
        List<DropItem> items = Lists.newArrayList();
        items.addAll(fixedDrop.drop());
        items.addAll(weightDrop.drop());
        items.addAll(probabilisticDrop.drop());
        return items;
    }

    /**
     * 合并相同掉落项
     * @return
     */
    public List<DropItem> dropMerge() {
        List<DropItem> items = Lists.newArrayList();
        fixedDrop.drop().forEach(item -> addItemMerge(items, item));
        weightDrop.drop().forEach(item -> addItemMerge(items, item));
        probabilisticDrop.drop().forEach(item -> addItemMerge(items, item));
        return items;
    }

    //合并相同物品
    private void addItemMerge(List<DropItem> list, DropItem item) {
        DropItem item2 = IterableUtils.find(list, item1 -> item1.itemId == item.itemId);
        if (Objects.nonNull(item2)) {
            item2.count += item.count;
        } else {
            list.add(item);
        }
    }

    //不合并相同物品
    private void addItem(List<DropItem> list, int itemId, int count) {
        DropItem item = new DropItem();
        item.itemId = itemId;
        item.count = count;
        list.add(item);
    }


    public static class DropItem {
        public int itemId;
        public int count;
    }

    public abstract class BaseDrop {
        public boolean onOff;
        public int times;
        public List<BaseDropEntry> entries = new ArrayList<>();

        protected abstract List<DropItem> drop();

        protected void dropImpl(List<DropItem> results, int randomBound) {
            for (BaseDropEntry entry : entries) {
                int random = RandomUtil.rand(RANDOM, randomBound);
                if (entry.isHit(random)) {
                    addItem(results, entry.itemId, entry.randomCount());
                }
            }
        }
    }

    public class FixedDrop extends BaseDrop {
        @Override
        public List<DropItem> drop() {
            if (!onOff) return Collections.emptyList();
            List<DropItem> results = new ArrayList<>();
            if (!entries.isEmpty()) {
                multExec(times, () -> dropImpl(results, 1));
            }
            return results;
        }
    }

    public class WeightDrop extends BaseDrop {
        private int totalWeight = Integer.MAX_VALUE;

        public void add(BaseDropEntry entry) {
            entries.add(entry);
        }

        @Override
        public List<DropItem> drop() {
            if (!onOff) return Collections.emptyList();
            List<DropItem> results = new ArrayList<>();
            if (!entries.isEmpty()) {
                multExec(times, () -> dropImpl(results, totalWeight));
            }
            return results;
        }

        @Override
        public void dropImpl(List<DropItem> results, int totalWeight) {
            int random = RandomUtil.rand(RANDOM, totalWeight);
            for (BaseDropEntry entry : entries) {
                if (entry.isHit(random)) {
                    addItem(results, entry.itemId, entry.randomCount());
                    break;
                }
            }
        }
    }

    public class ProbabilisticDrop extends BaseDrop {
        /**该随机组最多随机到道具的次数*/
        int maxNum;
        private static final int totalProbability = 10000;

        @Override
        public List<DropItem> drop() {
            if (!onOff) return Collections.emptyList();
            List<DropItem> results = new ArrayList<>();
            if (!entries.isEmpty()) {
                multExec(times, () -> dropImpl(results, totalProbability));
            }
            if (results.size() > maxNum) {
                return results.subList(0, maxNum);
            }
            return results;
        }
    }

    @Getter
    public abstract class BaseDropEntry {
        int itemId;
        int min;
        int max;

        public BaseDropEntry(int itemId, int min, int max) {
            this.itemId = itemId;
            this.min = min;
            this.max = max;
        }

        abstract boolean isHit(int random);

        public int randomCount() {
            return RandomUtil.rand(RANDOM, min, max);
        }
    }

    public class FixedDropEntry extends BaseDropEntry {
        public FixedDropEntry(int itemId, int min, int max) {
            super(itemId, min, max);
        }

        @Override
        boolean isHit(int random) {
            return true;
        }
    }

    public class WeightDropEntry extends BaseDropEntry {
        private int weight;
        private int start;
        private int end;

        public WeightDropEntry(int itemId, int min, int max, int weight, int start) {
            super(itemId, min, max);
            this.weight = weight;
            this.start = start;
            this.end = start + weight;
        }

        @Override
        public boolean isHit(int random) {
            if (weight == 0) return false;
            return random > start && random <= end;
        }
    }

    public class ProbabilisticDropEntry extends BaseDropEntry {
        private int probability;

        public ProbabilisticDropEntry(int itemId, int min, int max, int probability) {
            super(itemId, min, max);
            this.probability = probability;
        }

        @Override
        public boolean isHit(int random) {
            return random <= probability;
        }
    }

    private static void multExec(int times, Runnable runnable) {
        for (int i = 0; i < times; i++) {
            runnable.run();
        }
    }
}
