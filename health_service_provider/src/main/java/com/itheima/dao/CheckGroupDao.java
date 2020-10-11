package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wangfeng
 */

@Repository
public interface CheckGroupDao {
    /**
     * 添加
     *
     * @param checkGroup 参数是检查项对象
     */
    public boolean add(CheckGroup checkGroup);

    /**
     * 分页+模糊查询
     *
     * @param queryString
     * @return
     */


    public Page<CheckGroup> findByCondition(@Param("value") String queryString);

    /**
     * 查询所有
     *
     * @return
     */


    public List<CheckGroup> findAll();

    /**
     * 根据groupid 插入itermid
     *
     * @param groupId
     * @param checkItemId
     */

    public void groupIntoCheckItemIds(@Param("groupId") Integer groupId, @Param("checkItemId") Integer checkItemId);

    /**
     * 根据id进行插叙数据，最后将数据回显到前端
     *
     * @param id
     * @return
     */

    public CheckGroup findById(Integer id);


    /**
     * 根据检查组的id将与之绑定的检查项的id查询出来
     *
     * @param id
     * @return
     */
    public List<Integer> checkGroupIdWithCheckItemIds(Integer id);

    /**
     * 修改检查组的信息
     *
     * @param checkGroup
     */

    public void edit(CheckGroup checkGroup);

    /**
     * 根据Groupid删除数据库中的内容
     *
     * @param id
     */
    public void deleteCheckGroupId(Integer id);

    /**
     * 根据Groupid删除检查项的id
     * @param id
     */
    public void deleteCheckItemIds(Integer id);


    /**
     * 从检查套餐中间表中查询检查组所对应的检查套餐的个数
     *
     * @param id
     * @return
     */
    public long findSetMealId(Integer id);

    /**
     * 根据id查询检查组的详细信息
     *
     * @param id
     * @return
     */
    public List<CheckGroup> findCheckGroupById(int id);
}
