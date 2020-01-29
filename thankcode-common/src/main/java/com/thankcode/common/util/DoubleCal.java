package com.thankcode.common.util;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;

/**
 * Double计算
 * @author: suncl
 * @date: 2019/8/1 15:07
 * @version: V1.0
 */
public class DoubleCal {

    /**
     * 字符串转Double
     * @param doubleStr
     * @return
     */
    public static Double parseDouble(String doubleStr){
        if(StringUtils.isEmpty(doubleStr)){
            return 0d;
        }else{
            return Double.parseDouble(doubleStr);
        }
    }

    /**
     * 加法运算
     * @param m1
     * @param m2
     * @return
     */
    public static Double addDouble(Double m1, Double m2) {
        m1 = m1 == null ? 0d : m1;
        m2 = m2 == null ? 0d  :m2;
        BigDecimal p1 = new BigDecimal(Double.toString(m1));
        BigDecimal p2 = new BigDecimal(Double.toString(m2));
        return p1.add(p2).doubleValue();
    }

    /**
     * 减法运算
     * @param m1
     * @param m2
     * @return
     */
    public static Double subDouble(Double m1, Double m2) {
        m1 = m1 == null ? 0d : m1;
        m1 = m2 == null ? 0d  :m2;
        BigDecimal p1 = new BigDecimal(Double.toString(m1));
        BigDecimal p2 = new BigDecimal(Double.toString(m2));
        return p1.subtract(p2).doubleValue();
    }

    /**
     * 乘法运算
     * @param m1
     * @param m2
     * @return
     */
    public static Double mul(Double m1, Double m2) {
        m1 = m1 == null ? 0d : m1;
        m1 = m2 == null ? 0d  :m2;
        BigDecimal p1 = new BigDecimal(Double.toString(m1));
        BigDecimal p2 = new BigDecimal(Double.toString(m2));
        return p1.multiply(p2).doubleValue();
    }

    /**
     *  除法运算
     *   @param   m1
     *   @param   m2
     *   @param   scale
     *   @return
     */
    public static Double div(Double m1, Double m2, int scale) {
        m1 = m1 == null ? 0d : m1;
        m1 = m2 == null ? 0d  :m2;
        if (m2 < 0) {
            throw new IllegalArgumentException("Parameter error");
        }
        BigDecimal p1 = new BigDecimal(Double.toString(m1));
        BigDecimal p2 = new BigDecimal(Double.toString(m2));
        return p1.divide(p2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
