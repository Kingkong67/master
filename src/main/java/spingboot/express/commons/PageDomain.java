package spingboot.express.commons;

import java.io.Serializable;

/**
 * @Description:
 * @Author: wanghu
 * @Date: Create in 2020 11 2020/11/23
 * @return:
 * @throws:
 */
public class PageDomain implements Serializable {

    private static final long serialVersionUID = -4543564145896190989L;

    /**
     * 页数
     */
    private Integer pageNum;
    /**
     * 每页可以容纳的数据量
     */
    private Integer pageSize;

    public PageDomain() {
    }

    public Integer getPageNum() {
        return this.pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

}