package com.thankcode.common.enums;

/**
 * @author: suncl
 * @date: 2019/8/1 15:07
 * @version: V1.0
 */
public enum ResEnum {
    SUCCESS("1","成功"),
    FAIL("0","失败");

    public String getCode() {
        return code;
    }
    public String getDescription() {
        return description;
    }
    private String code;
    private String description;
    private ResEnum(String code,String description){
        this.code = code;
        this.description = description;
    }
}
