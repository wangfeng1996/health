package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Role;
import org.apache.ibatis.annotations.Param;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;

import java.util.List;
import java.util.Set;

public interface RoleDao {
    public Set<Role> findByUserId(Integer userId);

    /**
     * 分页查询
     *
     * @param queryString
     * @return
     */
    public Page<Role> selectByCondition(@Param("queryString") String queryString);

    /**
     * 添加角色
     *
     * @param role
     */
    public void add(Role role);

    /**
     * 根据角色id删除菜单id
     *
     * @param roleId
     */
    public void deleteRoleIdAndMenuId(@Param("roleId") Integer roleId);

    /**
     * 根据角色id添加菜单id
     *
     * @param roleId
     * @param menuId
     */
    public void insertRoIdAndMenuId(@Param("roleId") Integer roleId, @Param("menuId") Integer menuId);

    /**
     * 根据角色删除权限id
     *
     * @param roleId
     */
    public void deleteRoleIdAndPermissionId(@Param("roleId") Integer roleId);

    /**
     * 根据权限添加权限id
     *
     * @param roleId
     * @param permissionId
     */
    public void insertRoIdAndPermissionId(@Param("roleId") Integer roleId, @Param("permissionId") Integer permissionId);

    /**
     * 根据id删除角色信息
     *
     * @param id
     * @return
     */
    public boolean deleteRole(@Param("id") Integer id);

    /**
     * 根据id查询角色信息
     *
     * @param id
     * @return
     */
    public Role findRoleById(@Param("roleId") Integer id);

    /**
     * 根据RoleId 查询 菜单的的ids
     *
     * @param id
     * @return
     */
    public List<Integer> findMenuIdsByRoleId(@Param("id") Integer id);

    /**
     * 根据RoleId 查询权限的ids
     *
     * @param id
     * @return
     */
    public List<Integer> findPermissionIdsByRoleId(@Param("id") Integer id);

    /**
     * 修改角色
     *
     * @param role
     */
    public void edit(Role role);

    /**
     *
     */

    public List<Integer> findRoleIds(Integer userId);



}
