/**
 * All rights Reserved, Designed By thankcode.
 *
 * @author: suncl
 */
package com.thankcode.eureka;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 注册中心
 * @author: jiangqian
 * @date: 2019/11/4 0004 17:30
 * @version: V1.0
 * @review: jiangqian/2019/11/4 0004 17:30
 */
@EnableEurekaServer
@SpringBootApplication(exclude = {HibernateJpaAutoConfiguration.class,
                                  DataSourceAutoConfiguration.class
                                  })
public class EurekaApplication {
    public static void main(String[] args) {

        new SpringApplicationBuilder(EurekaApplication.class).web(true).run(args);

    }
}
