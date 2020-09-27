package com.itheima;

import com.itheima.entity.CheckGroupAndCheckItemIds;
import com.itheima.entity.PageResult;
import com.itheima.pojo.CheckGroup;
import com.sun.jersey.spi.inject.ClientSide;

import java.util.List;

/**
 * dubbo的远程接口
 *
 * @author wangfeng
 */

public interface CheckGroupService {
    /**
     * 添加检查项的接口
     *
     * @param checkGroupAndCheckItemIds 包含检查组和检查项的id
     * @return 返回值判断是否添加成功
     */
    public boolean add(CheckGroupAndCheckItemIds checkGroupAndCheckItemIds);

    /**
     * 分页查询的方法
     *
     * @param currentPage 页码
     * @param pageSiz     每页记录数
     * @param queryString 查询条件
     * @return 返回一个pageResult的对象
     */
    public PageResult pageQuery(Integer currentPage, Integer pageSiz, String queryString);

    /**
     * 查询所有
     * @return  集合
     */
    public List<CheckGroup> finAll();

    /**
     *根据id进行查询
     * @param id
     * @return
     */

    public CheckGroup findById(Integer id);

    /**
     * 根据检查组的id查询到检查项的id的集合
     * @param id
     * @return
     */

    public List<Integer> checkGroupIdWithCheckItemIds(Integer id);

    /**
     *  修改
     * @param checkGroupAndCheckItemIds   检查项id的数据和检查组的集合
     */
    public void edit(CheckGroupAndCheckItemIds checkGroupAndCheckItemIds);

    /**
     *删除
     * @param id  根据传过来的id删除数据
     */
    public void delete(Integer id);
}
