package com.itheima.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckItemDao;
import com.itheima.dao.MenuDao;
import com.itheima.dao.RoleDao;
import com.itheima.dao.UserDao;
import com.itheima.entity.PageResult;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.Menu;
import com.itheima.pojo.Role;
import com.itheima.service.CheckItemService;
import com.itheima.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author wangfeng
 * 检查项的业务层
 */
@Service(interfaceClass = MenuService.class)
@Transactional
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuDao menuDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;


    @Override
    public void add(Menu menu) {
        menuDao.add(menu);
        Integer menuId = menu.getId();
        String parentName = menu.getParentName();
//        如果为null说明是一级菜单
        if (parentName != null) {
            //      如果不为null，说明不是一级菜单，根据parentName，获取id，让后将id赋值给parentId
            Integer id = menuDao.selectIdByName(parentName);
            menuDao.editParentIdById(id, menuId);
        }
    }

    //  分页查询
    @Override
    public PageResult pageQuery(Integer currentPage, Integer pageSiz, String queryString) {
        PageHelper.startPage(currentPage, pageSiz);

        Page<Menu> page = menuDao.selectByCondition(queryString);
        for (Menu menu : page.getResult()) {
//            查询父id
            Integer parentMenuId = menu.getParentMenuId();
//            根据父id查询名称，最后将名称添加到对象中
//            根据父id查询名称，最后将名称添加到对象中
            String name = menuDao.findName(parentMenuId);
            menu.setParentName(name);
        }
        System.out.println(page.getResult());


        System.out.println(page.toString());
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public void delete(Integer id) {
        menuDao.delete(id);
    }

    @Override
    public void update(Menu menu) {
//        获取id，根据id进行更新的操作
        Integer id = menu.getId();
        System.out.println(id);
        menuDao.edit(menu);
        if (menu.getParentName() != null) {
            Integer pid = menuDao.selectIdByName(menu.getParentName());
            System.out.println(pid);
            menuDao.editParentIdById(pid, id);
        }
    }

    @Override
    public Menu findById(Integer id) {
        System.out.println(menuDao.findById(id));
        // 根据id 查询parentId,
        Integer parentId = menuDao.selectByIdForParentId(id);
//        根据parentId 查询 菜单名称
        String name = menuDao.findName(parentId);
        Menu menu = menuDao.findById(id);
        menu.setParentName(name);
        return menu;
    }

    @Override
    public List<Menu> findAll() {

        List<Map<String, Object>> all = menuDao.findAll();
        System.out.println(all);

        return null;
    }


    @Override
    public List<Map<String, Object>> finParentName() {
        List<Map<String, Object>> arrayList = new ArrayList<>();
//        查找所有的parentId
        List<Integer> parentId = menuDao.selectParentId();
        System.out.println(parentId);

        for (Integer pid : parentId) {
            String name = menuDao.findName(pid);
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("parentName", name);
            arrayList.add(hashMap);
        }
        return arrayList;
    }

    @Override
    public List<Map<String, Object>> findMenuList() {
        ArrayList<Map<String, Object>> arrayList = new ArrayList<>();
//        查询所有的以及菜单
        List<Integer> list = menuDao.selectParentId();
//        获取一级菜单的名称
        for (Integer pid : list) {
            HashMap<String, Object> hashMap = new HashMap<>();
//            根据父id获取父级菜单的名称
            String name = menuDao.findName(pid);
            System.out.println(name);
//            根据父id查询子菜单
            List<Menu> children = menuDao.findChildren(pid);
            System.out.println(children);
            hashMap.put("id", pid);
            hashMap.put("name", name);
            hashMap.put("children", children);
            arrayList.add(hashMap);
        }
//        根据以及菜单的id查询所有的字菜单的名称


        return arrayList;
    }

    @Override
    public List<Map<String, Object>> findMenuLists(String username) {
        ArrayList<Map<String, Object>> arrayList = new ArrayList<>();
//        根据用户名查询用户id
        Integer userNameId = userDao.findUserNameId(username);
//        根据用户id 查询角色ids
        List<Integer> roleIds = roleDao.findRoleIds(userNameId);
//        遍历roleId，获取到每个roleid
        for (Integer roleId : roleIds) {
//            根据roleId,获取菜单所有的菜单id
            List<Integer> menuIds = menuDao.findMenuIdsByRoleId(roleId);
            for (Integer menuId : menuIds) {
//                获取所有的父级菜单
                List<Integer> parentIds = menuDao.selectParentId();
                for (Integer parentId : parentIds) {
                    if (menuId.equals(parentId)) {
                        HashMap<String, Object> hashMap = new HashMap<>();
//                根据id，获取菜单信息
                        Menu menu = menuDao.findById(menuId);
                        hashMap.put("path", menu.getPath());
                        hashMap.put("name", menu.getName());
                        hashMap.put("icon", menu.getIcon());
                        List<Menu> children = menuDao.findChildren(menuId);
                        hashMap.put("children", children);
                        arrayList.add(hashMap);

                    }
                }
            }
        }
        return arrayList;
    }
}
