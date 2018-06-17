package shop.domain;

import java.util.List;

public class PageBean {
    private  int totalPage;//总页数
    private  int pageNum;//当前页数
    private  int currentPage;//当前码数
    private  int totalCount;//总条数
    private List<Product> ps;

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<Product> getPs() {
        return ps;
    }

    public void setPs(List<Product> list) {
        this.ps = list;
    }
}
