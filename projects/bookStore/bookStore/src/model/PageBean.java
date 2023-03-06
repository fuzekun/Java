package model;

import java.util.List;

public class PageBean {
    private int currentPage;// 当前页码
    private int totalCount;// 总条数
    private int totalPage;// 总页数
    private int currentCount;// 每页条数
    private List<Product> ps;// 每页显示的数据
    private String category; //分类

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public int getCurrentCount() {
        return currentCount;
    }

    public List<Product> getPs() {
        return ps;
    }

    public String getCategory() {
        return category;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public void setCurrentCount(int currentCount) {
        this.currentCount = currentCount;
    }

    public void setPs(List<Product> ps) {
        this.ps = ps;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "currentPage=" + currentPage +
                ", totalCount=" + totalCount +
                ", totalPage=" + totalPage +
                ", currentCount=" + currentCount +
                ", ps=" + ps +
                ", category='" + category + '\'' +
                '}';
    }
}
