package com.itheima;

import com.itheima.entity.PageResult;
import com.itheima.pojo.CheckItem;

import java.util.List;

/**
 * dubbo的远程接口
 *
 * @author wangfeng
 */
public interface CheckItemService {
    /**
     * 添加检查项的接口
     *
     * @param checkItem
     */
    public void add(CheckItem checkItem);

    /**
     * 分页查询的方法
     *
     * @param currentPage 页码
     * @param pageSiz     每页记录数
     * @param queryString 查询条件
     * @return
     */
    public PageResult pageQuery(Integer currentPage, Integer pageSiz, String queryString);

    /**
     * 删除检查项的接口
     *
     * @param id
     */
    public void delete(Integer id);

    /**
     * 更新数据（修改）
     *
     * @param checkItem
     */
    public void update(CheckItem checkItem);

    /**
     * 根据id查询数据
     *
     * @param id
     * @return
     */
    public CheckItem findById(Integer id);

    /**
     * 查询所有
     *
     * @return
     */

    public List<CheckItem> findAll();
}
