package com.thankcode.common.util;

import org.springframework.util.StringUtils;

/**
 * @author: suncl
 * @date: 2019/8/1 15:07
 * @version: V1.0
 */
public class StringIsNullHelper {
    /**
     *
     * brianhong  2008-2-21
     * @param nullStr
     * @return
     */
    public static boolean isNull(String nullStr)
    {
        if(StringUtils.isEmpty(nullStr) || "null".equals(nullStr.toLowerCase())
                ||nullStr.length()<=0) {
            return true;
        }
        //
        return false;
    }


    /**
     *
     * brianhong  2008-2-21
     * @param nullStr
     * @return
     */
    public static boolean isNotNull(String nullStr)
    {
        if(nullStr!=null && nullStr.length()>0 && !"null".equals(nullStr.toLowerCase())){
            return true;
        }else{
            return false;
        }

    }
}
