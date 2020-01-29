/**
 * All rights Reserved, Designed By ysusolt.
 *
 * @author: jiangqian
 * @date: 2019/11/19 0019
 * @Copyright ?2019 ysusolt. All rights reserved.
 * 注意：本内容仅限于燕大燕软内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.thankcode.auth.api.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: jiangqian
 * @date: 2019/11/19 0019 13:38
 * @version: V1.0
 * @review: jiangqian/2019/11/19 0019 13:38
 */
public class AuthDto implements Serializable {

    /**
     * 客户端ip
     */
    private String clientId;
    /**
     * token
     */
    private String token;
    /**
     * 服务ids
     */
    private String serverIds;
    /**
     * 描述
     */
    private String des;
    /**
     * 有效时间
     */
    private Date validTime;

    private String sex;

    private String respCode;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getServerIds() {
        return serverIds;
    }

    public void setServerIds(String serverIds) {
        this.serverIds = serverIds;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public Date getValidTime() {
        return validTime;
    }

    public void setValidTime(Date validTime) {
        this.validTime = validTime;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }
}
