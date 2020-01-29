package com.thankcode.auth.api;

import com.thankcode.auth.api.dto.AuthDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author: jiangqian
 * @date: 2019/11/19 0019 13:40
 * @version: V1.0
 * @review: jiangqian/2019/11/19 0019 13:40
 */
@FeignClient("AUTH-SERVER")
public interface AuthApi {

    @RequestMapping("/findAuthByToken")
    AuthDto findAuthByToken(@RequestParam(value = "token") String token);

    @RequestMapping("/findAuthByCid")
    boolean findAuthByClientId(String clientId);

}