package com.thankcode.web.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.thankcode.common.util.SpringUtil;
import com.thankcode.web.exception.ValidateException;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

/**
 * 验证
 * @author suncl
 */
public class ValidateUtil {

    private static Validator validator= (Validator) SpringUtil.getBean("validator");

    public static void validate(String content,Class clazz) {
        try {
            Object obj=JSON.parseObject(content,clazz);
            Set<ConstraintViolation<Object>> constraintViolations = validator.validate(obj);
            if (constraintViolations.size() > 0) {
                String mes = constraintViolations.iterator().next().getMessage();
                throw new ValidateException(mes);
            }

        }catch (JSONException json){
            throw  new JSONException("字符串不符合json格式或者所传字段非要求格式");
        }
    }
}
