/** 以下代码由代码生成器自动生成，如无必要，请勿修改！2021-5-19 11:09:12 **/
package com.example.plus.bean;

/**
 * @author code-generator
 * @date 2021-5-19 11:09:12
 */
public class PageQueryModel {

    private int pageNum;

    private int pageSize;

    private String[] orderByAsc;

    private String[] orderByDesc;

    private static final String DEFAULT_FIELD = "id";

    public PageQueryModel() {
        this.setPageNum(1);
        this.setPageSize(Integer.MAX_VALUE);
        this.setOrderByDesc(new String[]{DEFAULT_FIELD});
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String[] getOrderByAsc() {
        return orderByAsc;
    }

    public void setOrderByAsc(String[] orderByAsc) {
        this.orderByAsc = orderByAsc;
    }

    public String[] getOrderByDesc() {
        return orderByDesc;
    }

    public void setOrderByDesc(String[] orderByDesc) {
        this.orderByDesc = orderByDesc;
    }

}
