/**
 * All rights Reserved, Designed By ysusoft.
 *
 * @author: ${jiang_qian}
 * @date: 2019/6/11 12:41
 * @Copyright ?2019 ysusolt. All rights reserved.
 * 注意：本内容仅限于燕大燕软内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.thankcode.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ID
 *
 * @author: jiang_qian
 * @date: 2019/6/11 12:41
 * @version: V1.0
 */
public class IdWorker {
    private Logger logger = LoggerFactory.getLogger(getClass());

    // 时间起始标记点，作为基准，一般取系统的最近时间（一旦确定不能变动）
    private static final long TWEPOCH = 1482831049587L;
    // 机器标识位数
    private static final long WORKERIDBITS = 5L;
    // 数据中心标识位数
    private static final long DATACENTERIDBITS = 5L;
    // 机器ID最大值
    private static final long MAXWORKERID = -1L ^ -1L << WORKERIDBITS;
    // 毫秒内自增位
    private static final long SEQUENCEBITS = 12L;
    // 机器ID偏左移12位
    private static final long WORKERIDSHIFT = SEQUENCEBITS;
    // 时间毫秒左移22位
    private static final long TIMESTAMPLEFTSHIFT = SEQUENCEBITS + WORKERIDBITS + DATACENTERIDBITS;

    private static final long SEQUENCEMASK = -1L ^ (-1L << SEQUENCEBITS);
    /* 上次生产id时间戳 */
    private static long lastTimestamp = -1L;
    // 0，并发控制
    private long sequence = 0L;

    private final long workerId;

    public IdWorker(final long workerId) {
        super();
        if (workerId > MAXWORKERID || workerId < 0) {
            throw new IllegalArgumentException(
                    String.format("worker Id can't be greater than %d or less than 0", MAXWORKERID));
        }
        this.workerId = workerId;
    }

    /**
     * @return
     */
    public synchronized long nextId() {
        long timestamp = timeGen();
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & SEQUENCEMASK;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }
        if (timestamp < lastTimestamp) {
            try {
                throw new Exception(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds",
                        lastTimestamp - timestamp));
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        lastTimestamp = timestamp;
        long nextId = ((timestamp - TWEPOCH << TIMESTAMPLEFTSHIFT)) | (workerId << WORKERIDSHIFT)
                | (sequence);
        return nextId;
    }

    /**
     * @param lastTimestamp
     * @return
     */
    private long tilNextMillis(final long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * @return
     */
    private long timeGen() {
        return System.currentTimeMillis();
    }

    public static void main(String[] args) {
        System.out.println(MAXWORKERID);
    }
}
