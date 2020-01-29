/**
 * All rights Reserved, Designed By ysusolt.
 *
 * @author: jiangqian
 * @date: 2019/11/19 0019
 * @Copyright ?2019 ysusolt. All rights reserved.
 * 注意：本内容仅限于燕大燕软内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.thankcode.zuul.filter;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.thankcode.auth.api.AuthApi;
import com.thankcode.auth.api.dto.AuthDto;
import com.thankcode.common.entity.AnswerDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 权限验证
 *
 * @author: jiangqian
 * @date: 2019/11/19 0019 11:53
 * @version: V1.0
 * @review: jiangqian/2019/11/19 0019 11:53
 */
@Component
public class AccessFilter extends ZuulFilter {

    private Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private AuthApi authApi;

    /**
     * @brief filterType 参数详细解释
     * pre：可以在请求被路由之前调用，route：在路由请求时候被调用，
     * post：在调用server 响应成功之后 调用，error：处理请求时发生错误时被调用
     * @date 2108/4/18
     */


    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤器的执行顺序  数字越小代表越先执行
     *
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 过滤器开关
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器的执行逻辑
     */
    @Override
    public Object run() {

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        try {
            ctx.getResponse().setHeader("Access-Control-Allow-Origin", "*");
            ctx.getResponse().setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, OPTIONS, PATCH");
            ctx.getResponse().setHeader("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, X-File-Name");
            ctx.getResponse().setCharacterEncoding("UTF-8");
            ctx.getResponse().setContentType("application/json");
            log.info("method {} uri {}", request.getMethod(), request.getRequestURL().toString());
            String uri = request.getRequestURI();
            uri = uri.substring(1);
            String[] urlArray = uri.split("/");
            String serverId = urlArray[0];
            if (urlArray == null) {
                ctx.setSendZuulResponse(false);
                ctx.setResponseBody(JSON.toJSONString(getAnswerDTO("URL格式输入有问题")));
                return null;
            }
            //如果是auth的直接放过
            if ("auth-server".equals(serverId)) {
                return null;
            }
            String token = request.getHeader("token");
            log.info("<<<<<<获取的token:{}>>>>>>>>", token);
            if (StringUtils.isEmpty(token)) {
                log.warn("token is empty");
                //过滤该请求，不往下级服务去转发请求，到此结束
                ctx.setSendZuulResponse(false);
                AnswerDTO answerDTO = new AnswerDTO();
                answerDTO.setCode("0");
                answerDTO.setMessage("token 不允许为空 ");
                ctx.setResponseBody(JSON.toJSONString(answerDTO));
                return null;
            } else {
                log.info("<<<<<<<<<调用auth 开始  参数为 token:{}>>>>>>>>>>>>>>",token);
                AuthDto authDto = authApi.findAuthByToken(token);
                if (authDto == null) {
                    ctx.setSendZuulResponse(false);
                    ctx.setResponseBody(JSON.toJSONString(getAnswerDTO("无权访问")));
                    return null;
                }
                String serverIds = authDto.getServerIds();
                //如果配置的是* 全部可以路由
                if ("*".equals(serverIds)) {
                    return null;
                }
                if (!serverIds.contains(serverId)) {
                    ctx.setSendZuulResponse(false);
                    ctx.setResponseBody(JSON.toJSONString(getAnswerDTO("无权访问")));
                    return null;
                }
                return null;
            }
        } catch (Exception e) {
            log.info("网关路由出错：{}", e);
            ctx.setSendZuulResponse(false);
            ctx.setResponseBody(JSON.toJSONString(getAnswerDTO("系统异常")));
            return null;
        }


    }

    private AnswerDTO getAnswerDTO(String content) {
        AnswerDTO answerDTO = new AnswerDTO();
        answerDTO.setCode("0");
        answerDTO.setMessage(content);
        return answerDTO;
    }

    public static void main(String[] args) {
        String ip = "/core-server/rr";
        ip = ip.substring(1);
        String[] str = ip.split("/");
        System.out.print(JSON.toJSONString(str));
    }
}
