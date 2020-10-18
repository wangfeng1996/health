package com.itheima.dao;

import com.itheima.pojo.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface PermissionDao {

    /**
     * 添加方法
     *
     * @param permission 值
     */
    public void add(Permission permission);

    /**
     * 分页查找功能
     *
     * @param condition 条件
     * @return 返回集合
     */
    public List<Permission> selectByCondition(@Param("value") String value);

    /**
     * 查询id的关联情况
     *
     * @param id
     * @return 次数
     */
    Integer findByPermissionId(Integer id);

    /**
     * 删除
     *
     * @param id
     */

    void delete(Integer id);

    /**
     * 根据id查找
     *
     * @param id
     */
    Permission findById(Integer id);

    void edit(Permission permission);

    /**
     * 查询全部
     *
     * @return 返回集合
     */
    List findAll();

    /**
     *
     * @param roleId
     * @return
     */

    public Set<Permission> findByRoleId(Integer roleId);
}
