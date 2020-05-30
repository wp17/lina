package com.github.wp17.lina.rank.module;

import com.github.wp17.lina.rank.db.mapper.ScoreRecord;
import com.github.wp17.lina.rank.logic.ScoreRankService;
import lombok.Getter;

import java.util.List;

public class RankLogicModule {
    private RankLogicModule (){}
    private static final RankLogicModule instance = new RankLogicModule();
    public static final RankLogicModule getInstance() {
        return instance;
    }

    @Getter
    private ScoreRankService scoreRankService = new ScoreRankService();

    public void saveOrUpdateScore(ScoreRecord record) {
        scoreRankService.addOrUpdate(record);
    }

    public void getScoreRecord(long roleId) {
        scoreRankService.getRecord(roleId);
    }

    public int getScoreRank(long roleId) {
       return scoreRankService.getRank(roleId);
    }

    public List<ScoreRecord> getScoreRecords(int start, int end) {
        return scoreRankService.getRecords(start, end);
    }

    public void init() {
        scoreRankService.init();
    }

    public void shutdown() {
    }
}
