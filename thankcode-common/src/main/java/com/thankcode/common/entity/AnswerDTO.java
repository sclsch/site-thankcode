package com.thankcode.common.entity;

import java.io.Serializable;

/**
 *透明网 返回 实体
 */
public class AnswerDTO<T> implements Serializable{
    private String message;
    private T content;
    private String code;
    /**
     * 应答DTO构造器
     * @param message 提示信息
     * @param content 返回详细内容，可为pageDTO
     * @param code 一般为CommonState 的枚举
     */
    public AnswerDTO(String message, T content, Enum code) {
        this.message = message;
        this.content = content;
        this.code = code.name();
    }
    public AnswerDTO(String message, T content, String code) {
        this.message = message;
        this.content = content;
        this.code = code;
    }

    public AnswerDTO() {
        super();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public void setCode(Enum code) {
        this.code = code.name();
    }
}
