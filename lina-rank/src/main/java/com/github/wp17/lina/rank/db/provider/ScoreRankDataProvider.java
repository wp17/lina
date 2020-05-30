package com.github.wp17.lina.rank.db.provider;

import com.github.wp17.lina.rank.Executor.RankExecutorModule;
import com.github.wp17.lina.rank.db.mapper.ScoreRecord;
import com.github.wp17.lina.rank.db.mapper.ScoreRecordMapper;
import com.github.wp17.lina.rank.module.RankDBModule;

import java.util.List;

public class ScoreRankDataProvider {
    private ScoreRankDataProvider() {}
    private static final ScoreRankDataProvider instance = new ScoreRankDataProvider();
    public static ScoreRankDataProvider getInstance() {
        return instance;
    }

    public ScoreRecordMapper getMapper() {
        return RankDBModule.getInstance().getMapper(ScoreRecordMapper.class);
    }

    public void add(ScoreRecord rank) {
        getMapper().insert(rank);
    }

    public void addAsync(ScoreRecord record) {
        RankExecutorModule.getInstance().addDBTask(() -> add(record));
    }

    public void  delete(ScoreRecord record) {
        getMapper().deleteByPrimaryKey(record.getRoleId());
    }

    public void  deleteAsync(ScoreRecord record) {
        RankExecutorModule.getInstance().addDBTask(() ->delete(record));
    }

    public void update(ScoreRecord rank) {
        getMapper().updateByPrimaryKey(rank);
    }

    public void updateAsync(ScoreRecord rank) {
        RankExecutorModule.getInstance().addDBTask(() -> update(rank));
    }

    public void saveOrUpdate(ScoreRecord record) {
        getMapper().saveOrUpdate(record);
    }

    public void saveOrUpdateAsync(ScoreRecord record) {
        RankExecutorModule.getInstance().addDBTask(() -> saveOrUpdate(record));
    }

    public List<ScoreRecord> selectAll() {
        return getMapper().selectAll();
    }
}
