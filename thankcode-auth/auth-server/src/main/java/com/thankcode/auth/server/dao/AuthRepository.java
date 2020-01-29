package com.thankcode.auth.server.dao;

import com.thankcode.auth.server.domain.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author: suncl
 * @date: 2019-12-23 22:16:56
 * @version: V1.0
 */
public interface AuthRepository extends JpaRepository<Auth, Long>, JpaSpecificationExecutor {
    Auth findByClientIp(String clientIp);
    Auth findByToken(String token);
}
