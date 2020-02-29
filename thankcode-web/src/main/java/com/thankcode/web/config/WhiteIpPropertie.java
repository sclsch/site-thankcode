/**
 * All rights Reserved, Designed By ysusoft.
 *
 * @author: ${jiang_qian}
 * @date: 2019/7/5 16:40
 * @Copyright ?2019 ysusolt. All rights reserved.
 * 注意：本内容仅限于燕大燕软内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.thankcode.web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 白名单
 *
 * @author: jiang_qian
 * @date: 2019/7/16 16:40
 * @version: V1.0
 */
@Component
@ConfigurationProperties(prefix = "white")
public class WhiteIpPropertie {

    private List<String> ipList;

    public List<String> getIpList() {
        return ipList;
    }

    public void setIpList(List<String> ipList) {
        this.ipList = ipList;
    }
}
