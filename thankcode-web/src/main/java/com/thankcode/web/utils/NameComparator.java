package com.thankcode.web.utils;

import java.util.Comparator;
import java.util.Hashtable;

/**
 * All rights Reserved, Designed By ysusoft.
 *
 * @author: sunCl
 * @date: 2020/2/29 16:17
 * @Copyright ©2020 ysusoft. All rights reserved.
 * 注意：本内容仅限于燕大燕软内部传阅，禁止外泄以及用于其他的商业用途。。
 */

public class NameComparator implements Comparator {
    @Override
    public int compare(Object a, Object b) {
        Hashtable hashA = (Hashtable) a;
        Hashtable hashB = (Hashtable) b;
        if (((Boolean) hashA.get("is_dir")) && !((Boolean) hashB.get("is_dir"))) {
            return -1;
        } else if (!((Boolean) hashA.get("is_dir")) && ((Boolean) hashB.get("is_dir"))) {
            return 1;
        } else {
            return ((String) hashA.get("filename")).compareTo((String) hashB.get("filename"));
        }
    }
}

