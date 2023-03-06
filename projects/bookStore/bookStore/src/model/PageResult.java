package model;

import java.util.List;

public class PageResult<T> {
    List<T> list;//具体的数据
    long totalcnt;//总条数
    int PageSize; //每页显示条数，默认为4
    int totalpage;//总页数
    int currenpage;	  //当前页

    public List<T> getList() {
        return list;
    }

    public long getTotalcnt() {
        return totalcnt;
    }

    public int getPageSize() {
        return PageSize;
    }

    public int getTotalpage() {
        return totalpage;
    }

    public int getCurrenpage() {
        return currenpage;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public void setTotalcnt(long totalcnt) {
        this.totalcnt = totalcnt;
    }

    public void setPageSize(int pageSize) {
        PageSize = pageSize;
    }

    public void setTotalpage(int totalpage) {
        this.totalpage = totalpage;
    }

    public void setCurrenpage(int currenpage) {
        this.currenpage = currenpage;
    }

    @Override
    public String toString() {
        return "PageResult{" +
                "totalcnt=" + totalcnt +
                ", PageSize=" + PageSize +
                ", totalpage=" + totalpage +
                ", currenpage=" + currenpage +
                '}';
    }
}
