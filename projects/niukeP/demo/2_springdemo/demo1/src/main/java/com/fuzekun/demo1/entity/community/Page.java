package com.fuzekun.demo1.entity.community;

/**
 * @author: Zekun Fu
 * @date: 2022/9/29 14:25
 * @Description:
 */

public class Page {

    // 当前的页码
    private int current = 1;
    private int limit = 10;
    // 数据的总数(用于计算总页数)
    private int rows;
    //查询路径
    private String path;

    public int getCurrent() {
        return current;
    }

    public int getLimit() {
        return limit;
    }

    public int getRows() {
        return rows;
    }

    public String getPath() {
        return path;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public void setLimit(int limit) {
        if (limit >= 1 && limit <= 100) {
            this.limit = limit;
        }
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setRows(int rows) {
        if(rows >= 0)
            this.rows = rows;
    }

    public int getOffSet() {
        return (current - 1) * limit;
    }

    public int getTotal() {
        return (rows + limit - 1) / limit;          // 上取整
    }
    public int getFrom() {
        int from = current - 2;
        return from  < 1 ? 1:from;
    }

    public int getTo() {
        int to = current + 2;
        int total = getTotal();
        return to > total? total:to;

    }

    @Override
    public String toString() {
        return "Page{" +
                "current=" + current +
                ", limit=" + limit +
                ", rows=" + rows +
                ", path='" + path + '\'' +
                '}';
    }

    public Page(int current, int limit, int rows, String path) {
        this.current = current;
        this.limit = limit;
        this.rows = rows;
        this.path = path;
    }

    public Page() {}


}
