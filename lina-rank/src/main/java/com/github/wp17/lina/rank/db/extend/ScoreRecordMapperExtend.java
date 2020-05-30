package com.github.wp17.lina.rank.db.extend;

import com.github.wp17.lina.rank.db.mapper.ScoreRecord;
import org.apache.ibatis.annotations.Insert;

public interface ScoreRecordMapperExtend {

    @Insert(" insert into score_rank_record (role_id, score, time)" +
            " values (#{roleId,jdbcType=BIGINT}, #{score,jdbcType=INTEGER}, #{time,jdbcType=BIGINT})" +
            " ON DUPLICATE KEY update score=#{score,jdbcType=INTEGER}," +
            " time=#{time,jdbcType=BIGINT}")
    void saveOrUpdate(ScoreRecord record);
}
