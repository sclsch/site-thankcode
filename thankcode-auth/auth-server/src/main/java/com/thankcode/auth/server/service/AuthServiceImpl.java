package com.thankcode.auth.server.service;

import com.thankcode.auth.api.dto.AuthDto;
import com.thankcode.auth.server.dao.AuthRepository;
import com.thankcode.auth.server.domain.Auth;
import com.thankcode.cache.starter.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author: suncl
 * @date: 2019-12-23 22:20:47
 * @version: V1.0
 */
@Slf4j
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthRepository authRepository;
    @Resource
    private RedisUtil redisUtil;
    @Override
    public boolean authByIp(String ip) {
        log.info("ip:{}",ip);
        Auth auth = authRepository.findByClientIp(ip);
        boolean b = auth != null;
        log.info("auth by ip:{}", b);
        return b;
    }

    @Override
    public AuthDto authByToken(String token) {
        //TODO 从redis中去token，如果有直接返回，如果没有则从数据库中取，然后再放到redis中
        AuthDto authDto= (AuthDto) redisUtil.getValByKey(token);
        if(authDto!=null){
            return authDto;
        }else {
            Auth authDb=authRepository.findByToken(token);
            if(authDb==null){
                return null;
            }
            Date validTime=authDb.getValidTime();
            Long date=validTime.getTime()-System.currentTimeMillis();
            if(date<0){
                //失效
                return null;
            }
            AuthDto authDto2=new AuthDto();
            BeanUtils.copyProperties(authDb,authDto2);

            redisUtil.setVal(token,authDto2,date/1000);
            return authDto2;

        }
    }
}
