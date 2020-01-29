/**
 * All rights Reserved, Designed By ysusoft.
 *
 * @author: ${jiang_qian}
 * @date: 2019/7/26 15:28
 * @Copyright ?2019 ysusolt. All rights reserved.
 * 注意：本内容仅限于燕大燕软内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.thankcode.web.exception;

/**
 * 校验异常
 *
 * @author: jiang_qian
 * @date: 2019/7/26 15:28
 * @version: V1.0
 */
public class ValidateException extends RuntimeException {

    ValidateException(){}

    public ValidateException(String message)
    {
        super(message);
    }
}
