package com.thankcode.common.util;
import java.util.Date;
import java.util.Random;

public class SerialNumUtils {
    /**
     * 根据传入的数字， 产生 【年月日时分秒毫秒+随机数】. 格式：yyyyMMddHHmmssSSS + '_' + 四位随机数
     *
     * @return 25位 随机数
     */
    public static String createSerialNum() {
        Random random = new Random();
        String sRand = "";
        for (int i = 0; i < 2; i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand += rand;
        }
        for (int i = 0; i < 2; i++) {
            random = new Random();
            String rand = String.valueOf(random.nextInt(10));
            sRand += rand;
        }

        StringBuilder randNoSb = new StringBuilder();
        String dateStr = DateUtils.getFormatDate(new Date(), "yyyyMMddHHmmssSSS");
        randNoSb.append(dateStr).append(sRand);

        return randNoSb.toString();
    }

}
