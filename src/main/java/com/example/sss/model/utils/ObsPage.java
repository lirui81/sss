package com.example.sss.model.utils;


import java.util.List;

public class ObsPage {
    private long total; // 总记录数
    private List data; // 返回每页的数据的集合

    public ObsPage(long total, List data) {
        super();
        this.total = total;
        this.data = data;
    }
    public ObsPage(){
        super();
    }
    public long getTotal() {
        return total;
    }
    public void setTotal(long total) {
        this.total = total;
    }
    public List getData() {
        return data;
    }
    public void setData(List data) {
        this.data = data;
    }
}
