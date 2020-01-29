package com.thankcode;

import com.thankcode.web.resolvers.HttpEntityArg;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * @author: suncl
 * @date: 2019-12-22 19:41:06
 * @version: V1.0
 */
@SpringBootApplication(exclude = {HibernateJpaAutoConfiguration.class,
        DataSourceAutoConfiguration.class})
public class WebApplication   {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
    /**
     *     必须new 一个RestTemplate并放入spring容器当中,否则启动时报错
     */
    @Bean
    public RestTemplate restTemplate() {
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setConnectionRequestTimeout(30 * 1000);
        httpRequestFactory.setConnectTimeout(30 * 3000);
        httpRequestFactory.setReadTimeout(30 * 3000);
        return new RestTemplate(httpRequestFactory);
    }

}
