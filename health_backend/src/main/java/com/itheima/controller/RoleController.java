package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Role;
import com.itheima.service.RoleService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;


/**
 * @author wangfeng
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    @Reference
    private RoleService roleService;


    /**
     * 分页查询
     *
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        return roleService.pageQuery(queryPageBean);
    }

    @RequestMapping("/add")
    public Result add(@RequestBody Map<String, Object> map) {
        Object roleList = map.get("roleList");
        System.out.println(roleList);

        try {
            roleService.add(map);
            return new Result(true, "添加角色成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "添加角色失败,稍后请您再试一次");
        }
    }

    @RequestMapping("/delete")
    public Result deleteRole(Integer id) {
        try {
            roleService.delete(id);
            return new Result(true, "删除角色成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除角色失败,稍后请您再试一次");
        }
    }

    /**
     * 根据id 查询角色信息
     *
     * @param id
     * @return
     */
    @RequestMapping("/findRoleById")
    public Result findRoleById(Integer id) {
        System.out.println(id);
        try {
            Role roleById = roleService.findRoleById(id);
            System.out.println(roleById);
            return new Result(true, "查询角色成功", roleById);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "查询角色失败");
        }
    }

    @RequestMapping("/findMenuById")
    public Result findMenuById(Integer id) {
        try {
            List<Integer> menuList = roleService.findMenuById(id);
            System.out.println(menuList);
            return new Result(true, "查询角色成功", menuList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "查询角色失败");
        }
    }

    @RequestMapping("/findPermissionById")
    public Result findPermissionById(Integer id) {
        try {
            List<Integer> permissionList = roleService.findPermissionById(id);
            System.out.println(permissionList);
            return new Result(true, "查询角色成功", permissionList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "查询角色失败");
        }
    }

    @RequestMapping("/edit")
    public Result edit(@RequestBody Map<String, Object> map) {
        try {
            roleService.edit(map);
            return new Result(true, "编辑成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "编辑角色失败,稍后请您再试一次");
        }
    }
}

