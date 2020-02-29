package com.thankcode.web.utils;

import java.text.*;
import java.util.Date;

/**
 * All rights Reserved, Designed By ysusoft.
 *
 * @author: sunCl
 * @date: 2020/2/29 20:09
 * @Copyright ©2020 ysusoft. All rights reserved.
 * 注意：本内容仅限于燕大燕软内部传阅，禁止外泄以及用于其他的商业用途。。
 */
public class MyDateFormat extends DateFormat {

    private DateFormat dateFormat;

    private SimpleDateFormat format1 = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");

    public MyDateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    @Override
    public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
        return dateFormat.format(date, toAppendTo, fieldPosition);
    }

    @Override
    public Date parse(String source, ParsePosition pos) {

        Date date = null;

        try {

            date = format1.parse(source, pos);
        } catch (Exception e) {

            date = dateFormat.parse(source, pos);
        }

        return date;
    }

    // 主要还是装饰这个方法
    @Override
    public Date parse(String source) throws ParseException {

        Date date = null;

        try {

            // 先按我的规则来
            date = format1.parse(source);
        } catch (Exception e) {

            // 不行，那就按原先的规则吧
            date = dateFormat.parse(source);
        }

        return date;
    }

    // 这里装饰clone方法的原因是因为clone方法在jackson中也有用到
    @Override
    public Object clone() {
        Object format = dateFormat.clone();
        return new MyDateFormat((DateFormat) format);
    }
}