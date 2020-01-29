package com.thankcode.common.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fs on 9/28/17.
 *
 */
public class PageDTO<T> implements Serializable {

    private List<T> content;
    private int page;
    private int pageSize;
    private Integer totalPage;
    private Long totalElements;


    public PageDTO(List<T> content, int page, int pageSize, Integer totalPage) {
        this.content = content;
        this.page = page;
        this.pageSize = pageSize;
        this.totalPage = totalPage;
    }

    public PageDTO(List<T> content, int page, int pageSize, Integer totalPage,Long totalElements) {
        this.content = content;
        this.page = page;
        this.pageSize = pageSize;
        this.totalPage=totalPage;
        this.totalElements = totalElements;
    }


    public PageDTO() {
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }
}
