/**
 * All rights Reserved, Designed By ysusoft.
 *
 * @author: ${jiang_qian}
 * @date: 2019/8/6 15:59
 * @Copyright ?2019 ysusolt. All rights reserved.
 * 注意：本内容仅限于燕大燕软内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.thankcode.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * ${TODO}
 *
 * @author: jiang_qian
 * @date: 2019/8/6 15:59
 * @version: V1.0
 */
@Configuration
public class RestTemplateConfig {
    @Bean
    public SimpleClientHttpRequestFactory httpClientFactory() {
        SimpleClientHttpRequestFactory httpRequestFactory = new SimpleClientHttpRequestFactory();
        httpRequestFactory.setReadTimeout(60000);
        httpRequestFactory.setConnectTimeout(60000);
        return httpRequestFactory;
    }

    @Bean
    public RestTemplate restTemplate(SimpleClientHttpRequestFactory httpClientFactory) {
        RestTemplate restTemplate = new RestTemplate(httpClientFactory);
        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
        //设置为false就可以修改header中的accept-charset属性
        stringHttpMessageConverter.setWriteAcceptCharset(false);
        restTemplate.getMessageConverters().add(0,stringHttpMessageConverter);
        return restTemplate;
    }
}
