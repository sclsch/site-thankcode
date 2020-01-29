package com.thankcode.auth.server.service;

import com.thankcode.auth.api.dto.AuthDto;

/**
 * @author: suncl
 * @date: 2019-12-23 22:19:02
 * @version: V1.0
 */
public interface AuthService {
    /**
     * 根据ip验证
     * @param ip 客户端ip
     * @return
     */
    boolean authByIp(String ip);

    /**
     * 根据token验证
     * @param token
     * @return
     */
    AuthDto authByToken(String token);
}
