package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Permission;
import com.itheima.service.PermissionService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/permission")
public class PermissionController {
    @Reference
    private PermissionService permissionService;

    /**
     * 新增
     * @param permission
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody Permission permission ){
        try {
            permissionService.add(permission);
            System.out.println(permission);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
        return new Result(true,"新增权限成功");

    }

    /**
     * 分页查找
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = permissionService.findPage(queryPageBean);
        return  pageResult ;

    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(Integer id){
        try {
            permissionService.delete(id);
        }catch (RuntimeException e){
            return new Result(false,e.getMessage());
        } catch (Exception e) {
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }

        return  new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS) ;

    }

    /**
     * 根据id查找数据 目的是为了回显数据
     * @param id
     * @return
     */
    @RequestMapping("/findById")
    public Result findById(Integer id){
        Permission permission=null;
        try {
             permission = permissionService.findById(id);
        } catch (Exception e) {
          return new Result(false, MessageConstant.EDIT_QUERY_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.EDIT_QUERY_CHECKITEM_SUCCESS,permission);
    }

    /**
     *
     */
    @RequestMapping("/edit")
    public Result edit(@RequestBody Permission permission){
        try {
            permissionService.edit(permission);
        } catch (Exception e) {
           return  new Result(false, MessageConstant.EDIT_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }

    @RequestMapping("/findAll")
    public Result findAll(){
        List permissions=null;
        try {
            permissions = permissionService.findAll();
        } catch (Exception e) {
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant. QUERY_CHECKITEM_SUCCESS,permissions);
    }
}
