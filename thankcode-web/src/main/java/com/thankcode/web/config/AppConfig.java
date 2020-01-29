package com.thankcode.web.config;/**
 * All rights Reserved, Designed By ysusoft.
 *
 * @author: sunCl
 * @date: 2020/1/13 20:20
 * @Copyright ©2020 ysusoft. All rights reserved.
 * 注意：本内容仅限于燕大燕软内部传阅，禁止外泄以及用于其他的商业用途。。
 */

import com.thankcode.web.resolvers.HttpEntityArg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.ArrayList;
import java.util.List;

/** * 配置Springmvc 写点注释吧
 * @author: sunCl
 * @date: 2020/1/13 20:20
 * @version: V1.0
 * @review: sunCl/2020/1/13 20:20 */
@Configuration
public class AppConfig extends WebMvcConfigurerAdapter {
    @Autowired
    public void initArgumentResolvers(RequestMappingHandlerAdapter requestMappingHandlerAdapter) {
        List<HandlerMethodArgumentResolver> argumentResolvers = new ArrayList<>(requestMappingHandlerAdapter.getArgumentResolvers());
        List<HandlerMethodArgumentResolver> customResolvers = requestMappingHandlerAdapter.getCustomArgumentResolvers();
        argumentResolvers.removeAll(customResolvers);
        argumentResolvers.addAll(0, customResolvers);
        requestMappingHandlerAdapter.setArgumentResolvers(argumentResolvers);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new HttpEntityArg());
    }
}