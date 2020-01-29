/**
 * All rights Reserved, Designed By ysusoft.
 *
 * @author: ${jiang_qian}
 * @date: 2019/7/8 10:09
 * @Copyright ?2019 ysusolt. All rights reserved.
 * 注意：本内容仅限于燕大燕软内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.thankcode.common.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * code枚举
 *
 * @author: jiang_qian
 * @date: 2019/7/8 10:09
 * @version: V1.0
 */
public enum CodeEnum {
    NO_SIGN("1000", "请求头缺少sign验签"),
    ILLEGAL_JSON("1001", "参数格式不符合JSON"),
    LACK_REQUIRED("1002", "缺少必填项"),
    NO_PERMISSION("1003", "无权访问"),
    WHITE_LIST("1003", "白名单"),
    REPEAT_REQ("1004", "报文重复"),
    SYS_EXCEPTION("1006","系统异常"),
    NOT_SEQUENCENUM("1007","请求头里没有sequenceNum！"),
    DECODE_FAIL("1008","密文长度或者格式不对，解密失败！"),
    REQUEST_FAIL("1009","请求格式不正确！")
   ;
    private static Map<String , CodeEnum> nameMap = new HashMap<String, CodeEnum>(
            20);
    private static Map<String, CodeEnum> codeMap = new HashMap<String, CodeEnum>(
            20);

    static {
        CodeEnum[] allValues = CodeEnum.values();
        for (CodeEnum obj : allValues) {
            nameMap.put(obj.getName(), obj);
            codeMap.put(obj.getCode(), obj);
        }
    }

    private String code;
    private String name;

    CodeEnum(String code, String name) {
        this.name = name;
        this.code = code;

    }

    public static CodeEnum parseByName(String name) {
        return nameMap.get(name);
    }

    public static CodeEnum parseByCode(String code) {
        return codeMap.get(code);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
