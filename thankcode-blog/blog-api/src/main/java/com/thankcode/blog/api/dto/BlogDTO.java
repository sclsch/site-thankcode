package com.thankcode.blog.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.thankcode.blog.api.util.DateJsonDeserializer;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: suncl
 * @date: 2019-12-21 11:03:38
 * @version: V1.0
 */
@Getter
@Setter
public class BlogDTO implements Serializable {
    private Long id;
    @NotEmpty(message="标题不能为空")
    private String title;
    @NotEmpty(message="内容不能为空")
    private String content;

    @JsonDeserialize(using = DateJsonDeserializer.class)
    private Date updateTime;
    @Override
    public String toString() {
        return "id:" + id + ",title:" + title + ",content:" + content;
    }
}
