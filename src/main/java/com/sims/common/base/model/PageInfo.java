
package com.sims.common.base.model;

import java.io.Serializable;
import java.util.List;

/**
 * ClassName:PageParameter <br/>
 * Description: 分页对象 <br/>
 * Date: 2014年12月18日 下午5:16:55 <br/>
 *
 * @author 焦少平
 * @version 1.0
 * @since JDK 1.7
 * @see
 */
public class PageInfo implements Serializable {

    /**
     * serialVersionUID:序列化ID，缓存需要.
     *
     * @since JDK 1.7
     */
    private static final long serialVersionUID = -117781956848297165L;
    /**
     * 默认分页条数
     */
    public static final int DEFAULT_PAGE_SIZE = 20;
    /**
     * 页面大小
     */
    private int pageSize;
    /**
     * 当前页
     */
    private int currentPage;
    /**
     * 上一页
     */
    private int prePage;
    /**
     * 下一页
     */
    private int nextPage;
    /**
     * 分页总数
     */
    private int totalPage;
    /**
     * 结果总数
     */
    private int totalCount;
    /**
     * 数据库查询出来的结果集
     */
    private transient List<?> result;
    /**
     * 页脚
     */
    private transient List<?> footer;

    public PageInfo() {
        this.currentPage = 1;
        this.pageSize = DEFAULT_PAGE_SIZE;
    }

    public PageInfo(int currentPage, int pageSize) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPrePage() {
        return prePage;
    }

    public List<?> getResult() {
        return result;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }

    public void setResult(List<?> result) {
        this.result = result;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    /**
     * footer.
     *
     * @return  the footer
     * @since   JDK 1.7
     */
    public List<?> getFooter() {
        return footer;
    }

    /**
     * footer.
     *
     * @param   footer    the footer to set
     * @since   JDK 1.7
     */
    public void setFooter(List<?> footer) {
        this.footer = footer;
    }

}
