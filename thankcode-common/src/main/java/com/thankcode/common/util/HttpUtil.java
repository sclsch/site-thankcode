/**
 * All rights Reserved, Designed By ysusoft.
 *
 * @author: ${jiang_qian}
 * @date: 2019/8/6 15:18
 * @Copyright ?2019 ysusolt. All rights reserved.
 * 注意：本内容仅限于燕大燕软内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.thankcode.common.util;

import com.alibaba.fastjson.JSON;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;

/**
 * ${TODO}
 *
 * @author: jiang_qian
 * @date: 2019/8/6 15:18
 * @version: V1.0
 */

public class HttpUtil {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    private static RestTemplate restTemplate =SpringUtil.getBean(RestTemplate.class);

    public static  String post(String url, String content, Map<String,String> headerMap ){
        if(url.indexOf("https")!=-1){
            restTemplate.setRequestFactory(new HttpsClientRequestFactory());
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("Accept-Charset", StandardCharsets.UTF_8.name());
        if(headerMap!=null){
            Set<String> headerKeySet=headerMap.keySet();
            //赋值请求头
            for (String headerKey:headerKeySet) {
                headers.add(headerKey,headerMap.get(headerKey));
            }
        }
        HttpEntity httpEntity = new HttpEntity(content, headers);
        logger.info("<<<<<<<<<<请求体为：{}  url:{}",content,url);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        logger.info("<<<<<相应报文体:{}>>>>>>",JSON.toJSONString(response));

        Integer code = response.getStatusCode().value();
        logger.info("<<<<<<<<返回httpCode值为:{}>>>>>>",code);
        if (HttpStatus.OK.value() == code) {
            return response.getBody();
        } else {
            return null;
        }
    }


    public static  InputStream post(String url, Map<String,Object> paramMap, Map<String,String> headerMap ){
        if(url.indexOf("https")!=-1){
            restTemplate.setRequestFactory(new HttpsClientRequestFactory());
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept-Charset", StandardCharsets.UTF_8.name());
        if(headerMap!=null){
            Set<String> headerKeySet=headerMap.keySet();
            //赋值请求头
            for (String headerKey:headerKeySet) {
                headers.add(headerKey,headerMap.get(headerKey));
            }
        }
        MultiValueMap<String, Object> map= new LinkedMultiValueMap<>();
        Set<String> paramKeySet=paramMap.keySet();
        for (String key:paramKeySet) {
            map.add(key, paramMap.get(key));
        }
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(map, headers);

        ResponseEntity<Resource> response = restTemplate.postForEntity( url, request , Resource.class );

        try {
            InputStream in = response.getBody().getInputStream();
            return in;
        } catch (IOException e) {
            logger.info("http请求失败：{}",e);
            return null;
        }
    }



    public static  String  get(String url, String content,Map<String,String> headerMap ){

        logger.info("<<<<<<<<<<请求参数为：{},请求头为:{} url:{}",content,JSON.toJSONString(headerMap),url);
        if(url.indexOf("https")!=-1){
            //https
            restTemplate.setRequestFactory(new HttpsClientRequestFactory());
        }
        HttpHeaders headers = new HttpHeaders();
        Set<String> headerKeySet=headerMap.keySet();
        //赋值请求头
        for (String headerKey:headerKeySet) {
            headers.add(headerKey,headerMap.get(headerKey));
        }
        HttpEntity httpEntity = new HttpEntity(content, headers);
        String res = restTemplate.getForObject(url,String.class,httpEntity);
        return res;
    }




    public static  String postForm(String url, Map<String,Object> paramMap, Map<String,String> headerMap ){
        if(url.indexOf("https")!=-1){
            restTemplate.setRequestFactory(new HttpsClientRequestFactory());
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Accept-Charset", StandardCharsets.UTF_8.name());
        if(headerMap!=null){
            Set<String> headerKeySet=headerMap.keySet();
            //赋值请求头
            for (String headerKey:headerKeySet) {
                headers.add(headerKey,headerMap.get(headerKey));
            }
        }
        MultiValueMap<String, Object> map= new LinkedMultiValueMap<>();
        Set<String> paramKeySet=paramMap.keySet();
        for (String key:paramKeySet) {
            map.add(key, paramMap.get(key));
        }
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(map, headers);

        ResponseEntity<String> response = restTemplate.postForEntity( url, request , String.class );

        Integer code = response.getStatusCode().value();

        if (HttpStatus.OK.value() == code) {
            return response.getBody();
        } else {
            return null;
        }
    }






}
