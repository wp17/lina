package com.github.wp17.lina.rank.db.mapper;

import com.github.wp17.lina.rank.db.extend.ScoreRecordMapperExtend;
import com.github.wp17.lina.rank.db.mapper.ScoreRecord;
import java.util.List;

public interface ScoreRecordMapper extends ScoreRecordMapperExtend {
    int deleteByPrimaryKey(Long roleId);

    int insert(ScoreRecord record);

    ScoreRecord selectByPrimaryKey(Long roleId);

    List<ScoreRecord> selectAll();

    int updateByPrimaryKey(ScoreRecord record);
}