package cn.graht.springbootinit.commons;

import lombok.Data;

/**
 * 分页对象
 * @author grhat
 */
@Data
public class PageQuery {
    /**
     * 每页展示条数
     */
    protected long pageSize;
    /**
     * 页码
     */
    protected long pageNum;
}
