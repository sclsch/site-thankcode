/**
 * All rights Reserved, Designed By ysusoft.
 *
 * @author: ${jiang_qian}
 * @date: 2019/6/14 15:58
 * @Copyright ?2019 ysusolt. All rights reserved.
 * 注意：本内容仅限于燕大燕软内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.thankcode.common.util;

import org.slf4j.MDC;

/**
 * 链路时请求的log信息uuid 统一
 *
 * @author: jiang_qian
 * @date: 2019/6/14 15:58
 * @version: V1.0
 */
public class LogUtil {

    public static void init(LogUtilKey key, String value) {
        MDC.remove(key.name());
        MDC.put(key.name(), value);
    }


    public static void initUUID(String uuid) {
        init(LogUtilKey.tradenum, uuid);
    }


    public static void cleanAll() {
        MDC.clear();
    }

    public enum LogUtilKey {
        /**
         * apmTraceId
         */
        apmTraceId,
        /**
         * tradenum
         */
        tradenum,
    }
}
