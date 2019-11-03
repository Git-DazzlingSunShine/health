package com.yanglei.entry;

import java.io.Serializable;

/**
 * 封装查询条件
 */
public class QueryPageBean implements Serializable {
    private Integer currentPage = 1;//页码
    private Integer pageSize = 10;//每页记录数
    private String queryString;//查询条件

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }
}