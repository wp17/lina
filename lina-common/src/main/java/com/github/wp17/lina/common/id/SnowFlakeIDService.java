package com.github.wp17.lina.common.id;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;

/**
 * snowFlake算法实现的id生成服务
 */
public class SnowFlakeIDService implements IIDService {
    private IDWorker idWorker;
    private final int workerId;
    private final int dcId;

    public SnowFlakeIDService(int workerId, int dcId) {
        this.workerId = workerId;
        this.dcId = dcId;
    }

    public void init() {
        idWorker = new IDWorker(workerId, dcId);
    }

    @Override
    public long genId() {
        return idWorker.nextId();
    }

    private static long getMaxWorkerId(long dataCenterId, long maxWorkerId) {

        StringBuffer mpid = new StringBuffer();
        mpid.append(dataCenterId);
        String name = ManagementFactory.getRuntimeMXBean().getName();
        if (!name.isEmpty()) {
            mpid.append(name.split("@")[0]);
        }
        /*
         * MAC + PID 的 hashcode 获取16个低位
         */
        return (mpid.toString().hashCode() & 0xffff) % (maxWorkerId + 1);
    }

    private static long getDataCenterId(long maxDataCenterId) {
        long id = 0L;
        try {
            InetAddress ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            if (network == null) {
                id = 1L;
            } else {
                byte[] mac = network.getHardwareAddress();
                id = ((0x000000FF & (long) mac[mac.length - 1])
                        | (0x0000FF00 & (((long) mac[mac.length - 2]) << 8))) >> 6;
                id = id % (maxDataCenterId + 1);
            }
        } catch (Exception e) {
            System.out.println(" getDatacenterId: " + e.getMessage());
        }
        return id;
    }
}
