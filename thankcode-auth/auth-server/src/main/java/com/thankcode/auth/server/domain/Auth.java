package com.thankcode.auth.server.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author: suncl
 * @date: 2019-12-23 22:14:58
 * @version: V1.0
 */
@Entity
@Getter
@Setter
public class Auth {
    @Id
    @GeneratedValue
    private Long id;
    private String clientIp;
    private String token;
    private Date validTime;
    private String serverIds;
}
