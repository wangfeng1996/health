package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.RoleDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Role;
import com.itheima.pojo.Setmeal;
import com.itheima.service.RoleService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service(interfaceClass = RoleService.class)
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;

    /**
     * 添加角色信息
     *
     * @param map
     */
    @Override
    public void add(Map<String, Object> map) {
//        获取角色信息
        Map<String, String> roleList = (Map<String, String>) map.get("roleList");
//获取角色名称
        String name = roleList.get("name");
//        获取角色关键字
        String keyword = roleList.get("keyword");
//        获取描述信息
        String description = roleList.get("description");
        Role role = new Role();
        role.setName(name);
        role.setKeyword(keyword);
        role.setDescription(description);
        roleDao.add(role);
//       获取添加后的Role的id
        Integer roleId = role.getId();
//        添加菜单中间表
        addMenuIds(map, roleId);
//        添加权限中间表
        addPermissionIds(map, roleId);
    }


    /**
     * 分页查询
     *
     * @param queryPageBean 封装查询条件  包含当前页和每页的条数
     * @return
     */
    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage, pageSize);
        Page<Role> page = roleDao.selectByCondition(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 删除角色
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {

//        根据id删除角色
        boolean result = roleDao.deleteRole(id);
        if (result) {
            //        根据id删除菜单中间表
            roleDao.deleteRoleIdAndMenuId(id);
            //        根据id删除permission中间表
            roleDao.deleteRoleIdAndPermissionId(id);
        }
    }

    @Override
    public Role findRoleById(Integer id) {
        Role roleById = roleDao.findRoleById(id);
        return roleById;
    }

    @Override
    public List<Integer> findMenuById(Integer id) {
        List<Integer> menuIds = roleDao.findMenuIdsByRoleId(id);
        return menuIds;
    }

    @Override
    public List<Integer> findPermissionById(Integer id) {
        List<Integer> permissionIds = roleDao.findPermissionIdsByRoleId(id);
        return permissionIds;
    }

    @Override
    public void edit(Map<String, Object> map) {
        //        获取角色信息
        Map<String, Object> roleList = (Map<String, Object>) map.get("roleList");

        Object id = roleList.get("id");
        //获取角色名称
        String name = (String) roleList.get("name");
        //        获取角色关键字
        String keyword = (String) roleList.get("keyword");
        //        获取描述信息
        String description = (String) roleList.get("description");
        Role role = new Role();
        role.setId((Integer) id);
        role.setName(name);
        role.setKeyword(keyword);
        role.setDescription(description);
        roleDao.edit(role);
//        添加菜单中间表
        addMenuIds(map, (Integer) id);
//        添加权限中间表
        addPermissionIds(map, (Integer) id);
    }




    /**
     * 根据角色id删除菜单id
     *
     * @param map
     * @param roleId
     */
    private void addMenuIds(Map<String, Object> map, Integer roleId) {
//        获取菜单id
        List<Integer> menuIds = (List<Integer>) map.get("menuIds");
//        在添加之前，先查询列表中是否有id相关的数据,然后将其删除
        try {
            roleDao.deleteRoleIdAndMenuId(roleId);
            for (Integer menuId : menuIds) {
                System.out.println(menuId);
                roleDao.insertRoIdAndMenuId(roleId, menuId);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据角色id删除权限id
     *
     * @param map
     * @param roleId
     */

    private void addPermissionIds(Map<String, Object> map, Integer roleId) {
//        获取菜单id
        List<Integer> permissionIds = (List<Integer>) map.get("permissionIds");
        System.out.println(permissionIds);
//        在添加之前，先查询列表中是否有id相关的数据,然后将其删除
        try {
            roleDao.deleteRoleIdAndPermissionId(roleId);
            for (Integer permissionId : permissionIds) {
                System.out.println(permissionId);
                roleDao.insertRoIdAndPermissionId(roleId, permissionId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
