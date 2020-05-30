package com.github.wp17.lina.common.id;

import com.alibaba.fastjson.JSONObject;
import com.github.wp17.lina.common.util.CommonConst;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Snowflake 实现
 * 64位ID (42(毫秒)+5(DataCenterId)+5(workerId)+12(重复累加))
 * 41位的时间序列，精确到毫秒，可以使用69年
 * 10位的机器标识，最多支持部署1024个节点
 * 12位的序列号，支持每个节点每毫秒产生4096个ID序号，最高位是符号位始终为0。
 */
@Slf4j
public class IDWorker {
    /**
     * 时间起始标记点，作为基准，一般取系统的最近时间（一旦确定不能变动）
     */
    private final static long epoch = 1288834974657L;
    /**
     * 机器标识位数
     */
    private final static long workerIdBits = 5L;
    /**
     * 数据中心标识位数
     */
    private final static long dataCenterIdBits = 5L;
    /**
     * 机器ID最大值(0-31)
     */
    private final static long maxWorkerId = -1L ^ (-1L << workerIdBits);
    /**
     * 数据中心ID最大值(0-31)
     */
    private final static long maxDataCenterId = -1L ^ (-1L << dataCenterIdBits);
    /**
     * 毫秒内自增位
     */
    private final static long sequenceBits = 12L;
    /**
     * sequence上限（不包含）
     */
    private final static long maxSequence = (-1L ^ (-1L << sequenceBits)) + 1;

    private final static long sequenceMask = (-1L ^ (-1L << sequenceBits));

    /**
     * 机器ID偏左移12位
     */
    private final static long workerIdShift = sequenceBits;
    /**
     * 数据中心ID左移17位
     */
    private final static long dataCenterIdShift = sequenceBits + workerIdBits;
    /**
     * 时间毫秒左移22位
     */
    private final static long timestampLeftShift = sequenceBits + workerIdBits + dataCenterIdBits;

    /**
     * 上次生产id时间戳
     */
    private static long lastTimestamp = CommonConst.genesis;

    /**
     * 自增序列
     */
    private AtomicLong sequence = new AtomicLong(0L);

    private long sequence0 = 0L;

    private final long workerId;
    /**
     * 数据标识id部分
     */
    private final long dataCenterId;

    /**
     * @param workerId     工作机器ID
     * @param dataCenterId 序列号
     */
    public IDWorker(long workerId, long dataCenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(
                    String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (dataCenterId > maxDataCenterId || dataCenterId < 0) {
            throw new IllegalArgumentException(
                    String.format("data center Id can't be greater than %d or less than 0", maxDataCenterId));
        }
        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
    }

    /**
     * 获取下一个ID
     * sequence循环递增以便在逻辑上保证id均匀化
     * todo 似乎无法避免锁，一是 lastTimestamp+sequence保证的唯一性，还一个就是System.currentTimeMillis()不保证顺序调用的递增性
     * @return -1表示失败，调用端可重新尝试或者直接走异常处理逻辑
     */
    public synchronized long nextId0() {
        long timestamp = System.currentTimeMillis();
        if (timestamp < lastTimestamp) {
            log.error(String.format("时钟回拨异常 %d milliseconds", lastTimestamp - timestamp));
            return -1;
        }

        /**如果序列号已达最大值，则把序列号置为0，同时更新时间戳*/
        if (sequence.compareAndSet(maxSequence, 0) && timestamp == lastTimestamp) {
            while (System.currentTimeMillis() <= lastTimestamp) ;
            timestamp = System.currentTimeMillis();
        }
        lastTimestamp = timestamp;

        long seq = sequence.getAndIncrement();
        long result =
                ((timestamp - epoch) << timestampLeftShift)
                        | (dataCenterId << dataCenterIdShift)
                        | (workerId << workerIdShift)
                        | seq;

        return result;
    }

    public synchronized long nextId() {
        long timestamp = System.currentTimeMillis();
        if (timestamp < lastTimestamp) {
            log.error(String.format("时钟回拨异常 %d milliseconds", lastTimestamp - timestamp));
            return -1;
        }

        /**如果序列号已达最大值，则把序列号置为0，同时更新时间戳*/
        sequence0 = (sequence0 + 1) & sequenceMask;
        if (sequence0 == 0 && timestamp == lastTimestamp) {
            while (System.currentTimeMillis() <= lastTimestamp) ;
            timestamp = System.currentTimeMillis();
        }
        lastTimestamp = timestamp;

        long result =
                ((timestamp - epoch) << timestampLeftShift)
                        | (dataCenterId << dataCenterIdShift)
                        | (workerId << workerIdShift)
                        | sequence0;

        return result;
    }
}
