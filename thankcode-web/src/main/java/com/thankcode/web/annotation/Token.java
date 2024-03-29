package com.thankcode.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 需要自动注入Token的标识 写点注释吧
 *
 * @author: sunCl
 * @date: 2020/1/13 19:53
 * @version: V1.0
 * @review: sunCl/2020/1/13 19:53
 */
@Target(value = ElementType.PARAMETER)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Token {
}
