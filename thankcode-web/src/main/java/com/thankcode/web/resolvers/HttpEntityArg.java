package com.thankcode.web.resolvers;

import com.thankcode.blog.api.dto.BlogDTO;
import com.thankcode.web.annotation.Token;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer; /**
 * All rights Reserved, Designed By ysusoft.
 *
 * @author: sunCl
 * @date: 2020/1/13 19:51
 * @Copyright ©2020 ysusoft. All rights reserved.
 * 注意：本内容仅限于燕大燕软内部传阅，禁止外泄以及用于其他的商业用途。。
 */;

/** 处理HttpEntity的参数处理器 写点注释吧
 * @author: sunCl
 * @date: 2020/1/13 19:51
 * @version: V1.0
 * @review: sunCl/2020/1/13 19:51 */
public class HttpEntityArg  implements HandlerMethodArgumentResolver {


    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        return parameter.hasParameterAnnotation(Token.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
            //把你封装Token的代码copy过来..
        String token = "*^zH9vh74IWg5h4s";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/json; charset=UTF-8"));
        headers.set("token" , token);

        return headers;
    }
}