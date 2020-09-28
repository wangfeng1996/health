package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wangfeng
 */
public interface CheckItemDao {
    /**
     * 添加
     *
     * @param checkItem 参数是检查项对象
     */
    public void add(CheckItem checkItem);

    /**
     * @param queryString 查询的条件，就是搜索框中查询的条件
     * @return
     */

    public Page<CheckItem> selectByCondition(@Param("value") String queryString);

    /**
     * 根据id进行删除
     *
     * @param id
     */
    public void delete(Integer id);

    /**
     * 查找id是否关联外键
     *
     * @param
     * @return
     */
    public long findCountId(Integer id);

    /**
     * 修改
     *
     * @param checkItem
     */
    public void update(CheckItem checkItem);

    /**
     * 根据id查询数据，并且回显给前端
     *
     * @param id
     * @return
     */
    public CheckItem findById(Integer id);

    /**
     * 查询所有的检查项
     *
     * @return
     */

    public List<CheckItem> findAll();


}
