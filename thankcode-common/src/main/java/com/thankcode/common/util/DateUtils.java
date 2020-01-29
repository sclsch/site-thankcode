/**
 * All rights Reserved, Designed By ysusoft.
 *
 * @author: ${jiang_qian}
 * @date: 2019/7/23 13:16
 * @Copyright ?2019 ysusolt. All rights reserved.
 * 注意：本内容仅限于燕大燕软内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.thankcode.common.util;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author: jiang_qian
 * @date: 2019/7/23 13:16
 * @version: V1.0
 */
public class DateUtils {
    /**
     * 得到当前时间的日期  YYYYMMDD
     */
    public static String getCurDT(){
        return getCurTime("yyyyMMdd");
    }

    /**
     * 得到当前时间 格式  HHMMSS
     */
    public static String getCurTM(){
        return getCurTime("HHmmss");
    }

    /**
     * 当前时间
     */
    public static String getCurTime(String format) {
        StringBuilder str = new StringBuilder();
        Date ca = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        str.append(sdf.format(ca)) ;
        return str.toString() ;
    }

    /**
     * 获得当地时间，返回Timestamp格式
     */
    public static Timestamp getCurTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * 根据规则格式化时间
     * @param date
     * @param format
     * @return
     */

    public static String getFormatDate(Date date,String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return  sdf.format(date);
    }

    /**
     * 后一天
     * @return
     */

    public static String getLastDay(){
        Calendar date = Calendar.getInstance();
        date.add(Calendar.DATE, -1);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        return formatter.format(date.getTime());
    }

    /**
     * 字符串变时间
     */

    public static Date strToDate(String format,String dateStr )   {
        if(StringUtils.isEmpty(dateStr)){
            return null;
        }
        try {
            return new SimpleDateFormat(format).parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {

            System.out.print(strToDate("yyyyMMDDHHmmss","20190826101244"));

    }

}
