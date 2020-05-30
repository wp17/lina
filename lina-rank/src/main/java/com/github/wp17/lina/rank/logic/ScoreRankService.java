package com.github.wp17.lina.rank.logic;

import com.alibaba.fastjson.JSONObject;
import com.github.wp17.lina.rank.db.mapper.ScoreRecord;
import com.github.wp17.lina.rank.db.provider.ScoreRankDataProvider;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * 积分排行榜
 */
@Slf4j
public class ScoreRankService {
    private final int size = Integer.MAX_VALUE;
    private final Map<Long, ScoreRecord> p2rMap = new ConcurrentHashMap<>(100);
    private final ConcurrentSkipListSet<ScoreRecord> records = new ConcurrentSkipListSet(comparator());

    private final int cacheSize = 999;
    private volatile ScoreRecord[] cache = new ScoreRecord[cacheSize + 1];

    private Comparator comparator() {
        return (Comparator<ScoreRecord>) (o1, o2) -> {
            int r = 0;
            return (r = (int) (o2.getScore() - o1.getScore())) != 0 ? r : (int) (o2.getTime() - o1.getTime());
        };
    }

    public synchronized void addOrUpdate(ScoreRecord record) {
        if (null == record) return;
        if (record.getScore() <= 0) return;

        if (p2rMap.containsKey(record.getRoleId())) {
            ScoreRecord old = p2rMap.get(record.getRoleId());
            records.remove(old);
        }

        p2rMap.put(record.getRoleId(), record);
        records.add(record);
        ScoreRankDataProvider.getInstance().saveOrUpdateAsync(record);

        while (records.size() > size) {
            ScoreRecord last = records.pollLast();
            p2rMap.remove(last.getRoleId());
            ScoreRankDataProvider.getInstance().deleteAsync(last);
        }
        updateCache(record);
    }

    private void updateCache(ScoreRecord record) {
        int index = getRank(record.getRoleId()) - 1;
        if (index < 0) return;

        if (index <= cacheSize) {
            for (int i = cacheSize; i > index; i--) {
                cache[i] = cache[i - 1];
            }
            cache[index] = record;
        }
    }

    public List<ScoreRecord> getRecords(int start, int end) {
        if (start < end && end < cacheSize) {
            ScoreRecord[] result = Arrays.copyOfRange(cache, start, end);
            return Lists.newArrayList(result);
        }

        if (start > end || start > records.size()) {
            return Collections.emptyList();
        }

        ScoreRecord[] rankRecords = records.toArray(new ScoreRecord[records.size()]);
        if (rankRecords.length < 1) {
            return Collections.emptyList();
        }

        if (start > rankRecords.length) {
            return Collections.emptyList();
        }

        end = end > rankRecords.length ? rankRecords.length : end;
        ScoreRecord[] result = Arrays.copyOfRange(rankRecords, start, end);
        return Lists.newArrayList(result);
    }

    public ScoreRecord getRecord(long roleId) {
        return p2rMap.get(roleId);
    }

    /**
     * 获取玩家排名，从1开始
     */
    public int getRank(long roleId) {
        ScoreRecord old = p2rMap.get(roleId);
        if (Objects.isNull(old)) {
            return -1;
        }
        return records.headSet(old, true).size();
    }

    public int getTotal() {
        return records.size();
    }


    /**
     * 加载排行榜数据
     */
    public synchronized void init() {
        List<ScoreRecord> records = ScoreRankDataProvider.getInstance().selectAll();
        this.records.addAll(records);
        cache = getRecords(0, cacheSize).toArray(cache);
        records.forEach(record -> {
            p2rMap.putIfAbsent(record.getRoleId(), record);
        });
    }

    public synchronized List<ScoreRecord> getRecords2(int start, int end) {
        List<ScoreRecord> result = new ArrayList<>(end - start);
        int flag = 0;
        ScoreRecord record;
        while (null != (record = records.iterator().next())) {
            if (flag > end) break;
            if (flag >= start) {
                result.add(record);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        ScoreRecord[] records = new ScoreRecord[10];
        for (int i = 0; i < 5; i++) {
            records[i] = new ScoreRecord();
        }

        List<ScoreRecord> records1 = Lists.newArrayList(records);

        records1.removeIf(r -> null == r);
        System.out.println(JSONObject.toJSONString(records1));
    }
}
