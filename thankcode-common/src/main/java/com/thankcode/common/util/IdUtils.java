/**
 * All rights Reserved, Designed By ysusoft.
 *
 * @author: ${jiang_qian}
 * @date: 2019/6/11 12:39
 * @Copyright ?2019 ysusolt. All rights reserved.
 * 注意：本内容仅限于燕大燕软内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.thankcode.common.util;

import java.util.UUID;

/**
 * id生成器
 *
 * @author: jiang_qian
 * @date: 2019/6/11 12:39
 * @version: V1.0
 */
public class IdUtils {
    private static IdWorker ID = new IdWorker(1);

    /**
     * 生产随机ID
     *
     * @return
     */
    public static long getNextId() {
        return ID.nextId();
    }

    /**
     * 生产随机ID
     *
     * @param wordId
     * @return
     */
    public static long getNextId(int wordId) {
        ID = new IdWorker(wordId);
        return ID.nextId();
    }

    /**
     * @return
     */
    public static synchronized String get32UUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
