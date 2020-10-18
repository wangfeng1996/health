package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.Menu;
import com.itheima.service.CheckItemService;
import com.itheima.service.MenuService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangfeng
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    /**
     * 远程注入
     */
    @Reference
    private MenuService menuService;

    //http://localhost:82/checkitem/add.do
    @PostMapping("/add")
    public Result add(@RequestBody Menu menu) {

        System.out.println(menu);
        try {
            menuService.add(menu);
//            添加成功给的响应
        } catch (Exception e) {
            e.printStackTrace();
//            添加失败传给前端的响应
            return new Result(false, "添加菜单成功");
        }
        return new Result(true, "添加菜单失败");
    }

    /**
     * 分页+模糊查询
     *
     * @param queryPageBean
     * @return
     */
    //http://localhost:82/checkitem/findPage.do
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        return menuService.pageQuery(currentPage, pageSize, queryString);
    }

    /**
     * 删除数据
     *
     * @param id
     * @return
     */

    //    http://localhost:82/checkitem/delete/108.do
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        try {
            menuService.delete(id);
//            添加成功给的响应
        } catch (Exception e) {
            e.printStackTrace();
//            添加失败传给前端的响应
            return new Result(false, "删除菜单失败");
        }
        return new Result(true, "删除菜单成功");
    }

    /**
     * 修改数据
     *
     * @param menu 菜单数据
     * @return
     */
    //http://localhost:82/checkitem/update.d
    @PutMapping("/update")
    public Result update(@RequestBody Menu menu) {


        System.out.println(menu);
        try {
            menuService.update(menu);
//            添加成功给的响应
        } catch (Exception e) {
            e.printStackTrace();
//            添加失败传给前端的响应
            return new Result(false, "修改菜单失败");
        }
        return new Result(true, "修改菜单成功");
    }


    /**
     * 根据id进行查询数据
     *
     * @param id
     * @return
     */
    //http://localhost:82/checkitem/findById/108.do
    @GetMapping("/findById/{id}")
    public Result findById(@PathVariable Integer id) {

        try {
            Menu menu = menuService.findById(id);
            System.out.println(menu.toString());
            return new Result(true, "查询菜单成功", menu);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "查询菜单失败");
        }
    }

    /**
     * 查询所有的数据
     *
     * @return
     */
    //http://localhost:82/menu/findAll.do
    @PostMapping("/findAll")
    public Result findAll(String name, Integer age) {
        System.out.println(name + "," + age);
        return null;
//        try {
//            List<Menu> all = menuService.findAll();
//            return new Result(true, "查询菜单成功", all);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new Result(false, "查询菜单失败");
//        }
    }

    @RequestMapping("/finParentName")
    public Result finParentName() {
        try {
            List<Map<String, Object>> list = menuService.finParentName();
            System.out.println(list);
            return new Result(true, "查询父级菜单成功", list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "查询父级菜单失败");
        }
    }

    //  查询菜单，并将菜单的数据返回
    @RequestMapping("/menuList")
    public Result menuList() {
        List<Map<String, Object>> list = menuService.findMenuList();
        return new Result(true, "success", list);
    }


}
