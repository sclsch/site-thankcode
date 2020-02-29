/**
 * All rights Reserved, Designed By ysusoft.
 *
 * @author: ${jiang_qian}
 * @date: 2019/7/23 11:43
 * @Copyright ?2019 ysusolt. All rights reserved.
 * 注意：本内容仅限于燕大燕软内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.thankcode.web.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;

import com.thankcode.common.entity.AnswerDTO;
import com.thankcode.common.entity.ResEntity;
import com.thankcode.common.enums.CodeEnum;
import com.thankcode.common.enums.ResEnum;
import com.thankcode.common.util.ClientIpUtil;
import com.thankcode.common.util.IdUtils;
import com.thankcode.common.util.LogUtil;
import com.thankcode.web.config.WhiteIpPropertie;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * controller切面  验签 和 解密
 *
 * @author: jiang_qian
 * @date: 2019/7/23 11:43
 * @version: V1.0
 */
@Aspect
@Component
public class WhiteAspect implements Ordered {
    @Resource
    private WhiteIpPropertie whiteIpPropertie;
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 排除 auth包
     */
    @Pointcut("execution(* com.thankcode.web.*controller.*.*(..)) && !execution(* com.thankcode.web.aop.*.*(..)) ")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {

        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        logger.info("<<<<<请求头Accept-Charset:{}>>>>>>>",request.getHeader("Accept-Charset"));
        logger.info("<<<<<请求头Content-Type:{}>>>>>>>",request.getHeader("Content-Type"));
        //用户ip
        String ip = ClientIpUtil.getIpAddress(request);
        logger.info("未过滤前ip：{}",ip);
        //允许的ip
        List<String> ipList = whiteIpPropertie.getIpList();
        if(ipList.size() == 0){
            logger.info("未配置白名单，任何ip均可访问");
            return pjp.proceed();
        }else{
            for(String ipWhite:ipList){
                logger.info("ip白名单：{}",ipWhite);
            }
        }
        //需要拦截的服务
        String requestURI = request.getRequestURI();
        logger.info("requestURI:{}",requestURI);
        if( "/blog/post".equals(requestURI)
                ||"/blog/del".equals(requestURI)
                ||"/blog/edit".equals(requestURI)
                ||"/blog/add".equals(requestURI)
        ){

            if(!ipList.contains(ip)){
                logger.info("该ip需要添加白名单，请联系研发人员:{}",ip);
                AnswerDTO answerDTO=new AnswerDTO();
                answerDTO.setMessage("不劳而食，拾人牙慧");
                return answerDTO;
            }
        }
        logger.info("<<<<获取客户端ip：{}>>>>>>",ip);

        //TODO 取得参数，校验必填项
        Object[] args = pjp.getArgs();


        return pjp.proceed();
    }



    @Override
    public int getOrder() {
        return 0;
    }
}
