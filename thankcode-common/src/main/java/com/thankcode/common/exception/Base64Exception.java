/**
 * All rights Reserved, Designed By ysusoft.
 *
 * @author: ${jiang_qian}
 * @date: 2019/8/16 11:35
 * @Copyright ?2019 ysusolt. All rights reserved.
 * 注意：本内容仅限于燕大燕软内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.thankcode.common.exception;

/**
 * base64加解密异常
 *
 * @author: jiang_qian
 * @date: 2019/8/16 11:35
 * @version: V1.0
 */
public class Base64Exception  extends RuntimeException{

    Base64Exception(){}

    public Base64Exception(String message)
    {
        super(message);
    }

    public Base64Exception(Exception e)
    {
        super(e);
    }
}
