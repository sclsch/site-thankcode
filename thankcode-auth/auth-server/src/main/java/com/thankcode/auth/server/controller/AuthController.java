package com.thankcode.auth.server.controller;

import com.thankcode.auth.api.dto.AuthDto;
import com.thankcode.auth.server.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: suncl
 * @date: 2019-12-23 20:22:04
 * @version: V1.0
 */
@RestController
@Slf4j
public class AuthController {
    @Autowired
    private AuthService authService;
    @RequestMapping("/findAuthByToken")
    public AuthDto findAuthByToken(@RequestParam(value = "token") String token){
        log.info("token:{}",token);
        AuthDto b = authService.authByToken(token);
        return b;
    }

    @RequestMapping("/findAuthByCid")
    public boolean findAuthByClientId(String clientId){
        log.info("clientId:{}",clientId);
        boolean b = authService.authByIp(clientId);
        return b;
    }
}
