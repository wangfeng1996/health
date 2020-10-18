package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.Menu;

import java.util.List;
import java.util.Map;

/**
 * dubbo的远程接口
 *
 * @author wangfeng
 */
public interface MenuService {
    /**
     * 添加检查项的接口
     *
     * @param menu
     */
    public void add(Menu menu);

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
     * @param menu
     */
    public void update(Menu menu);

    /**
     * 根据id查询数据
     *
     * @param id
     * @return
     */
    public Menu findById(Integer id);

    /**
     * 查询所有
     *
     * @return
     */

    public List<Menu> findAll();

    /**
     * 查询所有的一级菜单
     *
     * @return
     */
    public List<Map<String, Object>> finParentName();

    /**
     * 查询MenuList集合
     *
     * @return
     */
    public List<Map<String, Object>> findMenuList();
}
