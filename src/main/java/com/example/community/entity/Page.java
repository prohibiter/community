package com.example.community.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Page {

    //当前页码
    private int current = 1;
    //显示上限
    private int limit = 10;
    //数据总数（用于计算总页数）
    private int rows;
    //查询路径(复用分页链接)
    @Setter
    private String path;

    public void setLimit(int limit) {
        if (limit >= 1 || limit <= 100) {
            this.limit = limit;
        }
    }

    public void setCurrent(int current) {
        if (current >= 1) {
            this.current = current;
        }
    }

    public void setRows(int rows) {
        if (rows >= 0) {
            this.rows = rows;
        }
    }

    /**
     * 获取当前页的起始行
     *
     * @return
     */
    public int getOffset() {
        //current * limit - limit
        return (current - 1) * limit;
    }

    /**
     * 获取总页数
     *
     * @return
     */
    public int getTotal() {
        // rows / limit
        return (int) Math.floor(rows / limit);
    }

    /**
     * 获取起始页码
     *
     * @return
     */
    public int getFrom() {
        int from = current - 2;
        return from < 1 ? 1 : from;
    }

    /**
     * 获取结束页码
     *
     * @return
     */
    public int getTo() {
        int to = current + 2;
        int total = getTotal();
        return to > total ? total : to;
    }
}
