/**
 * All rights Reserved, Designed By ysusoft.
 *
 * @author: ${jiang_qian}
 * @date: 2019/7/23 11:27
 * @Copyright ?2019 ysusolt. All rights reserved.
 * 注意：本内容仅限于燕大燕软内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.thankcode.common.entity;

import com.alibaba.fastjson.JSON;
import com.thankcode.common.enums.CodeEnum;
import com.thankcode.common.enums.ResEnum;

import java.io.Serializable;

/**
 * 统一返回实体
 *
 * @author: jiang_qian
 * @date: 2019/7/23 11:27
 * @version: V1.0
 */
public class ResEntity<T> implements Serializable{
    /**
     * 返回code 1 成功   0 是失败
     */
    private String resCode;
    /**
     * 返回消息
     */
    private String resMsg;
    /**
     * 消息实体
     */
    private T data;

    /**
     * 自定义错误编码
     * @return
     */

    private String detailCode;


    private String detailMsg;


    /**
     * @param resEnum 是否成功枚举
     * @param data 返回对象
     */
    public ResEntity(ResEnum resEnum, T data){
        this.resCode = resEnum.getCode();
        this.resMsg = resEnum.getDescription();
        this.data = data;
    }
    /**
     * @param resEnum 是否成功枚举
     * @param data 返回对象
     * @param codeEnum 错误枚举
     */
    public ResEntity(ResEnum resEnum, T data, CodeEnum codeEnum){
        this.resCode = resEnum.getCode();
        this.resMsg = resEnum.getDescription();
        this.data = data;
        this.detailCode = codeEnum.getCode();
        this.detailMsg = codeEnum.getName();
    }
    /**
     * @param resEnum 是否成功枚举
     * @param data 返回对象
     * @param codeEnum 错误枚举
     * @param detailMsg 自定义错误信息
     */
    public ResEntity(ResEnum resEnum, T data, CodeEnum codeEnum,String detailMsg){
        this.resCode = resEnum.getCode();
        this.resMsg = resEnum.getDescription();
        this.data = data;
        this.detailCode = codeEnum.getCode();
        this.detailMsg = detailMsg;
    }
    /**
     * @param resEnum 是否成功枚举
     * @param data 返回对象
     * @param detailCode 详细错误编码
     * @param detailMsg 自定义错误信息
     */
    public ResEntity(ResEnum resEnum, T data, String detailCode,String detailMsg){
        this.resCode = resEnum.getCode();
        this.resMsg = resEnum.getDescription();
        this.data = data;
        this.detailCode = detailCode ;
        this.detailMsg = detailMsg;
    }
    /**
     * @param resEnum 是否成功枚举
     * @param detailCode 详细错误编码
     * @param detailMsg 自定义错误信息
     */
    public ResEntity(ResEnum resEnum, String detailCode,String detailMsg){
        this.resCode = resEnum.getCode();
        this.resMsg = resEnum.getDescription();
        this.detailCode = detailCode ;
        this.detailMsg = detailMsg;
    }
    public ResEntity(){}

    /**
     * detailcode为空时调用
     * @param resEnum 是否成功枚举
     * @param data 返回对象
     * @param detailMsg 自定义错误信息
     */
    public ResEntity(ResEnum resEnum, T data, String detailMsg){
        this.resCode = resEnum.getCode();
        this.resMsg = resEnum.getDescription();
        this.data = data;
        this.detailMsg = detailMsg;
    }


    public String getResCode() {
        return resCode;
    }

    public String getResMsg() {
        return resMsg;
    }

    public T getData() {
        return data;
    }

    public String getDetailCode() {
        return detailCode;
    }

    public String getDetailMsg() {
        return detailMsg;
    }


    public void setResCode(ResEnum resEnum) {
        this.resCode = resEnum.getCode();
    }

    public void setResMsg(ResEnum resEnum) {
        this.resMsg = resEnum.getDescription();
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setDetailCode(String detailCode) {
        this.detailCode = detailCode;
    }

    public void setDetailMsg(String detailMsg) {
        this.detailMsg = detailMsg;
    }

    public static void main(String[] args) {
        System.out.print(JSON.toJSONString(new ResEntity(ResEnum.SUCCESS, null)));
    }
}
