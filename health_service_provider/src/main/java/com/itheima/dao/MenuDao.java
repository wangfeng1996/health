package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.Menu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author wangfeng
 */
@Repository
public interface MenuDao {
    /**
     * 添加
     *
     * @param menu 菜单列表
     */
    public void add(Menu menu);

    /**
     * @param queryString 查询的条件，就是搜索框中查询的条件
     * @return
     */

    public Page<Menu> selectByCondition(@Param("value") String queryString);

    /**
     * 根据id进行删除
     *
     * @param id
     */
    public void delete(@Param("id") Integer id);

    /**
     * 修改
     *
     * @param menu 菜单
     */
    public void edit(Menu menu);

    /**
     * 根据id查询数据，并且回显给前端
     *
     * @param id
     * @return
     */
    public Menu findById(Integer id);

    /**
     * 根据id 查询name
     *
     * @param parentId
     * @return
     */
    public String findName(@Param("parentId") Integer parentId);

    /**
     * 根据id 查询父id
     */
    public Integer selectByIdForParentId(@Param("id") Integer id);

    /**
     * 查询所有的父id
     */
    public List<Integer> selectParentId();

    /**
     * 根据名称查找id
     *
     * @param parentName
     * @return
     */
    public Integer selectIdByName(@Param("parentName") String parentName);


    /**
     * 更加id修改父级菜单的id
     *
     * @param ById   父id
     * @param menuId 菜单id
     */
    public void editParentIdById(@Param("ById") Integer ById, @Param("menuId") Integer menuId);

    public List<Map<String, Object>> findAll();

    /**
     * 根据id查询所有的子菜单的集合
     *
     * @param id
     * @return
     */
    public List<Menu> findChildren(@Param("id") Integer id);

    public List<Integer> findMenuIdsByRoleId(Integer id);


}
